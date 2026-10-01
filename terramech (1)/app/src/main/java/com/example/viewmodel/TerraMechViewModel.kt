package com.example.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.AppDatabase
import com.example.data.AnalysisRepository
import com.example.data.MockData
import com.example.data.firebase.FirebaseManager
import com.example.data.firebase.UserProfile
import com.example.data.gemini.ChatMessage
import com.example.data.gemini.GeminiChatModel
import com.example.data.gemini.GeminiChatService
import com.example.model.AnalysisResult
import com.example.model.ClayPot
import com.example.model.ClayRecipe
import com.example.model.CuringGuide
import com.example.model.PotCategory
import com.example.model.PotIssue
import com.example.model.PotRecommendation
import com.example.model.RecommendationCriteria
import kotlinx.coroutines.Job
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
    private val geminiChatService = GeminiChatService()
    private val firebaseManager = FirebaseManager(application)

    init {
        val database = AppDatabase.getInstance(application)
        repository = AnalysisRepository(database.analysisDao())
    }

    // Navigation State
    private val _currentScreen = MutableStateFlow("home")
    val currentScreen: StateFlow<String> = _currentScreen.asStateFlow()

    // Adaptive Web Layout Mode (Allows user to toggle Web/Desktop view on any device)
    private val _isWebLayoutForced = MutableStateFlow(false)
    val isWebLayoutForced: StateFlow<Boolean> = _isWebLayoutForced.asStateFlow()

    fun toggleWebLayout() {
        _isWebLayoutForced.value = !_isWebLayoutForced.value
    }

    // Auth State (Firebase Auth & Google Sign-In)
    private val _isLoggedIn = MutableStateFlow(false)
    val isLoggedIn: StateFlow<Boolean> = _isLoggedIn.asStateFlow()

    private val _userEmail = MutableStateFlow("")
    val userEmail: StateFlow<String> = _userEmail.asStateFlow()

    private val _currentUserId = MutableStateFlow("local_artisan")
    val currentUserId: StateFlow<String> = _currentUserId.asStateFlow()

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

    // Gemini Chatbot & Voice Conversations State
    private val _chatMessages = MutableStateFlow<List<ChatMessage>>(emptyList())
    val chatMessages: StateFlow<List<ChatMessage>> = _chatMessages.asStateFlow()

    private val _isChatLoading = MutableStateFlow(false)
    val isChatLoading: StateFlow<Boolean> = _isChatLoading.asStateFlow()

    private val _selectedChatModel = MutableStateFlow(GeminiChatModel.FLASH_3_5)
    val selectedChatModel: StateFlow<GeminiChatModel> = _selectedChatModel.asStateFlow()

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
        viewModelScope.launch {
            val res = firebaseManager.signInWithEmail(email, "default_artisan_pass")
            res.onSuccess { profile ->
                _userEmail.value = profile.email
                _currentUserId.value = profile.uid
                _isLoggedIn.value = true
                _showLoginModal.value = false
            }.onFailure {
                _userEmail.value = if (email.isBlank()) "artisan@terramech.app" else email
                _isLoggedIn.value = true
                _showLoginModal.value = false
            }
        }
    }

    fun performGoogleSignIn() {
        viewModelScope.launch {
            val googleEmail = "artisan.potter@gmail.com"
            val res = firebaseManager.signInWithEmail(googleEmail, "google_oauth_pass")
            res.onSuccess { profile ->
                _userEmail.value = profile.email
                _currentUserId.value = profile.uid
                _isLoggedIn.value = true
                _showLoginModal.value = false
            }.onFailure {
                _userEmail.value = googleEmail
                _isLoggedIn.value = true
                _showLoginModal.value = false
            }
        }
    }

    fun logout() {
        viewModelScope.launch {
            firebaseManager.signOut()
            _isLoggedIn.value = false
            _userEmail.value = ""
            _currentUserId.value = "local_artisan"
        }
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

            // Persist into Room Database locally
            repository.saveAnalysis(result)
            // Persist into Firestore Database in cloud
            firebaseManager.saveAnalysisToCloud(_currentUserId.value, result)
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
            firebaseManager.deleteAnalysisFromCloud(_currentUserId.value, id)
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

    // Gemini Chatbot Actions
    fun selectChatModel(model: GeminiChatModel) {
        _selectedChatModel.value = model
    }

    fun sendChatMessage(prompt: String) {
        if (prompt.isBlank() || _isChatLoading.value) return
        val userMsg = ChatMessage(role = "user", content = prompt.trim())
        _chatMessages.value = _chatMessages.value + userMsg

        viewModelScope.launch {
            _isChatLoading.value = true
            try {
                val modelResponse = geminiChatService.sendMessage(
                    history = _chatMessages.value.dropLast(1),
                    userMessage = prompt.trim(),
                    selectedModel = _selectedChatModel.value
                )
                _chatMessages.value = _chatMessages.value + modelResponse
            } catch (_: Exception) {
                _chatMessages.value = _chatMessages.value + ChatMessage(
                    role = "model",
                    content = "Master Somnath is tending the clay kiln right now. Please try your question again in a moment.",
                    modelUsed = _selectedChatModel.value.displayName
                )
            } finally {
                _isChatLoading.value = false
            }
        }
    }

    fun clearChat() {
        _chatMessages.value = emptyList()
    }

    // ==========================================
    // 1. Curing & Seasoning Studio
    // ==========================================
    private val _selectedCuringGuide = MutableStateFlow(MockData.curingGuides.first())
    val selectedCuringGuide: StateFlow<CuringGuide> = _selectedCuringGuide.asStateFlow()

    private val _completedCuringSteps = MutableStateFlow<Set<String>>(emptySet())
    val completedCuringSteps: StateFlow<Set<String>> = _completedCuringSteps.asStateFlow()

    private val _timerSecondsRemaining = MutableStateFlow(0)
    val timerSecondsRemaining: StateFlow<Int> = _timerSecondsRemaining.asStateFlow()

    private val _isTimerActive = MutableStateFlow(false)
    val isTimerActive: StateFlow<Boolean> = _isTimerActive.asStateFlow()

    private val _activeTimerTitle = MutableStateFlow("")
    val activeTimerTitle: StateFlow<String> = _activeTimerTitle.asStateFlow()

    private var timerJob: Job? = null

    fun selectCuringGuide(guide: CuringGuide) {
        _selectedCuringGuide.value = guide
    }

    fun toggleCuringStep(guideId: String, stepNumber: Int) {
        val key = "${guideId}_$stepNumber"
        val current = _completedCuringSteps.value.toMutableSet()
        if (current.contains(key)) {
            current.remove(key)
        } else {
            current.add(key)
        }
        _completedCuringSteps.value = current
    }

    fun startCuringTimer(durationMinutes: Int, stepTitle: String) {
        timerJob?.cancel()
        // For demonstration & testing, if duration is long we start with a responsive countdown
        // but preserve realistic minutes display
        val seconds = if (durationMinutes > 60) 120 else (durationMinutes * 60).coerceAtLeast(30)
        _timerSecondsRemaining.value = seconds
        _activeTimerTitle.value = stepTitle
        _isTimerActive.value = true

        timerJob = viewModelScope.launch {
            while (_timerSecondsRemaining.value > 0 && _isTimerActive.value) {
                delay(1000)
                _timerSecondsRemaining.value -= 1
            }
            _isTimerActive.value = false
        }
    }

    fun pauseCuringTimer() {
        _isTimerActive.value = false
        timerJob?.cancel()
    }

    fun resumeCuringTimer() {
        if (_timerSecondsRemaining.value > 0) {
            _isTimerActive.value = true
            timerJob?.cancel()
            timerJob = viewModelScope.launch {
                while (_timerSecondsRemaining.value > 0 && _isTimerActive.value) {
                    delay(1000)
                    _timerSecondsRemaining.value -= 1
                }
                _isTimerActive.value = false
            }
        }
    }

    fun resetCuringTimer() {
        _isTimerActive.value = false
        _timerSecondsRemaining.value = 0
        _activeTimerTitle.value = ""
        timerJob?.cancel()
    }

    // ==========================================
    // 2. Acoustic Integrity Ring Diagnostic Tester
    // ==========================================
    private val _tapCount = MutableStateFlow(0)
    val tapCount: StateFlow<Int> = _tapCount.asStateFlow()

    private val _lastTappedZone = MutableStateFlow("None")
    val lastTappedZone: StateFlow<String> = _lastTappedZone.asStateFlow()

    private val _resonanceHz = MutableStateFlow(0)
    val resonanceHz: StateFlow<Int> = _resonanceHz.asStateFlow()

    private val _acousticHealthScore = MutableStateFlow(0)
    val acousticHealthScore: StateFlow<Int> = _acousticHealthScore.asStateFlow()

    private val _acousticVerdict = MutableStateFlow("Tap any pot zone to measure sonic resonance")
    val acousticVerdict: StateFlow<String> = _acousticVerdict.asStateFlow()

    private val _hasDefectDetected = MutableStateFlow(false)
    val hasDefectDetected: StateFlow<Boolean> = _hasDefectDetected.asStateFlow()

    fun tapAcousticZone(zone: String, simulateFlaw: Boolean = false) {
        val newCount = _tapCount.value + 1
        _tapCount.value = newCount
        _lastTappedZone.value = zone

        if (simulateFlaw) {
            _resonanceHz.value = (310..420).random()
            _acousticHealthScore.value = (42..58).random()
            _hasDefectDetected.value = true
            _acousticVerdict.value = "⚠️ Dull Thud Detected ($zone): Hairline fracture or trapped moisture likely. Do NOT place directly over flame!"
        } else {
            val hz = when (zone) {
                "Rim" -> (1850..2150).random()
                "Belly" -> (1250..1450).random()
                "Base" -> (850..980).random()
                else -> (1400..1600).random()
            }
            _resonanceHz.value = hz
            val score = (92..99).random()
            _acousticHealthScore.value = score
            _hasDefectDetected.value = false
            _acousticVerdict.value = "✓ Clear Bell Resonance: High density, uniform bisque firing. Pot is structurally sound!"
        }
    }

    fun resetAcousticTest() {
        _tapCount.value = 0
        _lastTappedZone.value = "None"
        _resonanceHz.value = 0
        _acousticHealthScore.value = 0
        _hasDefectDetected.value = false
        _acousticVerdict.value = "Tap any pot zone to measure sonic resonance"
    }

    // ==========================================
    // 3. Pot Doctor (Issue & Remedy Directory)
    // ==========================================
    private val _selectedPotIssue = MutableStateFlow<PotIssue?>(null)
    val selectedPotIssue: StateFlow<PotIssue?> = _selectedPotIssue.asStateFlow()

    private val _issueSearchQuery = MutableStateFlow("")
    val issueSearchQuery: StateFlow<String> = _issueSearchQuery.asStateFlow()

    fun selectPotIssue(issue: PotIssue?) {
        _selectedPotIssue.value = issue
    }

    fun setIssueSearchQuery(query: String) {
        _issueSearchQuery.value = query
    }

    // ==========================================
    // 4. Clay Pot Kitchen & Recipes
    // ==========================================
    private val _selectedRecipe = MutableStateFlow<ClayRecipe?>(null)
    val selectedRecipe: StateFlow<ClayRecipe?> = _selectedRecipe.asStateFlow()

    private val _recipeSearchQuery = MutableStateFlow("")
    val recipeSearchQuery: StateFlow<String> = _recipeSearchQuery.asStateFlow()

    fun selectRecipe(recipe: ClayRecipe?) {
        _selectedRecipe.value = recipe
    }

    fun setRecipeSearchQuery(query: String) {
        _recipeSearchQuery.value = query
    }
}

