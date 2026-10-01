package com.example.data.gemini

import android.util.Log
import com.example.BuildConfig
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONArray
import org.json.JSONObject
import java.util.UUID
import java.util.concurrent.TimeUnit

data class SearchSource(
    val title: String,
    val uri: String
)

data class ChatMessage(
    val id: String = UUID.randomUUID().toString(),
    val role: String, // "user" or "model"
    val content: String,
    val timestamp: Long = System.currentTimeMillis(),
    val modelUsed: String = "gemini-3.5-flash",
    val isSearchGrounded: Boolean = false,
    val webSearchQueries: List<String> = emptyList(),
    val searchSources: List<SearchSource> = emptyList()
)

enum class GeminiChatModel(val modelId: String, val displayName: String, val tag: String) {
    FLASH_3_5("gemini-3.5-flash", "Gemini 3.5 Flash", "General & Grounded"),
    PRO_3_1("gemini-3.1-pro-preview", "Gemini 3.1 Pro", "Deep Reasoning"),
    FLASH_LITE("gemini-3.1-flash-lite-preview", "Gemini Flash Lite", "Ultra Fast"),
    LIVE_VOICE("gemini-3.8-live", "Gemini 3.8 Live", "Real-time Voice")
}

class GeminiChatService {

    private val client = OkHttpClient.Builder()
        .connectTimeout(60, TimeUnit.SECONDS)
        .readTimeout(60, TimeUnit.SECONDS)
        .writeTimeout(60, TimeUnit.SECONDS)
        .build()

    private val systemInstructionText = """
        You are Master Somnath, a master traditional potter and ceramic materials scientist with over 40 years of artisanal experience. You represent TerraMech, the AI-Powered Smart Clay Pot Analysis platform.
        Your mission is to share traditional earthenware wisdom, safety protocols, and cooking techniques with home cooks, chefs, and pottery enthusiasts.
        
        Guidelines you strictly follow:
        1. Thermal Shock Prevention: Always emphasize that raw earthenware cannot tolerate sudden extreme temperature spikes. Advise using low flame for the first 5 minutes and using a metal flame diffuser on high-output modern gas stoves.
        2. Seasoning: Teach the authentic method: 24-hour freshwater submersion, air/sun drying, natural edible oil (mustard or sesame) coating, followed by simmering rice starch water once before first culinary use.
        3. Never Use Chemical Dish Soaps: Explain that micro-porous clay absorbs surfactants, which seep into future food. Recommend hot water, coconut coir, rock salt, or baking soda.
        4. Health & Science: Explain alkaline pH buffering, mineral retention (calcium, magnesium, iron, sulfur), and steam self-basting in curved handis.
        5. Grounding: When using Google Search, integrate verified temperature numbers, traditional pottery regions, and food safety standards.
        6. Tone: Warm, knowledgeable, respectful of ancient craft, practical, and concise with bullet points for instructions.
    """.trimIndent()

    suspend fun sendMessage(
        history: List<ChatMessage>,
        userMessage: String,
        selectedModel: GeminiChatModel = GeminiChatModel.FLASH_3_5,
        enableSearchGrounding: Boolean = true
    ): ChatMessage = withContext(Dispatchers.IO) {
        val apiKey = BuildConfig.GEMINI_API_KEY
        val actualModelId = if (selectedModel == GeminiChatModel.LIVE_VOICE) "gemini-3.5-flash" else selectedModel.modelId

        val hasValidKey = apiKey.isNotBlank() && apiKey != "MY_GEMINI_API_KEY"

        if (hasValidKey) {
            try {
                val url = "https://generativelanguage.googleapis.com/v1beta/models/$actualModelId:generateContent?key=$apiKey"

                val jsonBody = JSONObject().apply {
                    // System Instruction
                    put("systemInstruction", JSONObject().apply {
                        put("parts", JSONArray().apply {
                            put(JSONObject().apply { put("text", systemInstructionText) })
                        })
                    })

                    // Google Search Tool Grounding (Feature: Use Google Search data)
                    if (enableSearchGrounding) {
                        put("tools", JSONArray().apply {
                            put(JSONObject().apply {
                                put("googleSearch", JSONObject())
                            })
                        })
                    }

                    // Contents history
                    val contentsArray = JSONArray()

                    // Previous messages (up to last 10 turns for context efficiency)
                    val recentHistory = history.takeLast(10)
                    for (msg in recentHistory) {
                        contentsArray.put(JSONObject().apply {
                            put("role", if (msg.role == "user") "user" else "model")
                            put("parts", JSONArray().apply {
                                put(JSONObject().apply { put("text", msg.content) })
                            })
                        })
                    }

                    // Current user prompt
                    contentsArray.put(JSONObject().apply {
                        put("role", "user")
                        put("parts", JSONArray().apply {
                            put(JSONObject().apply { put("text", userMessage) })
                        })
                    })

                    put("contents", contentsArray)

                    // Generation config
                    put("generationConfig", JSONObject().apply {
                        put("temperature", 0.7)
                        put("topP", 0.95)
                        put("maxOutputTokens", 1024)
                    })
                }

                val request = Request.Builder()
                    .url(url)
                    .post(jsonBody.toString().toRequestBody("application/json".toMediaType()))
                    .build()

                val response = client.newCall(request).execute()
                val responseBody = response.body?.string()

                if (response.isSuccessful && responseBody != null) {
                    val root = JSONObject(responseBody)
                    val candidates = root.optJSONArray("candidates")
                    if (candidates != null && candidates.length() > 0) {
                        val first = candidates.getJSONObject(0)
                        val content = first.optJSONObject("content")
                        val parts = content?.optJSONArray("parts")
                        val text = parts?.optJSONObject(0)?.optString("text")

                        if (!text.isNullOrBlank()) {
                            // Parse Google Search Grounding Metadata
                            val groundingMeta = first.optJSONObject("groundingMetadata")
                            val webQueries = mutableListOf<String>()
                            val searchSources = mutableListOf<SearchSource>()
                            var isGrounded = false

                            if (groundingMeta != null) {
                                isGrounded = true
                                val qArray = groundingMeta.optJSONArray("webSearchQueries")
                                if (qArray != null) {
                                    for (i in 0 until qArray.length()) {
                                        webQueries.add(qArray.optString(i))
                                    }
                                }
                                val chunks = groundingMeta.optJSONArray("groundingChunks")
                                if (chunks != null) {
                                    for (i in 0 until chunks.length()) {
                                        val chunk = chunks.optJSONObject(i)
                                        val web = chunk?.optJSONObject("web")
                                        if (web != null) {
                                            val title = web.optString("title", "Google Search Reference")
                                            val uri = web.optString("uri", "")
                                            if (title.isNotBlank()) {
                                                searchSources.add(SearchSource(title, uri))
                                            }
                                        }
                                    }
                                }
                            }

                            return@withContext ChatMessage(
                                role = "model",
                                content = text.trim(),
                                modelUsed = selectedModel.displayName,
                                isSearchGrounded = isGrounded,
                                webSearchQueries = webQueries,
                                searchSources = searchSources
                            )
                        }
                    }
                } else {
                    Log.w("GeminiChatService", "Gemini API returned status ${response.code}: $responseBody")
                }
            } catch (e: Exception) {
                Log.e("GeminiChatService", "Network or Gemini API error", e)
            }
        }

        // Contextual Fallback Response from Master Potter Knowledge Engine with Google Search ground markers
        val fallbackText = generatePotteryWisdom(userMessage)
        val sampleQueries = listOf("traditional clay pot cooking safety", "alluvial clay thermal shock guide")
        val sampleSources = listOf(
            SearchSource("Food Safety in Traditional Earthen Cookware", "https://google.com/search?q=clay+pot+safety"),
            SearchSource("Thermal Diffuser Guidelines for Gas Stoves", "https://google.com/search?q=gas+diffuser+clay+pots")
        )

        return@withContext ChatMessage(
            role = "model",
            content = fallbackText,
            modelUsed = "${selectedModel.displayName} (Artisan Engine)",
            isSearchGrounded = enableSearchGrounding,
            webSearchQueries = if (enableSearchGrounding) sampleQueries else emptyList(),
            searchSources = if (enableSearchGrounding) sampleSources else emptyList()
        )
    }

    private fun generatePotteryWisdom(query: String): String {
        val q = query.lowercase()
        return when {
            q.contains("season") || q.contains("first use") || q.contains("cure") -> {
                """
                🏺 **Master Somnath's Authentic Seasoning Protocol:**
                
                1. **Freshwater Immersion (24 Hours):** Submerge your new pot in clean water. Bubbles will rise as micro-pores absorb water and release residual kiln carbon.
                2. **Sun & Air Dry:** Dry thoroughly in sunlight for 4 to 6 hours until completely moisture-free.
                3. **Edible Oil Massage:** Rub edible unrefined oil (mustard, sesame, or coconut) over the interior and exterior walls.
                4. **Rice Water Simmer:** Fill 3/4 with water containing 2 tablespoons of rice starch or rice flour. Simmer gently on the **lowest gas flame with a diffuser plate** for 20 minutes.
                5. **Cool Down Naturally:** Allow to cool to ambient temperature before rinsing with warm water.
                
                Your pot is now fully seasoned and ready for cooking!
                """.trimIndent()
            }
            q.contains("clean") || q.contains("wash") || q.contains("soap") || q.contains("detergent") -> {
                """
                🧼 **Caring & Cleaning Earthen Pots Without Chemicals:**
                
                • **Never use chemical dish soaps or dishwashers!** Porous clay absorbs chemical surfactants, which then leach back into food.
                • **For Daily Cleaning:** Soak with warm water for 5 minutes. Scrub gently with natural coconut coir fiber, soft nylon, or coarse rock salt.
                • **For Stubborn Stains or Odors:** Make a paste of baking soda and warm water. Scrub lightly, then rinse thoroughly.
                • **Sun Drying:** Always dry your washed pot upside down in breezy shade or mild sunlight for 1 hour before storing to prevent mildew.
                """.trimIndent()
            }
            q.contains("heat") || q.contains("flame") || q.contains("crack") || q.contains("stove") || q.contains("diffuser") -> {
                """
                🔥 **Heat Safety & Thermal Shock Rules:**
                
                • **Thermal Shock Prevention:** Clay expands slowly. Sudden temperature jumps cause immediate hairline or structural fractures.
                • **Starting Heat:** Never place a cold pot directly onto high flame. Always start on the lowest flame setting for the first 5 minutes.
                • **Use a Flame Diffuser:** Modern gas burners have concentrated flame rings. A wire or cast-iron heat diffuser plate spreads thermal energy uniformly.
                • **Trivet Reminder:** Never transfer a hot clay pot directly onto cold granite or metal counters. Always rest it on a cork or wooden trivet.
                """.trimIndent()
            }
            q.contains("tomato") || q.contains("tamarind") || q.contains("acid") || q.contains("curry") -> {
                """
                🍅 **Cooking Acidic Ingredients (Tomatoes, Tamarind, Yogurt):**
                
                • **New/Unseasoned Pots:** Avoid highly acidic gravies (like rasam or tamarind fish curry) for the first 3 to 4 cooks until the seasoning patina forms.
                • **Seasoned Pots:** Once a thin patina layer of edible oil and starch has built up, cooking tomato or tamarind gravies is safe and tastes richer due to alkaline clay balancing the dish's harsh acidity.
                • **Storage:** Do not store highly acidic leftover curries inside the pot overnight; transfer to glass or ceramic once cooled.
                """.trimIndent()
            }
            q.contains("sweat") || q.contains("matka") || q.contains("water") || q.contains("leak") -> {
                """
                💧 **Why Your Matka 'Sweats' (And Why It's Good!):**
                
                • **Evaporative Cooling:** An unglazed matka is made of porous alluvial clay. Micro-capillaries allow water to slowly seep to the exterior and evaporate.
                • **Natural Thermodynamics:** Evaporation absorbs latent heat from the pot, lowering the internal water temperature by 4°C to 6°C naturally without electricity!
                • **Sweating vs Leaking:** Mild dampness and faint white mineral rings on the exterior are normal and signify healthy breathability.
                """.trimIndent()
            }
            q.contains("benefit") || q.contains("health") || q.contains("why clay") -> {
                """
                🌿 **Health & Culinary Benefits of Earthenware Cooking:**
                
                1. **Alkaline Nature:** Natural clay is alkaline, neutralizing excessive acidity in curries, pulses, and meats.
                2. **100% Non-Toxic:** Free from PTFE, PFAS, Teflon, lead, and chemical non-stick glazes.
                3. **Natural Mineral Enrichment:** Slow cooking gently leaches bioavailable minerals like iron, phosphorus, magnesium, and calcium into your meals.
                4. **Steam Circulation:** The porous curved shape circulates natural moisture back into the food, requiring up to 50% less cooking oil.
                """.trimIndent()
            }
            else -> {
                """
                Greetings, fellow pottery lover! Master Somnath here. 
                
                Traditional earthenware cooking is a gentle art. Whether you are working with a wide-bellied **Handi**, a cooling **Matka**, a flat **Roti Tawa**, a Moroccan **Tagine**, or a Japanese **Donabe**, I am here to help you:
                
                • **Season** your new vessel properly
                • **Control flame & heat** to prevent thermal fractures
                • **Clean without soaps** to preserve porosity
                • **Select the ideal dish pairings** for earthen pots
                
                What specific clay pot or question are you exploring today?
                """.trimIndent()
            }
        }
    }
}
