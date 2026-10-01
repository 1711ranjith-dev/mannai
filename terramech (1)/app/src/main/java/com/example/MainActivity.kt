package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.data.MockData
import com.example.ui.components.LoginDialog
import com.example.ui.components.TerraMechBottomBar
import com.example.ui.components.TerraMechDrawerContent
import com.example.ui.components.TerraMechHeader
import com.example.ui.components.TerraMechWebNavRail
import com.example.ui.screens.AboutScreen
import com.example.ui.screens.AnalyzeScreen
import com.example.ui.screens.ChatScreen
import com.example.ui.screens.ClayKitchenScreen
import com.example.ui.screens.CuringStudioScreen
import com.example.ui.screens.HistoryScreen
import com.example.ui.screens.HomeScreen
import com.example.ui.screens.PotDoctorScreen
import com.example.ui.screens.PotGuideScreen
import com.example.ui.screens.RecommendationWizardScreen
import com.example.ui.theme.TerraMechTheme
import com.example.data.gemini.ChatMessage
import com.example.data.gemini.GeminiChatModel
import com.example.model.AnalysisResult
import com.example.model.ClayPot
import com.example.model.ClayRecipe
import com.example.model.CuringGuide
import com.example.model.PotCategory
import com.example.model.PotIssue
import com.example.model.PotRecommendation
import com.example.viewmodel.AnalysisUiState
import com.example.viewmodel.TerraMechViewModel
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TerraMechTheme {
                TerraMechApp()
            }
        }
    }
}

@Composable
fun TerraMechApp(
    viewModel: TerraMechViewModel = viewModel()
) {
    val currentScreen by viewModel.currentScreen.collectAsState()
    val isLoggedIn by viewModel.isLoggedIn.collectAsState()
    val userEmail by viewModel.userEmail.collectAsState()
    val showLoginModal by viewModel.showLoginModal.collectAsState()

    val analysisState by viewModel.analysisState.collectAsState()
    val historyItems by viewModel.historyItems.collectAsState()
    val historySearchQuery by viewModel.historySearchQuery.collectAsState()

    val guideSearchQuery by viewModel.guideSearchQuery.collectAsState()
    val selectedGuideCategory by viewModel.selectedGuideCategory.collectAsState()
    val selectedPotDetail by viewModel.selectedPotDetail.collectAsState()

    val wizardUsage by viewModel.wizardUsage.collectAsState()
    val wizardCapacity by viewModel.wizardCapacity.collectAsState()
    val wizardHeat by viewModel.wizardHeat.collectAsState()
    val recommendationResults by viewModel.recommendationResults.collectAsState()
    val isFindingRecommendations by viewModel.isFindingRecommendations.collectAsState()

    val chatMessages by viewModel.chatMessages.collectAsState()
    val isChatLoading by viewModel.isChatLoading.collectAsState()
    val selectedChatModel by viewModel.selectedChatModel.collectAsState()

    // Curing Studio State
    val selectedCuringGuide by viewModel.selectedCuringGuide.collectAsState()
    val completedCuringSteps by viewModel.completedCuringSteps.collectAsState()
    val timerSecondsRemaining by viewModel.timerSecondsRemaining.collectAsState()
    val isTimerActive by viewModel.isTimerActive.collectAsState()
    val activeTimerTitle by viewModel.activeTimerTitle.collectAsState()

    // Pot Doctor State
    val selectedPotIssue by viewModel.selectedPotIssue.collectAsState()
    val issueSearchQuery by viewModel.issueSearchQuery.collectAsState()
    val tapCount by viewModel.tapCount.collectAsState()
    val lastTappedZone by viewModel.lastTappedZone.collectAsState()
    val resonanceHz by viewModel.resonanceHz.collectAsState()
    val acousticHealthScore by viewModel.acousticHealthScore.collectAsState()
    val acousticVerdict by viewModel.acousticVerdict.collectAsState()
    val hasDefectDetected by viewModel.hasDefectDetected.collectAsState()

    // Clay Kitchen State
    val selectedRecipe by viewModel.selectedRecipe.collectAsState()
    val recipeSearchQuery by viewModel.recipeSearchQuery.collectAsState()
    val isWebLayoutForced by viewModel.isWebLayoutForced.collectAsState()

    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val coroutineScope = rememberCoroutineScope()

    // Handle back button on secondary screens
    BackHandler(enabled = currentScreen != "home") {
        viewModel.navigateTo("home")
    }

    BoxWithConstraints(modifier = Modifier.fillMaxSize()) {
        val isWideScreen = isWebLayoutForced || maxWidth >= 720.dp

        ModalNavigationDrawer(
            drawerState = drawerState,
            gesturesEnabled = !isWideScreen,
            drawerContent = {
                TerraMechDrawerContent(
                    currentScreen = currentScreen,
                    onNavigate = { route ->
                        viewModel.navigateTo(route)
                    },
                    onCloseDrawer = {
                        coroutineScope.launch { drawerState.close() }
                    }
                )
            }
        ) {
            if (isWideScreen) {
                // Adaptive Desktop Web Application Layout
                Row(modifier = Modifier.fillMaxSize()) {
                    TerraMechWebNavRail(
                        currentScreen = currentScreen,
                        onNavigate = { viewModel.navigateTo(it) }
                    )
                    Scaffold(
                        modifier = Modifier.weight(1f),
                        topBar = {
                            TerraMechHeader(
                                currentScreen = currentScreen,
                                isLoggedIn = isLoggedIn,
                                userEmail = userEmail,
                                isWideScreen = true,
                                onNavigate = { viewModel.navigateTo(it) },
                                onOpenLogin = { viewModel.openLoginModal() },
                                onOpenDrawer = {
                                    coroutineScope.launch {
                                        if (drawerState.isClosed) drawerState.open() else drawerState.close()
                                    }
                                },
                                onToggleWebLayout = { viewModel.toggleWebLayout() }
                            )
                        }
                    ) { innerPadding ->
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(innerPadding),
                            contentAlignment = Alignment.TopCenter
                        ) {
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .widthIn(max = 1240.dp)
                            ) {
                                MainContentRouter(
                                    currentScreen = currentScreen,
                                    viewModel = viewModel,
                                    analysisState = analysisState,
                                    guideSearchQuery = guideSearchQuery,
                                    selectedGuideCategory = selectedGuideCategory,
                                    selectedPotDetail = selectedPotDetail,
                                    historyItems = historyItems,
                                    historySearchQuery = historySearchQuery,
                                    wizardUsage = wizardUsage,
                                    wizardCapacity = wizardCapacity,
                                    wizardHeat = wizardHeat,
                                    recommendationResults = recommendationResults,
                                    isFindingRecommendations = isFindingRecommendations,
                                    chatMessages = chatMessages,
                                    isChatLoading = isChatLoading,
                                    selectedChatModel = selectedChatModel,
                                    selectedCuringGuide = selectedCuringGuide,
                                    completedCuringSteps = completedCuringSteps,
                                    timerSecondsRemaining = timerSecondsRemaining,
                                    isTimerActive = isTimerActive,
                                    activeTimerTitle = activeTimerTitle,
                                    selectedPotIssue = selectedPotIssue,
                                    issueSearchQuery = issueSearchQuery,
                                    tapCount = tapCount,
                                    lastTappedZone = lastTappedZone,
                                    resonanceHz = resonanceHz,
                                    acousticHealthScore = acousticHealthScore,
                                    acousticVerdict = acousticVerdict,
                                    hasDefectDetected = hasDefectDetected,
                                    selectedRecipe = selectedRecipe,
                                    recipeSearchQuery = recipeSearchQuery
                                )
                            }
                        }
                    }
                }
            } else {
                // Mobile Handheld Layout
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    topBar = {
                        TerraMechHeader(
                            currentScreen = currentScreen,
                            isLoggedIn = isLoggedIn,
                            userEmail = userEmail,
                            isWideScreen = false,
                            onNavigate = { viewModel.navigateTo(it) },
                            onOpenLogin = { viewModel.openLoginModal() },
                            onOpenDrawer = {
                                coroutineScope.launch {
                                    if (drawerState.isClosed) drawerState.open() else drawerState.close()
                                }
                            },
                            onToggleWebLayout = { viewModel.toggleWebLayout() }
                        )
                    },
                    bottomBar = {
                        TerraMechBottomBar(
                            currentScreen = currentScreen,
                            onNavigate = { viewModel.navigateTo(it) }
                        )
                    }
                ) { innerPadding ->
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding)
                    ) {
                        MainContentRouter(
                            currentScreen = currentScreen,
                            viewModel = viewModel,
                            analysisState = analysisState,
                            guideSearchQuery = guideSearchQuery,
                            selectedGuideCategory = selectedGuideCategory,
                            selectedPotDetail = selectedPotDetail,
                            historyItems = historyItems,
                            historySearchQuery = historySearchQuery,
                            wizardUsage = wizardUsage,
                            wizardCapacity = wizardCapacity,
                            wizardHeat = wizardHeat,
                            recommendationResults = recommendationResults,
                            isFindingRecommendations = isFindingRecommendations,
                            chatMessages = chatMessages,
                            isChatLoading = isChatLoading,
                            selectedChatModel = selectedChatModel,
                            selectedCuringGuide = selectedCuringGuide,
                            completedCuringSteps = completedCuringSteps,
                            timerSecondsRemaining = timerSecondsRemaining,
                            isTimerActive = isTimerActive,
                            activeTimerTitle = activeTimerTitle,
                            selectedPotIssue = selectedPotIssue,
                            issueSearchQuery = issueSearchQuery,
                            tapCount = tapCount,
                            lastTappedZone = lastTappedZone,
                            resonanceHz = resonanceHz,
                            acousticHealthScore = acousticHealthScore,
                            acousticVerdict = acousticVerdict,
                            hasDefectDetected = hasDefectDetected,
                            selectedRecipe = selectedRecipe,
                            recipeSearchQuery = recipeSearchQuery
                        )
                    }
                }
            }
        }
    }

    // Login modal with Firebase & Google Sign-In
    LoginDialog(
        isOpen = showLoginModal,
        isLoggedIn = isLoggedIn,
        userEmail = userEmail,
        onDismiss = { viewModel.closeLoginModal() },
        onLogin = { email -> viewModel.performLogin(email) },
        onGoogleSignIn = { viewModel.performGoogleSignIn() },
        onLogout = { viewModel.logout() }
    )
}

@Composable
private fun MainContentRouter(
    currentScreen: String,
    viewModel: TerraMechViewModel,
    analysisState: AnalysisUiState,
    guideSearchQuery: String,
    selectedGuideCategory: PotCategory,
    selectedPotDetail: ClayPot?,
    historyItems: List<AnalysisResult>,
    historySearchQuery: String,
    wizardUsage: String,
    wizardCapacity: String,
    wizardHeat: String,
    recommendationResults: List<PotRecommendation>?,
    isFindingRecommendations: Boolean,
    chatMessages: List<ChatMessage>,
    isChatLoading: Boolean,
    selectedChatModel: GeminiChatModel,
    selectedCuringGuide: CuringGuide,
    completedCuringSteps: Set<String>,
    timerSecondsRemaining: Int,
    isTimerActive: Boolean,
    activeTimerTitle: String,
    selectedPotIssue: PotIssue?,
    issueSearchQuery: String,
    tapCount: Int,
    lastTappedZone: String,
    resonanceHz: Int,
    acousticHealthScore: Int,
    acousticVerdict: String,
    hasDefectDetected: Boolean,
    selectedRecipe: ClayRecipe?,
    recipeSearchQuery: String
) {
    Crossfade(targetState = currentScreen, label = "ScreenTransition") { screen ->
        when (screen) {
            "home" -> HomeScreen(
                onNavigate = { viewModel.navigateTo(it) },
                onSelectPresetAndAnalyze = { presetKey ->
                    viewModel.selectPresetImage(presetKey)
                }
            )

            "analyze" -> AnalyzeScreen(
                uiState = analysisState,
                analysisSteps = viewModel.analysisSteps,
                onSelectPreset = { presetKey -> viewModel.selectPresetImage(presetKey) },
                onSelectCustomImage = { uri -> viewModel.selectCustomImage(uri) },
                onRemoveImage = { viewModel.removeSelectedImage() },
                onStartAnalysis = { viewModel.startAnalysis() },
                onNavigateToGuide = { viewModel.navigateTo("guide") },
                onNavigateToCuring = { viewModel.navigateTo("curing") },
                onNavigateToDoctor = { viewModel.navigateTo("doctor") },
                onNavigateToKitchen = { viewModel.navigateTo("kitchen") }
            )

            "chat" -> ChatScreen(
                messages = chatMessages,
                isLoading = isChatLoading,
                selectedModel = selectedChatModel,
                onSelectModel = { viewModel.selectChatModel(it) },
                onSendMessage = { viewModel.sendChatMessage(it) },
                onClearChat = { viewModel.clearChat() }
            )

            "guide" -> PotGuideScreen(
                pots = MockData.potsCatalog,
                searchQuery = guideSearchQuery,
                selectedCategory = selectedGuideCategory,
                selectedPotDetail = selectedPotDetail,
                onSearchChange = { viewModel.setGuideSearchQuery(it) },
                onCategorySelect = { viewModel.setGuideCategory(it) },
                onOpenPotDetail = { viewModel.openPotDetail(it) },
                onClosePotDetail = { viewModel.closePotDetail() },
                onAnalyzePreset = { presetKey ->
                    viewModel.selectPresetImage(presetKey)
                    viewModel.navigateTo("analyze")
                }
            )

            "recommend" -> RecommendationWizardScreen(
                selectedUsage = wizardUsage,
                selectedCapacity = wizardCapacity,
                selectedHeat = wizardHeat,
                results = recommendationResults,
                isLoading = isFindingRecommendations,
                onUsageChange = { viewModel.setWizardUsage(it) },
                onCapacityChange = { viewModel.setWizardCapacity(it) },
                onHeatChange = { viewModel.setWizardHeat(it) },
                onFindPot = { viewModel.runRecommendationWizard() },
                onOpenPotDetail = { viewModel.openPotDetail(it) }
            )

            "history" -> HistoryScreen(
                historyItems = historyItems,
                searchQuery = historySearchQuery,
                onSearchChange = { viewModel.setHistorySearchQuery(it) },
                onViewResult = { result ->
                    viewModel.viewAnalysisResult(result)
                },
                onDeleteItem = { id -> viewModel.deleteHistoryItem(id) },
                onClearAll = { viewModel.clearAllHistory() },
                onNavigateToAnalyze = { viewModel.navigateTo("analyze") }
            )

            "curing" -> CuringStudioScreen(
                guides = MockData.curingGuides,
                selectedGuide = selectedCuringGuide,
                completedSteps = completedCuringSteps,
                timerSecondsRemaining = timerSecondsRemaining,
                isTimerActive = isTimerActive,
                activeTimerTitle = activeTimerTitle,
                onSelectGuide = { viewModel.selectCuringGuide(it) },
                onToggleStep = { guideId, step -> viewModel.toggleCuringStep(guideId, step) },
                onStartTimer = { mins, title -> viewModel.startCuringTimer(mins, title) },
                onPauseTimer = { viewModel.pauseCuringTimer() },
                onResumeTimer = { viewModel.resumeCuringTimer() },
                onResetTimer = { viewModel.resetCuringTimer() },
                onAskSage = { question ->
                    viewModel.sendChatMessage(question)
                    viewModel.navigateTo("chat")
                }
            )

            "doctor" -> PotDoctorScreen(
                issues = MockData.potIssues,
                searchQuery = issueSearchQuery,
                selectedIssue = selectedPotIssue,
                tapCount = tapCount,
                lastTappedZone = lastTappedZone,
                resonanceHz = resonanceHz,
                healthScore = acousticHealthScore,
                verdictMessage = acousticVerdict,
                hasDefect = hasDefectDetected,
                onSearchChange = { viewModel.setIssueSearchQuery(it) },
                onSelectIssue = { viewModel.selectPotIssue(it) },
                onTapZone = { zone, simulateFlaw -> viewModel.tapAcousticZone(zone, simulateFlaw) },
                onResetAcousticTest = { viewModel.resetAcousticTest() },
                onAskSage = { question ->
                    viewModel.sendChatMessage(question)
                    viewModel.navigateTo("chat")
                }
            )

            "kitchen" -> ClayKitchenScreen(
                recipes = MockData.clayRecipes,
                searchQuery = recipeSearchQuery,
                selectedRecipe = selectedRecipe,
                onSearchChange = { viewModel.setRecipeSearchQuery(it) },
                onSelectRecipe = { viewModel.selectRecipe(it) },
                onCookWithPot = { potName ->
                    viewModel.setGuideSearchQuery(potName)
                    viewModel.navigateTo("guide")
                },
                onAskSage = { question ->
                    viewModel.sendChatMessage(question)
                    viewModel.navigateTo("chat")
                }
            )

            "about" -> AboutScreen()

            else -> HomeScreen(
                onNavigate = { viewModel.navigateTo(it) },
                onSelectPresetAndAnalyze = { presetKey ->
                    viewModel.selectPresetImage(presetKey)
                }
            )
        }
    }
}

