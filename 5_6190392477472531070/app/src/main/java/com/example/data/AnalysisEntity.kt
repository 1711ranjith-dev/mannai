package com.example.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.model.AnalysisResult
import com.example.model.HeatSuitability

@Entity(tableName = "analysis_history")
data class AnalysisEntity(
    @PrimaryKey
    val id: String,
    val potType: String,
    val potTypeExplanation: String,
    val confidenceScore: Float,
    val estimatedCapacity: String,
    val capacityNote: String,
    val material: String,
    val materialExplanation: String,
    val heatSuitability: String,
    val heatSuitabilityExplanation: String,
    val recommendedUsageCsv: String,
    val safetyGuidelinesCsv: String,
    val careMaintenanceCsv: String,
    val smartRecommendationsCsv: String,
    val imageUri: String?,
    val presetKey: String?,
    val timestamp: Long,
    val shapeDescription: String
) {
    fun toDomainModel(): AnalysisResult {
        val heat = try {
            HeatSuitability.valueOf(heatSuitability)
        } catch (_: Exception) {
            HeatSuitability.UNKNOWN
        }

        return AnalysisResult(
            id = id,
            potType = potType,
            potTypeExplanation = potTypeExplanation,
            confidenceScore = confidenceScore,
            estimatedCapacity = estimatedCapacity,
            capacityNote = capacityNote,
            material = material,
            materialExplanation = materialExplanation,
            heatSuitability = heat,
            heatSuitabilityExplanation = heatSuitabilityExplanation,
            recommendedUsage = if (recommendedUsageCsv.isBlank()) emptyList() else recommendedUsageCsv.split("||"),
            safetyGuidelines = if (safetyGuidelinesCsv.isBlank()) emptyList() else safetyGuidelinesCsv.split("||"),
            careAndMaintenance = if (careMaintenanceCsv.isBlank()) emptyList() else careMaintenanceCsv.split("||"),
            smartRecommendations = if (smartRecommendationsCsv.isBlank()) emptyList() else smartRecommendationsCsv.split("||"),
            imageUri = imageUri,
            presetKey = presetKey,
            timestamp = timestamp,
            shapeDescription = shapeDescription
        )
    }

    companion object {
        fun fromDomainModel(model: AnalysisResult): AnalysisEntity {
            return AnalysisEntity(
                id = model.id,
                potType = model.potType,
                potTypeExplanation = model.potTypeExplanation,
                confidenceScore = model.confidenceScore,
                estimatedCapacity = model.estimatedCapacity,
                capacityNote = model.capacityNote,
                material = model.material,
                materialExplanation = model.materialExplanation,
                heatSuitability = model.heatSuitability.name,
                heatSuitabilityExplanation = model.heatSuitabilityExplanation,
                recommendedUsageCsv = model.recommendedUsage.joinToString("||"),
                safetyGuidelinesCsv = model.safetyGuidelines.joinToString("||"),
                careMaintenanceCsv = model.careAndMaintenance.joinToString("||"),
                smartRecommendationsCsv = model.smartRecommendations.joinToString("||"),
                imageUri = model.imageUri,
                presetKey = model.presetKey,
                timestamp = model.timestamp,
                shapeDescription = model.shapeDescription
            )
        }
    }
}
