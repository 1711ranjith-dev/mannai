package com.example.model

enum class HeatSuitability(val displayName: String) {
    SUITABLE("Suitable"),
    LIMITED("Limited"),
    NOT_RECOMMENDED("Not Recommended"),
    UNKNOWN("Unknown")
}

enum class PotCategory(val displayName: String) {
    ALL("All Categories"),
    COOKING("Cooking"),
    WATER_STORAGE("Water Storage"),
    SERVING("Serving"),
    STORAGE("Storage"),
    FERMENTATION("Fermentation"),
    TRADITIONAL("Traditional"),
    DECORATIVE("Decorative")
}

data class ClayPot(
    val id: String,
    val name: String,
    val category: PotCategory,
    val description: String,
    val capacity: String,
    val material: String,
    val heatSuitability: HeatSuitability,
    val heatSuitabilityNote: String,
    val usage: List<String>,
    val safetyGuidelines: List<String>,
    val careAndMaintenance: List<String>,
    val seasoningInstructions: String,
    val origin: String,
    val presetKey: String
)

data class AnalysisResult(
    val id: String,
    val potType: String,
    val potTypeExplanation: String,
    val confidenceScore: Float,
    val estimatedCapacity: String,
    val capacityNote: String = "Estimated from image. Actual capacity may vary.",
    val material: String,
    val materialExplanation: String,
    val heatSuitability: HeatSuitability,
    val heatSuitabilityExplanation: String,
    val recommendedUsage: List<String>,
    val safetyGuidelines: List<String>,
    val careAndMaintenance: List<String>,
    val smartRecommendations: List<String>,
    val imageUri: String? = null,
    val presetKey: String? = null,
    val timestamp: Long = System.currentTimeMillis(),
    val shapeDescription: String = "Spherical curved body with flared rim"
)

data class RecommendationCriteria(
    val usage: String = "",
    val capacity: String = "",
    val heatNeeded: String = ""
)

data class PotRecommendation(
    val pot: ClayPot,
    val matchScore: Int,
    val matchReason: String
)

data class CuringStep(
    val stepNumber: Int,
    val title: String,
    val durationMinutes: Int, // 0 if untimed
    val instruction: String,
    val proTip: String
)

data class CuringGuide(
    val id: String,
    val potType: String,
    val summary: String,
    val estimatedDays: String,
    val recommendedOil: String,
    val steps: List<CuringStep>,
    val goldenRule: String
)

data class PotIssue(
    val id: String,
    val title: String,
    val symptom: String,
    val cause: String,
    val remedySteps: List<String>,
    val preventionTip: String,
    val severity: String // "Mild", "Moderate", "Critical"
)

data class ClayRecipe(
    val id: String,
    val title: String,
    val potType: String,
    val cookTime: String,
    val flameLevel: String, // "Low Flame Only", "Low-to-Medium", "No Direct Flame"
    val description: String,
    val ingredients: List<String>,
    val cookingSteps: List<String>,
    val earthenBenefit: String
)

