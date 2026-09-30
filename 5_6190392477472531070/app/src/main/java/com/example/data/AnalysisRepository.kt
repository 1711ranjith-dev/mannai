package com.example.data

import com.example.model.AnalysisResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class AnalysisRepository(private val dao: AnalysisDao) {

    val allHistory: Flow<List<AnalysisResult>> = dao.getAllHistory().map { entities ->
        entities.map { it.toDomainModel() }
    }

    suspend fun saveAnalysis(result: AnalysisResult) {
        dao.insertAnalysis(AnalysisEntity.fromDomainModel(result))
    }

    suspend fun deleteAnalysis(id: String) {
        dao.deleteById(id)
    }

    suspend fun clearHistory() {
        dao.clearAll()
    }
}
