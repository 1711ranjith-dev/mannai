package com.example.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.AppDatabase
import com.example.data.AnalysisRepository
import com.example.data.MockData
import com.example.model.AnalysisResult
import com.example.model.ClayPot
import com.example.model.PotCategory
import com.example.model.PotRecommendation
import com.example.model.RecommendationCriteria
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

sealed class AnalysisUiState {
    object Idle : AnalysisUiState()
    data class ImageSelected(val uri: String?, val presetKey: String?, val fileName: String, val fileSize: String) : AnalysisUiState()
    data class Analyzing(val stepIndex: Int, val currentStepLabel: String, val progress: Float) : AnalysisUiState()
    data class Success(val result: AnalysisResult) : AnalysisUiState()
    data class Error(val message: String) : AnalysisUiState()
}

class TerraMechViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: AnalysisRepository

    init {
        val database = AppDatabase.getInstance(application)
        repository = AnalysisRepository(database.analysisDao())
    }

    // Navigation State
    private val _currentScreen = MutableStateFlow("home")
    val currentScreen: StateFlow<String> = _currentScreen.asStateFlow()

    // Auth State (Demo Mode)
    private val _isLoggedIn = MutableStateFlow(false)
    val isLoggedIn: StateFlow<Boolean> = _isLoggedIn.asStateFlow()

    private val _userEmail = MutableStateFlow("")
    val userEmail: StateFlow<String> = _userEmail.asStateFlow()

    private val _showLoginModal = MutableStateFlow(false)
    val showLoginModal: StateFlow<Boolean> = _showLoginModal.asStateFlow()

    // Analysis State
    private val _analysisState = MutableStateFlow<AnalysisUiState>(AnalysisUiState.Idle)
    val analysisState: StateFlow<AnalysisUiState> = _analysisState.asStateFlow()

    private val _lastAnalyzedResult = MutableStateFlow<AnalysisResult?>(null)
    val lastAnalyzedResult: StateFlow<AnalysisResult?> = _lastAnalyzedResult.asStateFlow()

    // Pot Guide Catalog State
    private val _guideSearchQuery = MutableStateFlow("")
    val guideSearchQuery: StateFlow<String> = _guideSearchQuery.asStateFlow()

    private val _selectedGuideCategory = MutableStateFlow(PotCategory.ALL)
    val selectedGuideCategory: StateFlow<PotCategory> = _selectedGuideCategory.asStateFlow()

    private val _selectedPotDetail = MutableStateFlow<ClayPot?>(null)
    val selectedPotDetail: StateFlow<ClayPot?> = _selectedPotDetail.asStateFlow()

    // Recommendation Wizard State
    private val _wizardUsage = MutableStateFlow("Cooking")
    val wizardUsage: StateFlow<String> = _wizardUsage.asStateFlow()

    private val _wizardCapacity = MutableStateFlow("2–3 L")
    val wizardCapacity: StateFlow<String> = _wizardCapacity.asStateFlow()

    private val _wizardHeat = MutableStateFlow("Yes")
    val wizardHeat: StateFlow<String> = _wizardHeat.asStateFlow()

    private val _recommendationResults = MutableStateFlow<List<PotRecommendation>?>(null)
    val recommendationResults: StateFlow<List<PotRecommendation>?> = _recommendationResults.asStateFlow()

    private val _isFindingRecommendations = MutableStateFlow(false)
    val isFindingRecommendations: StateFlow<Boolean> = _isFindingRecommendations.asStateFlow()

    // History from Room DB
    val historyItems: StateFlow<List<AnalysisResult>> = repository.allHistory
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    private val _historySearchQuery = MutableStateFlow("")
    val historySearchQuery: StateFlow<String> = _historySearchQuery.asStateFlow()

    val analysisSteps = listOf(
        "Image uploaded successfully",
        "Validating pottery geometry and resolution",
        "Identifying traditional clay pot characteristics",
        "Estimating volumetric capacity",
        "Analyzing porous clay composition and finish",
        "Preparing safety & heat suitability protocol",
        "Generating smart recommendations"
    )

    fun navigateTo(screen: String) {
        _currentScreen.value = screen
    }

    fun openLoginModal() {
        _showLoginModal.value = true
    }

    fun closeLoginModal() {
        _showLoginModal.value = false
    }

    fun performLogin(email: String) {
        _userEmail.value = if (email.isBlank()) "artisan@terramech.app" else email
        _isLoggedIn.value = true
        _showLoginModal.value = false
    }

    fun logout() {
        _isLoggedIn.value = false
        _userEmail.value = ""
    }

    fun selectPresetImage(presetKey: String) {
        val preset = MockData.samplePresets.firstOrNull { it.key == presetKey } ?: MockData.samplePresets.first()
        _analysisState.value = AnalysisUiState.ImageSelected(
            uri = null,
            presetKey = preset.key,
            fileName = "${preset.title.replace(" ", "_").lowercase()}.jpg",
            fileSize = "1.8 MB"
        )
    }

    fun selectCustomImage(uriString: String) {
        _analysisState.value = AnalysisUiState.ImageSelected(
            uri = uriString,
            presetKey = null,
            fileName = "captured_clay_pot.jpg",
            fileSize = "2.4 MB"
        )
    }

    fun removeSelectedImage() {
        _analysisState.value = AnalysisUiState.Idle
    }

    fun startAnalysis() {
        val current = _analysisState.value
        if (current !is AnalysisUiState.ImageSelected) return

        viewModelScope.launch {
            // Animate through all 7 analysis steps
            for (index in analysisSteps.indices) {
                val progress = (index + 1).toFloat() / analysisSteps.size.toFloat()
                _analysisState.value = AnalysisUiState.Analyzing(
                    stepIndex = index,
                    currentStepLabel = analysisSteps[index],
                    progress = progress
                )
                delay(450)
            }

            // Generate analysis result
            val result = MockData.analyzePotImage(current.presetKey, current.fileName).copy(
                imageUri = current.uri
            )
            _lastAnalyzedResult.value = result
            _analysisState.value = AnalysisUiState.Success(result)

            // Persist into Room Database automatically
            repository.saveAnalysis(result)
        }
    }

    fun viewAnalysisResult(result: AnalysisResult) {
        _lastAnalyzedResult.value = result
        _analysisState.value = AnalysisUiState.Success(result)
        _currentScreen.value = "analyze"
    }

    fun deleteHistoryItem(id: String) {
        viewModelScope.launch {
            repository.deleteAnalysis(id)
        }
    }

    fun clearAllHistory() {
        viewModelScope.launch {
            repository.clearHistory()
        }
    }

    // Pot Guide actions
    fun setGuideSearchQuery(query: String) {
        _guideSearchQuery.value = query
    }

    fun setGuideCategory(category: PotCategory) {
        _selectedGuideCategory.value = category
    }

    fun openPotDetail(pot: ClayPot) {
        _selectedPotDetail.value = pot
    }

    fun closePotDetail() {
        _selectedPotDetail.value = null
    }

    // Recommendation Wizard actions
    fun setWizardUsage(usage: String) {
        _wizardUsage.value = usage
    }

    fun setWizardCapacity(capacity: String) {
        _wizardCapacity.value = capacity
    }

    fun setWizardHeat(heat: String) {
        _wizardHeat.value = heat
    }

    fun runRecommendationWizard() {
        viewModelScope.launch {
            _isFindingRecommendations.value = true
            delay(600)
            val criteria = RecommendationCriteria(
                usage = _wizardUsage.value,
                capacity = _wizardCapacity.value,
                heatNeeded = _wizardHeat.value
            )
            _recommendationResults.value = MockData.getRecommendations(criteria)
            _isFindingRecommendations.value = false
        }
    }

    fun setHistorySearchQuery(query: String) {
        _historySearchQuery.value = query
    }
}
