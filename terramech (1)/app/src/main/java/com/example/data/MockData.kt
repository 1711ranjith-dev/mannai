package com.example.data

import com.example.model.AnalysisResult
import com.example.model.ClayPot
import com.example.model.ClayRecipe
import com.example.model.CuringGuide
import com.example.model.CuringStep
import com.example.model.HeatSuitability
import com.example.model.PotCategory
import com.example.model.PotIssue
import com.example.model.PotRecommendation
import com.example.model.RecommendationCriteria
import java.util.UUID

object MockData {

    val samplePresets = listOf(
        PresetItem(
            key = "handi",
            title = "Traditional Cooking Handi",
            subtitle = "Deep spherical pot with steam-retaining rim",
            estimatedCapacity = "2.5 L",
            category = PotCategory.COOKING
        ),
        PresetItem(
            key = "matka",
            title = "Earthen Water Matka",
            subtitle = "Round natural porous evaporative cooling pitcher",
            estimatedCapacity = "5.0 L",
            category = PotCategory.WATER_STORAGE
        ),
        PresetItem(
            key = "tawa",
            title = "Terracotta Cooking Tawa",
            subtitle = "Flattish convex griddle for artisanal flatbreads",
            estimatedCapacity = "0.8 L",
            category = PotCategory.COOKING
        ),
        PresetItem(
            key = "urn",
            title = "Glazed Decorative Urn",
            subtitle = "High-gloss finished ceramic terracotta vessel",
            estimatedCapacity = "3.2 L",
            category = PotCategory.DECORATIVE
        )
    )

    data class PresetItem(
        val key: String,
        val title: String,
        val subtitle: String,
        val estimatedCapacity: String,
        val category: PotCategory
    )

    val potsCatalog: List<ClayPot> = listOf(
        ClayPot(
            id = "pot_handi_1",
            name = "Traditional Biryani Handi",
            category = PotCategory.COOKING,
            description = "A deep, wide-mouthed earthen pot with a narrow neck and curved belly. Designed specifically for dum cooking and slow simmer stews.",
            capacity = "2.5 L",
            material = "Natural Unglazed Terracotta with Mica Inclusions",
            heatSuitability = HeatSuitability.SUITABLE,
            heatSuitabilityNote = "Suitable for low to medium direct gas flame and charcoal embers. Requires gradual heat build-up.",
            usage = listOf("Biryani", "Slow Cooking", "Curries", "Dal", "Dum Stews"),
            safetyGuidelines = listOf(
                "Never place cold pot directly onto high flame; sudden thermal shock will cause fractures.",
                "Always ensure liquid or oil is added before placing on heat source.",
                "Do not use if structural hairline cracks are visible near base.",
                "Use heat diffuser on high-BTU modern gas burners."
            ),
            careAndMaintenance = listOf(
                "Initial Seasoning: Soak in clean water for 24 hours, dry in sunlight, rub with edible mustard or sesame oil, and warm with rice starch water.",
                "Cleaning: Scrub only with warm water and soft coconut coir or nylon brush. Never use chemical dish soaps as clay absorbs surfactants.",
                "Drying: Allow to air dry completely in ventilated area before stowing.",
                "Storage: Store uncovered with paper towel inside to absorb any ambient moisture."
            ),
            seasoningInstructions = "Submerge in fresh water for 24 hrs. Coat interior with oil and simmer rice water once before first culinary use.",
            origin = "Northern & Central India Artisanal Guilds",
            presetKey = "handi"
        ),
        ClayPot(
            id = "pot_matka_2",
            name = "Royal Matka Water Cooler",
            category = PotCategory.WATER_STORAGE,
            description = "A classic spherical earthenware pitcher crafted from micro-porous alluvial riverbed clay. Uses capillary evaporation to cool drinking water naturally by 4-6°C.",
            capacity = "5.0 L",
            material = "Unfinished Alluvial Micro-Porous Red Clay",
            heatSuitability = HeatSuitability.NOT_RECOMMENDED,
            heatSuitabilityNote = "Strictly for ambient water storage. Direct heating will bake out micro-capillaries and risk explosion.",
            usage = listOf("Water Storage", "Natural Cooling", "Alkaline Water Infusion", "Household Dispenser"),
            safetyGuidelines = listOf(
                "Keep elevated on a ring stand or sand bowl to allow continuous evaporation.",
                "Do not store dairy, citrus, or fermented beverages in raw water pots.",
                "Flush with fresh boiled water once every fortnight to prevent microbial film."
            ),
            careAndMaintenance = listOf(
                "Cleaning: Rinse with hot water and baking soda or rock salt; avoid scented detergents.",
                "Outer surface: Gently wipe exterior salt rings with damp cotton cloth.",
                "Rejuvenation: Re-soak overnight every season to clear pores."
            ),
            seasoningInstructions = "Fill with fresh water and discard initial batch after 48 hours to activate evaporative capillary pathways.",
            origin = "Rajasthan & Gujarat Clay Traditions",
            presetKey = "matka"
        ),
        ClayPot(
            id = "pot_tawa_3",
            name = "Terracotta Roti Tawa",
            category = PotCategory.COOKING,
            description = "Shallow, gently curved griddle designed for even heat distribution. Imparts natural earthy aroma and superior crust texture to artisanal breads.",
            capacity = "0.8 L",
            material = "Dense Stoneware-Terracotta Composite Clay",
            heatSuitability = HeatSuitability.SUITABLE,
            heatSuitabilityNote = "Suitable for gas flame with heat diffuser plate. Maximum recommended surface temperature 260°C.",
            usage = listOf("Flatbreads", "Rotis", "Pita", "Tortillas", "Dry Roasting Spices"),
            safetyGuidelines = listOf(
                "Preheat gradually on low flame for 4 minutes before raising to cooking temperature.",
                "Never rinse with cold water while the tawa is hot.",
                "Do not deep fry or submerge in excess oil on open flame."
            ),
            careAndMaintenance = listOf(
                "Wipe clean with a damp cloth and a pinch of dry flour after each use.",
                "Lightly wipe with a drop of coconut or vegetable oil before storing.",
                "Never wash in automatic dishwasher."
            ),
            seasoningInstructions = "Rub raw garlic and mustard oil on surface, heat gently on lowest flame for 10 minutes, allow to cool.",
            origin = "South Asian Village Pottery Tradition",
            presetKey = "tawa"
        ),
        ClayPot(
            id = "pot_curd_4",
            name = "Earthen Yogurt Setting Jar",
            category = PotCategory.FERMENTATION,
            description = "Porous clay pot designed to absorb excess whey during milk curdling, yielding exceptionally thick, creamy, natural yogurt without synthetic gelatin.",
            capacity = "1.5 L",
            material = "Low-Fire Porous Terracotta",
            heatSuitability = HeatSuitability.NOT_RECOMMENDED,
            heatSuitabilityNote = "Used at ambient temperatures for setting yogurt and microbial fermentation.",
            usage = listOf("Yogurt Setting", "Kefir", "Buttermilk Storage", "Culturing"),
            safetyGuidelines = listOf(
                "Clean thoroughly between culture cycles to maintain beneficial bacterial balance.",
                "Not suitable for direct flame or oven baking."
            ),
            careAndMaintenance = listOf(
                "Wash with warm water and lemon rind or mild vinegar rinse.",
                "Sun dry thoroughly for at least 4 hours after washing."
            ),
            seasoningInstructions = "Rinse with hot water and let air dry completely before introducing starter culture.",
            origin = "Bengal & Odisha Earthen Curd Vessels",
            presetKey = "handi"
        ),
        ClayPot(
            id = "pot_kulhar_5",
            name = "Traditional Artisanal Kulhar",
            category = PotCategory.TRADITIONAL,
            description = "Single-fired terracotta cup without glaze, traditionally used for steaming spiced chai or chilled rabdi, imparting unmatched petrichor aroma.",
            capacity = "0.25 L",
            material = "Single-Fired Unglazed Earthen Clay",
            heatSuitability = HeatSuitability.LIMITED,
            heatSuitabilityNote = "Can hold boiling hot tea or coffee; not intended for placement over direct flames.",
            usage = listOf("Masala Chai", "Lassi", "Dessert Serving", "Rabdi", "Artisanal Tableware"),
            safetyGuidelines = listOf(
                "Ensure smooth rim before drinking to avoid clay micro-splinters.",
                "Disposable in traditional culture, but can be reused for cold desserts if washed gently."
            ),
            careAndMaintenance = listOf(
                "Hand rinse in warm water; avoid harsh scrubbing to preserve natural edge.",
                "Store in dry ambient cupboard."
            ),
            seasoningInstructions = "Brief warm water soak for 5 minutes prior to first serving.",
            origin = "Varanasi Pottery Clusters",
            presetKey = "handi"
        ),
        ClayPot(
            id = "pot_urn_6",
            name = "Glazed Terracotta Vessel",
            category = PotCategory.DECORATIVE,
            description = "Stately terracotta urn with decorative glazed exterior and sealed inner lining. Designed for aesthetic interior decor, flower arrangements, or dry grain storage.",
            capacity = "3.2 L",
            material = "Fired Terracotta with Lead-Free Vitrified Glaze",
            heatSuitability = HeatSuitability.NOT_RECOMMENDED,
            heatSuitabilityNote = "Glazed finishes are not rated for stovetop cooking. Glaze might craze or chip under flame.",
            usage = listOf("Home Decor", "Dry Goods Storage", "Planter", "Centerpiece"),
            safetyGuidelines = listOf(
                "Check food safety rating if storing acidic liquids.",
                "Keep away from sudden knocks on stone or metal surfaces."
            ),
            careAndMaintenance = listOf(
                "Clean with microfiber cloth and mild eco-friendly soap.",
                "Polish exterior with natural beeswax for lasting sheen."
            ),
            seasoningInstructions = "No seasoning required due to sealed glaze.",
            origin = "Mediterranean & Khurja Heritage Studios",
            presetKey = "urn"
        ),
        ClayPot(
            id = "pot_pickle_7",
            name = "Martaban Ceramic-Clay Pickle Jar",
            category = PotCategory.STORAGE,
            description = "Traditional glazed top and unglazed terracotta base jar specifically crafted for fermenting mango, lime, and chili pickles in mustard oil.",
            capacity = "4.0 L",
            material = "Stoneware-Lined Terracotta with High-Alumina Glaze",
            heatSuitability = HeatSuitability.NOT_RECOMMENDED,
            heatSuitabilityNote = "Acid-resistant storage vessel; not designed for stovetop heating.",
            usage = listOf("Pickle Fermentation", "Dry Spices", "Fermented Vegetables", "Kombucha"),
            safetyGuidelines = listOf(
                "Ensure seal cloth is tied firmly around neck to prevent airborne contaminants.",
                "Use clean, dry wooden spoons only when scooping contents."
            ),
            careAndMaintenance = listOf(
                "Wash with warm water and rock salt; dry fully under midday sun before packing pickles.",
                "Keep lid groove clean."
            ),
            seasoningInstructions = "Sun bake empty for 1 day before adding salt and oil mixture.",
            origin = "Punjab & Uttar Pradesh Heritage Pottery",
            presetKey = "urn"
        ),
        ClayPot(
            id = "pot_serving_8",
            name = "Artisanal Serving Kadhai",
            category = PotCategory.SERVING,
            description = "Rustic earthen bowl with sculpted side handles. Keeps prepared soups, gravies, and rice piping hot on the dining table.",
            capacity = "1.8 L",
            material = "Burnished Terracotta Clay",
            heatSuitability = HeatSuitability.LIMITED,
            heatSuitabilityNote = "Can be placed in warm oven (up to 180°C) for gentle reheating; not for direct high gas flame.",
            usage = listOf("Table Serving", "Salads", "Slow Warmth Retention", "Soup Presentation"),
            safetyGuidelines = listOf(
                "Always use heat-resistant mats under the pot when placing on dining tables.",
                "Avoid dropping heavy metal spoons against the clay base."
            ),
            careAndMaintenance = listOf(
                "Wash with soft sponge and warm water.",
                "Let dry thoroughly upside down on a drying rack."
            ),
            seasoningInstructions = "Rub interior with rice oil and let sit for 12 hours before first serving.",
            origin = "Kerala Clay Craft Cooperative",
            presetKey = "handi"
        )
    )

    fun analyzePotImage(presetKey: String?, customFileName: String?): AnalysisResult {
        // If matches known preset or is analyzed, generate realistic structured output
        return when (presetKey) {
            "matka" -> AnalysisResult(
                id = UUID.randomUUID().toString(),
                potType = "Artisanal Water Matka (Surahi)",
                potTypeExplanation = "Spherical wide-body shape with rounded base and narrow collar designed for evaporative thermal cooling.",
                confidenceScore = 0.96f,
                estimatedCapacity = "5.0 L",
                capacityNote = "Estimated based on geometric proportions and standard pitcher ratios. Actual volume may vary by ±10%.",
                material = "Natural Alluvial Porous Clay",
                materialExplanation = "Unfinished, matte terracotta finish with visible natural micro-pores and capillary breathability.",
                heatSuitability = HeatSuitability.NOT_RECOMMENDED,
                heatSuitabilityExplanation = "Intended strictly for ambient liquid cooling. Direct flame heating will destroy capillary cooling channels and cause thermal cracking.",
                recommendedUsage = listOf("Water Storage", "Natural Cooling", "Alkaline Water", "Ambient Dispenser"),
                safetyGuidelines = listOf(
                    "Store on elevated ring base with drainage dish to catch natural capillary condensation.",
                    "Do not use for boiling or cooking on direct stovetop.",
                    "Flush thoroughly with fresh warm water every 10–14 days.",
                    "Never freeze or expose to sub-zero temperatures with liquid inside."
                ),
                careAndMaintenance = listOf(
                    "Cleaning: Rinse with lukewarm water and a teaspoon of baking soda; do not use chemical soaps.",
                    "Drying: Air dry in breezy shade periodically.",
                    "Storage: When not in use, ensure pot is bone-dry to prevent mildew in pores."
                ),
                smartRecommendations = listOf(
                    "Pairs best with an earthen lid and brass ladle.",
                    "Keep in an airy corridor or open kitchen corner for optimal evaporative cooling rate.",
                    "Consider adding a handful of vetiver roots inside for traditional summer cooling aroma."
                ),
                presetKey = "matka",
                shapeDescription = "Symmetric spherical bulb with flared pouring collar and circular base."
            )
            "tawa" -> AnalysisResult(
                id = UUID.randomUUID().toString(),
                potType = "Terracotta Cooking Tawa (Griddle)",
                potTypeExplanation = "Flat convex circular clay disc engineered for even heat distribution across flatbreads.",
                confidenceScore = 0.94f,
                estimatedCapacity = "0.8 L",
                capacityNote = "Estimated shallow volume. Intended for contact surface cooking rather than liquid containment.",
                material = "High-Density Reinforced Stoneware Clay",
                materialExplanation = "Smooth burnished cooking face treated with fine iron-rich slip for reduced stickiness and heat retention.",
                heatSuitability = HeatSuitability.SUITABLE,
                heatSuitabilityExplanation = "Designed for gas flame or charcoal cooking when used with gentle preheating and moderate flame settings.",
                recommendedUsage = listOf("Flatbreads", "Rotis & Naan", "Pancakes", "Tortillas", "Spice Toasting"),
                safetyGuidelines = listOf(
                    "Preheat on low flame for 3–5 minutes before raising heat to avoid thermal shock.",
                    "Never shock hot tawa with cold water; allow to cool naturally to room temperature before washing.",
                    "Do not place directly onto extreme high-BTU open campfire flames without a diffuser plate."
                ),
                careAndMaintenance = listOf(
                    "Cleaning: Gently scrape crumbs with wooden spatula and wipe with damp linen cloth.",
                    "Oiling: Lightly buff with coconut or sesame oil after every 3 uses to maintain natural seasoning.",
                    "Storage: Store horizontally in padded shelf or hang using dedicated wall strap."
                ),
                smartRecommendations = listOf(
                    "Always season with oil and garlic prior to initial flatbread session.",
                    "Use a wire heat diffuser rack if your stove has concentrated single-point flame rings."
                ),
                presetKey = "tawa",
                shapeDescription = "Convex circular disk with shallow bevel edge."
            )
            "urn" -> AnalysisResult(
                id = UUID.randomUUID().toString(),
                potType = "Glazed Ornamental Terracotta Urn",
                potTypeExplanation = "High-profile decorative vessel with vitrified gloss coating, flared rim, and ornamental shoulder fluting.",
                confidenceScore = 0.92f,
                estimatedCapacity = "3.2 L",
                capacityNote = "Estimated volumetric internal displacement. Suitable for dry goods or decorative display.",
                material = "Vitrified Glazed Earthenware",
                materialExplanation = "Terracotta core kiln-fired with mineral glaze finish, creating an impermeable glass-like outer barrier.",
                heatSuitability = HeatSuitability.NOT_RECOMMENDED,
                heatSuitabilityExplanation = "Glazed coatings cannot withstand uneven thermal expansion on direct stove flames; heating may cause glaze crazing, chemical release, or explosive cracking.",
                recommendedUsage = listOf("Home Decoration", "Dry Spice Storage", "Indoor Planter", "Decorative Centerpiece"),
                safetyGuidelines = listOf(
                    "Do not expose to open stovetop or broiler flames.",
                    "Verify non-toxic certified glaze before storing pickled or acidic liquids for extended periods.",
                    "Handle with dry hands; glazed surfaces become slick when wet."
                ),
                careAndMaintenance = listOf(
                    "Cleaning: Wash with mild organic dish detergent and warm water.",
                    "Drying: Towel dry with microfiber cloth to prevent water spots on gloss glaze.",
                    "Maintenance: Avoid abrasive scourers or wire wool."
                ),
                smartRecommendations = listOf(
                    "Excellent as an ambient kitchen countertop vessel for storing dry beans or rice.",
                    "Display in natural indirect sunlight to highlight rich glaze undertones."
                ),
                presetKey = "urn",
                shapeDescription = "Elongated classical amphora silhouette with glazed finish."
            )
            else -> AnalysisResult(
                id = UUID.randomUUID().toString(),
                potType = "Traditional Clay Cooking Handi",
                potTypeExplanation = "Deep spherical earthen vessel featuring curved sides, thick walls, and a recessed rim designed for retaining steam moisture.",
                confidenceScore = 0.95f,
                estimatedCapacity = "2.5 L",
                capacityNote = "Estimated based on pot body proportions and neck flare. Actual capacity may vary by ±0.3 L.",
                material = "Natural Unglazed Terracotta",
                materialExplanation = "Artisanal porous clay body with natural earthy red-brown coloration and fine sand temper for heat resistance.",
                heatSuitability = HeatSuitability.SUITABLE,
                heatSuitabilityExplanation = "Suitable for low to medium direct flame and slow simmer cooking. Gradual heat distribution preserves food nutrients.",
                recommendedUsage = listOf("Slow Cooking", "Biryani & Rice", "Lentil Curries", "Stews", "Clay Pot Baking"),
                safetyGuidelines = listOf(
                    "Always begin on the lowest heat setting for 5 minutes before increasing flame.",
                    "Ensure bottom of pot has adequate liquid or sauce before applying heat to prevent dry scorching.",
                    "Do not transfer directly from hot flame onto cold granite countertops; always place onto a cork or wooden trivet.",
                    "Discard or repurpose if bottom surface shows deep spider-web fractures."
                ),
                careAndMaintenance = listOf(
                    "Initial Seasoning: Soak in fresh water for 24 hours, dry in sun, rub with oil, and simmer rice water once.",
                    "Washing: Use hot water and soft natural bristle brush. Avoid synthetic dishwashing soap as clay absorbs chemicals.",
                    "Thorough Drying: Dry completely in sunlight or airy spot before storing to prevent mold.",
                    "Storage: Store without lid on top to maintain natural airflow."
                ),
                smartRecommendations = listOf(
                    "Ideal for cooking traditional slow-cooked curries and biryani where moisture recycling is essential.",
                    "Add an unglazed clay lid sealed with wheat dough (dum) for authentic aroma retention.",
                    "Keep on medium-low flame for the best flavor infusion."
                ),
                presetKey = "handi",
                shapeDescription = "Wide spherical base tapering into a defined neck and lip collar."
            )
        }
    }

    fun getRecommendations(criteria: RecommendationCriteria): List<PotRecommendation> {
        return potsCatalog.map { pot ->
            var score = 70
            var reason = "Matches general clay pottery criteria."

            // Usage matching
            if (criteria.usage.isNotBlank()) {
                val matchesUsage = pot.usage.any { it.contains(criteria.usage, ignoreCase = true) } ||
                        pot.category.displayName.contains(criteria.usage, ignoreCase = true)
                if (matchesUsage) {
                    score += 15
                    reason = "Perfect match for your intended ${criteria.usage} usage."
                }
            }

            // Heat matching
            if (criteria.heatNeeded.isNotBlank()) {
                when {
                    criteria.heatNeeded.startsWith("Yes", ignoreCase = true) -> {
                        if (pot.heatSuitability == HeatSuitability.SUITABLE) {
                            score += 15
                            reason += " Fully rated for direct heat cooking."
                        } else {
                            score -= 25
                        }
                    }
                    criteria.heatNeeded.startsWith("No", ignoreCase = true) -> {
                        if (pot.heatSuitability != HeatSuitability.SUITABLE) {
                            score += 10
                            reason += " Safe and specialized for non-heating use."
                        }
                    }
                }
            }

            // Capacity matching
            if (criteria.capacity.isNotBlank()) {
                if (pot.capacity.contains(criteria.capacity.replace(" ", ""), ignoreCase = true) ||
                    (criteria.capacity.contains("2-3", ignoreCase = true) && pot.capacity.contains("2.5")) ||
                    (criteria.capacity.contains("5", ignoreCase = true) && pot.capacity.contains("5.0")) ||
                    (criteria.capacity.contains("Under 1", ignoreCase = true) && pot.capacity.contains("0."))
                ) {
                    score += 10
                    reason += " Matches your preferred ${pot.capacity} capacity."
                }
            }

            PotRecommendation(
                pot = pot,
                matchScore = score.coerceIn(55, 99),
                matchReason = reason.trim()
            )
        }.sortedByDescending { it.matchScore }.take(4)
    }

    val curingGuides = listOf(
        CuringGuide(
            id = "curing_handi",
            potType = "Cooking Handi & Kadai",
            summary = "Essential 4-step seasoning to harden pores and eliminate muddy aftertaste.",
            estimatedDays = "2 Days",
            recommendedOil = "Mustard, Sesame, or Coconut Oil (high smoke point)",
            goldenRule = "Never heat a completely dry, unseasoned clay pot directly over high flame.",
            steps = listOf(
                CuringStep(
                    stepNumber = 1,
                    title = "Submerged Water Soak",
                    durationMinutes = 1440, // 24 hours
                    instruction = "Submerge the new pot and lid completely in fresh room-temperature water for 24 hours. The porous walls will saturate with water, expelling trapped air bubbles.",
                    proTip = "Add 1 cup of strained rice starch water (kanji) to the soak to help bind loose fine particles."
                ),
                CuringStep(
                    stepNumber = 2,
                    title = "Complete Sun Drying",
                    durationMinutes = 360, // 6 hours
                    instruction = "Remove from water and let it air-dry naturally in direct sunlight or in a breezy room until completely dry to touch.",
                    proTip = "Make sure the base is 100% dry; placing wet clay on flame causes immediate thermal fractures."
                ),
                CuringStep(
                    stepNumber = 3,
                    title = "Aromatic Oil Rub",
                    durationMinutes = 15,
                    instruction = "Generously coat the entire interior and exterior with natural unrefined mustard or sesame oil using a soft cotton cloth.",
                    proTip = "Leave the coated pot to absorb the oil for at least 2-3 hours before heat application."
                ),
                CuringStep(
                    stepNumber = 4,
                    title = "Gentle Rice Starch Simmer",
                    durationMinutes = 45,
                    instruction = "Fill pot half-full with starchy rice water or diluted milk. Place on LOW flame with a heat diffuser. Bring to a very gentle simmer for 30-40 minutes, then turn off and let cool completely overnight.",
                    proTip = "Discard this initial liquid. Your pot is now hardened, tempered, and ready for lifelong cooking!"
                )
            )
        ),
        CuringGuide(
            id = "curing_tawa",
            potType = "Clay Tawa (Griddle)",
            summary = "Quick 3-step seasoning to create a natural non-stick patina for rotis and dosas.",
            estimatedDays = "1 Day",
            recommendedOil = "Sesame oil or Gingelly oil",
            goldenRule = "Never pour cold batter onto a scorching dry clay tawa; always warm it slowly.",
            steps = listOf(
                CuringStep(
                    stepNumber = 1,
                    title = "Clean Water Rinse",
                    durationMinutes = 120, // 2 hours
                    instruction = "Soak the tawa in clean lukewarm water for 2 hours. Do not use chemical soap.",
                    proTip = "Gently scrub surface with a soft scrubber to remove clay dust."
                ),
                CuringStep(
                    stepNumber = 2,
                    title = "Turmeric & Oil Paste Coating",
                    durationMinutes = 30,
                    instruction = "Mix 1 tbsp turmeric powder with 2 tbsp sesame oil. Rub this golden paste uniformly across the cooking surface.",
                    proTip = "Turmeric acts as a natural antimicrobial sealant."
                ),
                CuringStep(
                    stepNumber = 3,
                    title = "Low Flame Pre-heat & Onion Rub",
                    durationMinutes = 20,
                    instruction = "Place on lowest gas flame. Rub a sliced raw onion dipped in oil all over the hot surface for 5 minutes. Let it cool naturally.",
                    proTip = "The onion's natural sulfur forms an organic non-stick coating."
                )
            )
        ),
        CuringGuide(
            id = "curing_matka",
            potType = "Earthen Water Matka",
            summary = "Gentle preparation for natural evaporative mineral cooling without chemical leaching.",
            estimatedDays = "12 Hours",
            recommendedOil = "NO OIL! Water only (oil clogs evaporative micropores)",
            goldenRule = "NEVER apply oil, soap, or detergents to drinking water matkas!",
            steps = listOf(
                CuringStep(
                    stepNumber = 1,
                    title = "Overnight Fresh Water Flush",
                    durationMinutes = 720, // 12 hours
                    instruction = "Fill the matka to the brim with potable fresh water. Allow moisture to seep through outer walls onto an earthen tray.",
                    proTip = "Outer sweat droplets prove the micro-capillaries are active and functional!"
                ),
                CuringStep(
                    stepNumber = 2,
                    title = "Drain & Salt Scrub Test",
                    durationMinutes = 10,
                    instruction = "Empty the first batch of water. Rinse interior with pinch of rock salt and fresh water. Refill with fresh drinking water.",
                    proTip = "Wrap a damp jute or cotton cloth around the neck for extra 3-4°C chill."
                )
            )
        ),
        CuringGuide(
            id = "curing_tagine",
            potType = "Moroccan Tagine",
            summary = "Two-stage immersion and low-heat bake to endure conical condensation cycles.",
            estimatedDays = "1 Day",
            recommendedOil = "Virgin Olive Oil",
            goldenRule = "Always use an induction/gas heat diffuser ring under a Tagine base.",
            steps = listOf(
                CuringStep(
                    stepNumber = 1,
                    title = "Base & Cone Submersion",
                    durationMinutes = 180, // 3 hours
                    instruction = "Submerge base dish and conical lid in a large sink or basin filled with tap water for 2-3 hours.",
                    proTip = "Ensure the rim and steam vents are completely submerged."
                ),
                CuringStep(
                    stepNumber = 2,
                    title = "Olive Oil Massage",
                    durationMinutes = 15,
                    instruction = "Dry with a clean towel. Rub 2 tablespoons of olive oil all over the inside and outside of the base and lid.",
                    proTip = "Avoid touching glazed outer decorative patterns with harsh brushes."
                ),
                CuringStep(
                    stepNumber = 3,
                    title = "Cold-Oven Gentle Tempering",
                    durationMinutes = 120, // 2 hours
                    instruction = "Place tagine inside a COLD oven. Set temperature to 150°C (300°F). Bake for 2 hours, then turn oven off and leave inside until completely cold.",
                    proTip = "Gradual oven heating tempers the clay without localized flame stress."
                )
            )
        )
    )

    val potIssues = listOf(
        PotIssue(
            id = "issue_mold",
            title = "White Mold / Mildew Growth",
            symptom = "Fuzzy white or grey patches on pot interior after storage in closed cabinet.",
            cause = "Pot was stored while still retaining microscopic moisture inside the pores in an unventilated dark space.",
            remedySteps = listOf(
                "Do NOT use chemical dish soap or bleach (clay will absorb toxic chemicals).",
                "Make a paste of baking soda and fresh lemon juice.",
                "Scrub thoroughly with a coconut fiber brush or stiff nylon bristle.",
                "Boil water in the pot with 2 tablespoons of white vinegar for 10 minutes.",
                "Sun-dry under direct hot sunlight for at least 8 hours to sterilize."
            ),
            preventionTip = "Always store clay pots with lid ajar or upside down in an airy, dry shelf.",
            severity = "Moderate"
        ),
        PotIssue(
            id = "issue_burn",
            title = "Burnt Food Crust / Carbon Stains",
            symptom = "Black blackened crust stuck firmly to the bottom base after cooking.",
            cause = "High localized flame heat or inadequate moisture during initial frying.",
            remedySteps = listOf(
                "Fill the pot with warm water and 3 tablespoons of coarse rock salt.",
                "Let it soak undisturbed for 3-4 hours to soften carbon deposits.",
                "Scrub gently using coarse salt as a natural abrasive with a half-cut raw potato or lemon rind.",
                "Boil water with 1 tbsp baking soda to lift stubborn grease."
            ),
            preventionTip = "Always cook on low-to-medium heat with a heat diffuser plate.",
            severity = "Mild"
        ),
        PotIssue(
            id = "issue_seepage",
            title = "Base Weeping / Micro-Pore Seepage",
            symptom = "Small moisture rings or water droplets pooling under the pot during cooking.",
            cause = "Natural micro-pores have opened up due to repeated thermal expansion or incomplete starch seasoning.",
            remedySteps = listOf(
                "Prepare a thick gruel from cooked rice water (boiled rice starch) or whole wheat slurry.",
                "Fill the pot half-way with the starchy liquid.",
                "Simmer on the lowest possible flame for 45 minutes.",
                "Turn off heat and let the liquid sit inside until completely cold.",
                "The gelatinized starch crystals penetrate the micro-fissures and bake into a waterproof seal."
            ),
            preventionTip = "Regularly cook rice or starchy broths in your pot once every two months to replenish the seal.",
            severity = "Moderate"
        ),
        PotIssue(
            id = "issue_efflorescence",
            title = "White Chalky Salt Crust (Efflorescence)",
            symptom = "White powder residue appearing on the outer surface of drinking water matkas.",
            cause = "Harmless natural minerals (calcium, magnesium) dissolved in water being filtered and left behind during evaporation.",
            remedySteps = listOf(
                "Rinse under tap water with a soft nylon brush.",
                "No chemicals needed; this is positive proof that your matka is naturally filtering your water.",
                "For heavy crust, wipe gently with diluted vinegar on a cloth, followed by copious water rinse."
            ),
            preventionTip = "Rinse the outer shell every 3-4 days with fresh water.",
            severity = "Mild"
        ),
        PotIssue(
            id = "issue_crack",
            title = "Hairline Thermal Crack Warning",
            symptom = "Fine visible line on bottom or side that emits a dull 'thud' rather than a high resonant chime.",
            cause = "Thermal shock: pouring cold water into a hot pot, or placing cold pot on roaring flame.",
            remedySteps = listOf(
                "Do not use for deep liquids or stovetop flame cooking.",
                "If superficial: boil thick rice starch to see if hairline fracture seals.",
                "If leaking continues, retire from stovetop flame use immediately.",
                "Repurpose pot safely for dry storage (garlic, shallots) or natural container planting."
            ),
            preventionTip = "Never expose hot clay to cold surfaces; always place hot pots on wooden trivets.",
            severity = "Critical"
        )
    )

    val clayRecipes = listOf(
        ClayRecipe(
            id = "recipe_biryani",
            title = "Earthen Dum Biryani",
            potType = "Traditional Clay Handi with Rim",
            cookTime = "45 mins",
            flameLevel = "Low Flame Only",
            description = "Fragrant long-grain basmati rice and marinated spices slow-steamed under a sealed wheat dough ring. Clay retains gentle uniform heat without scorching.",
            ingredients = listOf(
                "2 cups Aged Basmati Rice (soaked 30 mins)",
                "500g Marinated Vegetables or Chicken with Yogurt & Spices",
                "2 tbsp Pure Ghee & Saffron Milk",
                "Fried crisp onions (Birista) & mint leaves",
                "Wheat flour dough strip for rim sealing"
            ),
            cookingSteps = listOf(
                "Place cured Handi on low flame with heat diffuser plate.",
                "Layer par-boiled rice over marinated base.",
                "Drizzle saffron milk, pure ghee, and top with fried onions.",
                "Seal the clay lid tightly using a strip of wheat dough.",
                "Cook on lowest simmer flame for 30 minutes. Let rest unopened for 15 minutes before unsealing."
            ),
            earthenBenefit = "Clay allows slow pressure condensation while absorbing excess wetness, yielding distinct, perfectly tender, non-sticky rice grains."
        ),
        ClayRecipe(
            id = "recipe_fish_curry",
            title = "Kerala Meen Curry (Manchatti Fish Curry)",
            potType = "Clay Manchatti (South Indian Earthen Kadai)",
            cookTime = "30 mins",
            flameLevel = "Low-to-Medium",
            description = "Spicy, tangy fish curry cooked with Malabar tamarind (Kudampuli) and coconut oil. Taste enhances significantly when left in the clay pot for 24 hours.",
            ingredients = listOf(
                "500g Kingfish or Pomfret steaks",
                "3-4 pieces Kudampuli (Malabar tamarind) soaked",
                "2 tbsp Coconut oil, curry leaves & fenugreek seeds",
                "Shallots, green chilies, ginger-garlic paste",
                "Kashmiri chili powder, turmeric & coriander"
            ),
            cookingSteps = listOf(
                "Heat coconut oil in Manchatti on medium flame. Splutter mustard and fenugreek.",
                "Sauté shallots, ginger, garlic, and curry leaves till golden.",
                "Add spices dissolved in tamarind water. Bring to gentle bubbling simmer.",
                "Slide in fish pieces, swirl pot gently by handles (avoid stirring with hard spoons).",
                "Cover and simmer on low for 15 minutes. Best enjoyed the next day!"
            ),
            earthenBenefit = "Natural alkaline clay neutralizes excess harshness from sour tamarind, creating a mellow, deeply layered curry sauce impossible in stainless steel."
        ),
        ClayRecipe(
            id = "recipe_curd",
            title = "Velvety Artisanal Clay Pot Curd (Dahi)",
            potType = "Unglazed Clay Shallow Bowl or Matka",
            cookTime = "6-8 hours (Fermentation)",
            flameLevel = "No Direct Flame",
            description = "Natural probiotic thick yogurt set without gelatin or artificial thickeners. Clay naturally absorbs excess water whey.",
            ingredients = listOf(
                "1 Litre Whole Buffalo or Cow Milk (full fat)",
                "1 tbsp Fresh Live Culture Starter Curd"
            ),
            cookingSteps = listOf(
                "Boil milk in a separate pan and let cool until lukewarm (approx. 42°C / 108°F).",
                "Whisk starter curd and pour into the clean unglazed clay pot.",
                "Pour warm milk from a height to create light froth and mix with starter.",
                "Cover with a porous terracotta saucer and wrap in a clean warm towel.",
                "Leave undisturbed in a draft-free spot for 6-8 hours until set."
            ),
            earthenBenefit = "Earthen pores absorb roughly 10-15% excess water moisture from milk as it coagulates, producing restaurant-quality thick, spoonable yogurt with pleasant natural sweetness."
        ),
        ClayRecipe(
            id = "recipe_dal",
            title = "Panchmel Dal (Slow-Simmered Clay Lentils)",
            potType = "Deep Cooking Handi",
            cookTime = "50 mins",
            flameLevel = "Low Flame Only",
            description = "Five-lentil wholesome stew slow-melted into a buttery rich consistency. Clay preserves essential iron and phosphorus minerals.",
            ingredients = listOf(
                "1 cup Mixed Lentils (Toor, Moong, Chana, Urad, Masoor)",
                "3 cups Water, 1/2 tsp Turmeric & Rock Salt",
                "Tempering: Ghee, cumin seeds, asafoetida (hing), dry red chilies, crushed garlic"
            ),
            cookingSteps = listOf(
                "Add rinsed lentils, water, salt, and turmeric into the clay handi.",
                "Bring to gentle boil on low flame, skimming any white froth from surface.",
                "Lower flame, cover loosely, and simmer for 40 minutes until creamy soft.",
                "Prepare tempering (tadka) in a small clay ladle and pour sizzling over dal.",
                "Cover immediately for 5 minutes to trap aromatic smoke."
            ),
            earthenBenefit = "Slow simmer below boiling point keeps lentil starch proteins unbroken, delivering 100% nutrient bioavailability with zero stomach bloating."
        ),
        ClayRecipe(
            id = "recipe_kulhar_chai",
            title = "Tandoori Spiced Kulhar Chai",
            potType = "Raw Unglazed Clay Kulhar Cup",
            cookTime = "15 mins",
            flameLevel = "Special Smoky Infusion",
            description = "Strong milk tea poured into a red-hot roasted earthen cup, resulting in a volcanic froth that infuses rustic baked earth aroma.",
            ingredients = listOf(
                "2 cups Full Milk, 1 cup Water",
                "2 tbsp CTC Assam Tea Leaves",
                "Crushed Ginger, Green Cardamom, Cloves & Cinnamon",
                "Sugar or Jaggery to taste",
                "1 New Raw Unglazed Clay Kulhar Cup"
            ),
            cookingSteps = listOf(
                "Brew rich masala chai in your saucepan till deeply aromatic.",
                "Simultaneously, heat the empty raw clay kulhar directly over a medium gas flame with tongs for 7-10 minutes until sizzling hot.",
                "Place hot kulhar in a deep brass or stainless bowl.",
                "Strain boiling hot tea directly into the scorching kulhar. Watch it bubble up and caramelize!",
                "Pour back into serving cups and experience pure earthy bliss."
            ),
            earthenBenefit = "Instant contact with heated terracotta caramelizes milk sugars and releases 'geosmin' earthy aromatic compounds beloved across South Asia."
        )
    )
}

