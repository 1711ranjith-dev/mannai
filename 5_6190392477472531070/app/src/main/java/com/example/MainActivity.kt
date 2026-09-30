package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.data.MockData
import com.example.ui.components.LoginDialog
import com.example.ui.components.TerraMechBottomBar
import com.example.ui.components.TerraMechDrawerContent
import com.example.ui.components.TerraMechHeader
import com.example.ui.screens.AboutScreen
import com.example.ui.screens.AnalyzeScreen
import com.example.ui.screens.HistoryScreen
import com.example.ui.screens.HomeScreen
import com.example.ui.screens.PotGuideScreen
import com.example.ui.screens.RecommendationWizardScreen
import com.example.ui.theme.TerraMechTheme
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

    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val coroutineScope = rememberCoroutineScope()

    // Handle back button on secondary screens
    BackHandler(enabled = currentScreen != "home") {
        viewModel.navigateTo("home")
    }

    ModalNavigationDrawer(
        drawerState = drawerState,
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
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            topBar = {
                TerraMechHeader(
                    currentScreen = currentScreen,
                    isLoggedIn = isLoggedIn,
                    userEmail = userEmail,
                    onNavigate = { viewModel.navigateTo(it) },
                    onOpenLogin = { viewModel.openLoginModal() },
                    onOpenDrawer = {
                        coroutineScope.launch {
                            if (drawerState.isClosed) drawerState.open() else drawerState.close()
                        }
                    }
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
                            onNavigateToGuide = { viewModel.navigateTo("guide") }
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
        }
    }

    // Login modal
    LoginDialog(
        isOpen = showLoginModal,
        isLoggedIn = isLoggedIn,
        userEmail = userEmail,
        onDismiss = { viewModel.closeLoginModal() },
        onLogin = { email -> viewModel.performLogin(email) },
        onLogout = { viewModel.logout() }
    )
}
