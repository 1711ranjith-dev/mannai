package com.example.ui.screens

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.HelpOutline
import androidx.compose.material.icons.automirrored.filled.MenuBook
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.CleaningServices
import androidx.compose.material.icons.filled.CloudUpload
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Explore
import androidx.compose.material.icons.filled.Hearing
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.material.icons.filled.LocalFireDepartment
import androidx.compose.material.icons.filled.PhotoLibrary
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Restaurant
import androidx.compose.material.icons.filled.Science
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.SquareFoot
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.data.MockData
import com.example.model.AnalysisResult
import com.example.model.HeatSuitability
import com.example.ui.components.ClayPotIllustration
import com.example.ui.theme.ClayBorder
import com.example.ui.theme.ClayEarthyBrown
import com.example.ui.theme.ClayWarmBrown
import com.example.ui.theme.OrganicSageContainer
import com.example.ui.theme.OrganicSageGreen
import com.example.ui.theme.OrganicSageLight
import com.example.ui.theme.StatusLimited
import com.example.ui.theme.StatusNotRecommended
import com.example.ui.theme.StatusSuitable
import com.example.ui.theme.StatusUnknown
import com.example.ui.theme.TerracottaContainer
import com.example.ui.theme.TerracottaLight
import com.example.ui.theme.TerracottaPrimary
import com.example.ui.theme.WarmCreamBg
import com.example.ui.theme.WarmCreamCard
import com.example.ui.theme.WarmCreamSurface
import com.example.viewmodel.AnalysisUiState

@Composable
fun AnalyzeScreen(
    uiState: AnalysisUiState,
    analysisSteps: List<String>,
    onSelectPreset: (String) -> Unit,
    onSelectCustomImage: (String) -> Unit,
    onRemoveImage: () -> Unit,
    onStartAnalysis: () -> Unit,
    onNavigateToGuide: () -> Unit,
    onNavigateToCuring: () -> Unit = {},
    onNavigateToDoctor: () -> Unit = {},
    onNavigateToKitchen: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    val photoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia()
    ) { uri: Uri? ->
        if (uri != null) {
            onSelectCustomImage(uri.toString())
        }
    }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(WarmCreamBg)
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Spacer(modifier = Modifier.height(4.dp))
            // Screen Header
            Column(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = "Analyze Your Clay Pot",
                    style = MaterialTheme.typography.headlineMedium.copy(
                        fontWeight = FontWeight.Bold,
                        color = ClayEarthyBrown
                    )
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Upload a clear image of your clay pot to get smart insights, thermal limits, and care protocols.",
                    style = MaterialTheme.typography.bodyMedium.copy(color = ClayWarmBrown)
                )
            }
        }

        when (uiState) {
            is AnalysisUiState.Idle -> {
                item {
                    UploadSection(
                        onPickFromGallery = {
                            photoPickerLauncher.launch(
                                PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                            )
                        },
                        onSelectPreset = onSelectPreset
                    )
                }
            }

            is AnalysisUiState.ImageSelected -> {
                item {
                    ImagePreviewSection(
                        state = uiState,
                        onAnalyze = onStartAnalysis,
                        onChangeImage = {
                            photoPickerLauncher.launch(
                                PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                            )
                        },
                        onRemove = onRemoveImage
                    )
                }
            }

            is AnalysisUiState.Analyzing -> {
                item {
                    AnalyzingProgressSection(
                        state = uiState,
                        steps = analysisSteps
                    )
                }
            }

            is AnalysisUiState.Success -> {
                item {
                    AnalysisResultDashboard(
                        result = uiState.result,
                        onAnalyzeAnother = onRemoveImage,
                        onNavigateToGuide = onNavigateToGuide,
                        onNavigateToCuring = onNavigateToCuring,
                        onNavigateToDoctor = onNavigateToDoctor,
                        onNavigateToKitchen = onNavigateToKitchen
                    )
                }
            }

            is AnalysisUiState.Error -> {
                item {
                    ErrorStateSection(
                        message = uiState.message,
                        onRetry = onRemoveImage
                    )
                }
            }
        }

        item {
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Composable
private fun UploadSection(
    onPickFromGallery: () -> Unit,
    onSelectPreset: (String) -> Unit
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        // Drag-and-drop / Tap upload box
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(24.dp))
                .border(2.dp, TerracottaPrimary.copy(alpha = 0.4f), RoundedCornerShape(24.dp))
                .clickable { onPickFromGallery() }
                .testTag("upload_drop_box"),
            colors = CardDefaults.cardColors(containerColor = WarmCreamCard),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(32.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(
                    modifier = Modifier
                        .size(72.dp)
                        .clip(CircleShape)
                        .background(TerracottaContainer),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.CloudUpload,
                        contentDescription = "Upload",
                        tint = TerracottaPrimary,
                        modifier = Modifier.size(36.dp)
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "Tap to Browse or Take Photo",
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Bold,
                        color = ClayEarthyBrown
                    )
                )

                Spacer(modifier = Modifier.height(6.dp))

                Text(
                    text = "Supported formats: JPG, JPEG, PNG, WEBP (Max 15MB)",
                    style = MaterialTheme.typography.bodySmall.copy(color = ClayWarmBrown),
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = onPickFromGallery,
                    colors = ButtonDefaults.buttonColors(containerColor = TerracottaPrimary),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.testTag("browse_image_button")
                ) {
                    Icon(Icons.Default.PhotoLibrary, contentDescription = null, modifier = Modifier.size(18.dp))
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Select from Photos")
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Curated Presets
        Text(
            text = "Or Test with Sample Earthenware",
            style = MaterialTheme.typography.titleSmall.copy(
                fontWeight = FontWeight.Bold,
                color = ClayEarthyBrown
            )
        )
        Text(
            text = "Tap any preset below to simulate instant real-device pottery analysis",
            style = MaterialTheme.typography.bodySmall.copy(color = ClayWarmBrown)
        )

        Spacer(modifier = Modifier.height(12.dp))

        MockData.samplePresets.forEach { preset ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp)
                    .clickable { onSelectPreset(preset.key) }
                    .testTag("sample_preset_${preset.key}"),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = WarmCreamCard),
                elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    ClayPotIllustration(
                        presetKey = preset.key,
                        isScanning = false,
                        size = 56.dp
                    )
                    Spacer(modifier = Modifier.width(14.dp))
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = preset.title,
                            style = MaterialTheme.typography.titleSmall.copy(
                                fontWeight = FontWeight.Bold,
                                color = ClayEarthyBrown
                            )
                        )
                        Text(
                            text = preset.subtitle,
                            style = MaterialTheme.typography.bodySmall.copy(
                                color = ClayWarmBrown,
                                fontSize = 11.sp
                            )
                        )
                    }
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(8.dp))
                            .background(TerracottaContainer.copy(alpha = 0.6f))
                            .padding(horizontal = 8.dp, vertical = 4.dp)
                    ) {
                        Text(
                            text = preset.estimatedCapacity,
                            style = MaterialTheme.typography.labelSmall.copy(
                                fontWeight = FontWeight.Bold,
                                color = TerracottaPrimary
                            )
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun ImagePreviewSection(
    state: AnalysisUiState.ImageSelected,
    onAnalyze: () -> Unit,
    onChangeImage: () -> Unit,
    onRemove: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .testTag("image_preview_card"),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = WarmCreamCard),
        elevation = CardDefaults.cardElevation(defaultElevation = 3.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Pot Image Preview",
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.Bold,
                    color = ClayEarthyBrown
                )
            )

            Spacer(modifier = Modifier.height(14.dp))

            // Display Image or Canvas Representation
            Box(
                modifier = Modifier
                    .size(240.dp)
                    .clip(RoundedCornerShape(20.dp))
                    .background(WarmCreamSurface),
                contentAlignment = Alignment.Center
            ) {
                if (state.uri != null) {
                    val context = LocalContext.current
                    AsyncImage(
                        model = ImageRequest.Builder(context)
                            .data(state.uri)
                            .crossfade(true)
                            .build(),
                        contentDescription = "Uploaded clay pot",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )
                } else {
                    ClayPotIllustration(
                        presetKey = state.presetKey,
                        isScanning = false,
                        size = 240.dp
                    )
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            // File metadata info
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(12.dp))
                    .background(WarmCreamSurface)
                    .padding(horizontal = 14.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = state.fileName,
                        style = MaterialTheme.typography.labelMedium.copy(
                            fontWeight = FontWeight.Bold,
                            color = ClayEarthyBrown
                        )
                    )
                    Text(
                        text = state.fileSize,
                        style = MaterialTheme.typography.bodySmall.copy(color = ClayWarmBrown)
                    )
                }
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(6.dp))
                        .background(OrganicSageContainer)
                        .padding(horizontal = 8.dp, vertical = 2.dp)
                ) {
                    Text(
                        text = "Ready to Scan",
                        style = MaterialTheme.typography.labelSmall.copy(
                            color = OrganicSageGreen,
                            fontWeight = FontWeight.Bold
                        )
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Action Buttons
            Button(
                onClick = onAnalyze,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
                    .testTag("analyze_pot_button"),
                colors = ButtonDefaults.buttonColors(containerColor = TerracottaPrimary),
                shape = RoundedCornerShape(14.dp)
            ) {
                Icon(Icons.Default.AutoAwesome, contentDescription = null, modifier = Modifier.size(20.dp))
                Spacer(modifier = Modifier.width(8.dp))
                Text("Analyze Pot", style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold))
            }

            Spacer(modifier = Modifier.height(10.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                OutlinedButton(
                    onClick = onChangeImage,
                    modifier = Modifier
                        .weight(1f)
                        .testTag("change_image_button"),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Icon(Icons.Default.Refresh, contentDescription = null, modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(6.dp))
                    Text("Change")
                }

                OutlinedButton(
                    onClick = onRemove,
                    modifier = Modifier
                        .weight(1f)
                        .testTag("remove_image_button"),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = Color(0xFFC62828))
                ) {
                    Icon(Icons.Default.Delete, contentDescription = null, modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(6.dp))
                    Text("Remove")
                }
            }
        }
    }
}

@Composable
private fun AnalyzingProgressSection(
    state: AnalysisUiState.Analyzing,
    steps: List<String>
) {
    val animatedProgress by animateFloatAsState(
        targetValue = state.progress,
        animationSpec = tween(400, easing = LinearEasing),
        label = "progress"
    )

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .testTag("analyzing_progress_card"),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = WarmCreamCard),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Visual pot with active scan laser
            ClayPotIllustration(
                presetKey = "handi",
                isScanning = true,
                size = 180.dp
            )

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = "Analyzing Your Pot...",
                style = MaterialTheme.typography.headlineSmall.copy(
                    fontWeight = FontWeight.Bold,
                    color = TerracottaPrimary
                )
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = state.currentStepLabel,
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = ClayWarmBrown,
                    fontWeight = FontWeight.Medium
                ),
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(16.dp))

            LinearProgressIndicator(
                progress = { animatedProgress },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(8.dp)
                    .clip(RoundedCornerShape(4.dp)),
                color = TerracottaPrimary,
                trackColor = WarmCreamSurface,
                strokeCap = StrokeCap.Round
            )

            Spacer(modifier = Modifier.height(20.dp))

            // Step Progress Checklist
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp))
                    .background(WarmCreamSurface)
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                steps.forEachIndexed { index, stepText ->
                    val isDone = index < state.stepIndex
                    val isCurrent = index == state.stepIndex

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        when {
                            isDone -> {
                                Icon(
                                    imageVector = Icons.Default.CheckCircle,
                                    contentDescription = "Completed",
                                    tint = OrganicSageGreen,
                                    modifier = Modifier.size(18.dp)
                                )
                            }
                            isCurrent -> {
                                CircularProgressIndicator(
                                    modifier = Modifier.size(18.dp),
                                    strokeWidth = 2.dp,
                                    color = TerracottaPrimary
                                )
                            }
                            else -> {
                                Box(
                                    modifier = Modifier
                                        .size(18.dp)
                                        .clip(CircleShape)
                                        .background(ClayBorder.copy(alpha = 0.6f))
                                )
                            }
                        }

                        Spacer(modifier = Modifier.width(10.dp))

                        Text(
                            text = stepText,
                            style = MaterialTheme.typography.bodySmall.copy(
                                fontWeight = if (isCurrent) FontWeight.Bold else FontWeight.Normal,
                                color = if (isDone || isCurrent) ClayEarthyBrown else ClayWarmBrown.copy(alpha = 0.5f)
                            )
                        )
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun AnalysisResultDashboard(
    result: AnalysisResult,
    onAnalyzeAnother: () -> Unit,
    onNavigateToGuide: () -> Unit,
    onNavigateToCuring: () -> Unit = {},
    onNavigateToDoctor: () -> Unit = {},
    onNavigateToKitchen: () -> Unit = {}
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .testTag("analysis_dashboard"),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        // Top Banner Card
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(containerColor = WarmCreamCard),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Image or vector illustration
                ClayPotIllustration(
                    presetKey = result.presetKey,
                    isScanning = false,
                    size = 140.dp
                )

                Spacer(modifier = Modifier.height(14.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(30.dp))
                            .background(OrganicSageContainer)
                            .padding(horizontal = 12.dp, vertical = 4.dp)
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.CheckCircle, contentDescription = null, tint = OrganicSageGreen, modifier = Modifier.size(14.dp))
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = "Analysis Complete",
                                style = MaterialTheme.typography.labelMedium.copy(
                                    color = OrganicSageGreen,
                                    fontWeight = FontWeight.Bold
                                )
                            )
                        }
                    }

                    Spacer(modifier = Modifier.width(8.dp))

                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(30.dp))
                            .background(TerracottaContainer)
                            .padding(horizontal = 10.dp, vertical = 4.dp)
                    ) {
                        Text(
                            text = "${(result.confidenceScore * 100).toInt()}% Confidence",
                            style = MaterialTheme.typography.labelSmall.copy(
                                color = TerracottaPrimary,
                                fontWeight = FontWeight.Bold
                            )
                        )
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                Text(
                    text = result.potType,
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontWeight = FontWeight.Bold,
                        color = ClayEarthyBrown,
                        textAlign = TextAlign.Center
                    )
                )

                Text(
                    text = result.shapeDescription,
                    style = MaterialTheme.typography.bodySmall.copy(color = ClayWarmBrown)
                )
            }
        }

        // Result Card 1: Pot Type
        ResultCardItem(
            icon = Icons.Default.AutoAwesome,
            iconTint = TerracottaPrimary,
            title = "Pot Type",
            primaryValue = result.potType,
            description = result.potTypeExplanation,
            testTag = "result_card_pot_type"
        )

        // Result Card 2: Estimated Capacity
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .testTag("result_card_capacity"),
            shape = RoundedCornerShape(18.dp),
            colors = CardDefaults.cardColors(containerColor = WarmCreamCard)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(18.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(36.dp)
                            .clip(RoundedCornerShape(10.dp))
                            .background(Color(0xFFD48B38).copy(alpha = 0.15f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(Icons.Default.SquareFoot, contentDescription = null, tint = Color(0xFFD48B38), modifier = Modifier.size(20.dp))
                    }
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(
                        text = "Estimated Capacity",
                        style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold, color = ClayEarthyBrown)
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                Row(verticalAlignment = Alignment.Bottom) {
                    Text(
                        text = result.estimatedCapacity,
                        style = MaterialTheme.typography.headlineMedium.copy(
                            fontWeight = FontWeight.ExtraBold,
                            color = TerracottaPrimary
                        )
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "(Estimated from image)",
                        style = MaterialTheme.typography.bodySmall.copy(color = ClayWarmBrown)
                    )
                }

                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = result.capacityNote,
                    style = MaterialTheme.typography.bodySmall.copy(color = ClayWarmBrown, fontStyle = androidx.compose.ui.text.font.FontStyle.Italic)
                )
            }
        }

        // Result Card 3: Material
        ResultCardItem(
            icon = Icons.Default.Science,
            iconTint = OrganicSageGreen,
            title = "Material Analysis",
            primaryValue = result.material,
            description = result.materialExplanation,
            testTag = "result_card_material"
        )

        // Result Card 4: Heat Suitability
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .testTag("result_card_heat"),
            shape = RoundedCornerShape(18.dp),
            colors = CardDefaults.cardColors(containerColor = WarmCreamCard)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(18.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .size(36.dp)
                                .clip(RoundedCornerShape(10.dp))
                                .background(Color(0xFFE27B58).copy(alpha = 0.15f)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(Icons.Default.LocalFireDepartment, contentDescription = null, tint = TerracottaPrimary, modifier = Modifier.size(20.dp))
                        }
                        Spacer(modifier = Modifier.width(10.dp))
                        Text(
                            text = "Heat Suitability",
                            style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold, color = ClayEarthyBrown)
                        )
                    }

                    val (badgeBg, badgeTextColor) = when (result.heatSuitability) {
                        HeatSuitability.SUITABLE -> StatusSuitable.copy(alpha = 0.15f) to StatusSuitable
                        HeatSuitability.LIMITED -> StatusLimited.copy(alpha = 0.15f) to StatusLimited
                        HeatSuitability.NOT_RECOMMENDED -> StatusNotRecommended.copy(alpha = 0.15f) to StatusNotRecommended
                        HeatSuitability.UNKNOWN -> StatusUnknown.copy(alpha = 0.15f) to StatusUnknown
                    }

                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(20.dp))
                            .background(badgeBg)
                            .padding(horizontal = 12.dp, vertical = 6.dp)
                    ) {
                        Text(
                            text = result.heatSuitability.displayName,
                            style = MaterialTheme.typography.labelMedium.copy(
                                fontWeight = FontWeight.Bold,
                                color = badgeTextColor
                            )
                        )
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = result.heatSuitabilityExplanation,
                    style = MaterialTheme.typography.bodySmall.copy(color = ClayWarmBrown, lineHeight = 18.sp)
                )
            }
        }

        // Result Card 5: Recommended Usage (Tags/Chips)
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .testTag("result_card_usage"),
            shape = RoundedCornerShape(18.dp),
            colors = CardDefaults.cardColors(containerColor = WarmCreamCard)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(18.dp)
            ) {
                Text(
                    text = "Recommended Usage",
                    style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold, color = ClayEarthyBrown)
                )

                Spacer(modifier = Modifier.height(10.dp))

                FlowRow(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    result.recommendedUsage.forEach { tag ->
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(20.dp))
                                .background(TerracottaContainer.copy(alpha = 0.5f))
                                .border(1.dp, TerracottaPrimary.copy(alpha = 0.3f), RoundedCornerShape(20.dp))
                                .padding(horizontal = 12.dp, vertical = 6.dp)
                        ) {
                            Text(
                                text = tag,
                                style = MaterialTheme.typography.labelMedium.copy(
                                    fontWeight = FontWeight.SemiBold,
                                    color = TerracottaPrimary
                                )
                            )
                        }
                    }
                }
            }
        }

        // Result Card 6: Safety Guidance
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .testTag("result_card_safety"),
            shape = RoundedCornerShape(18.dp),
            colors = CardDefaults.cardColors(containerColor = WarmCreamCard)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(18.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(36.dp)
                            .clip(RoundedCornerShape(10.dp))
                            .background(Color(0xFFC62828).copy(alpha = 0.12f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(Icons.Default.Security, contentDescription = null, tint = Color(0xFFC62828), modifier = Modifier.size(20.dp))
                    }
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(
                        text = "Safety Considerations",
                        style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold, color = ClayEarthyBrown)
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                result.safetyGuidelines.forEach { guideline ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 3.dp),
                        verticalAlignment = Alignment.Top
                    ) {
                        Icon(
                            imageVector = Icons.Default.Warning,
                            contentDescription = null,
                            tint = Color(0xFFD97706),
                            modifier = Modifier
                                .size(16.dp)
                                .padding(top = 2.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = guideline,
                            style = MaterialTheme.typography.bodySmall.copy(color = ClayWarmBrown, lineHeight = 17.sp)
                        )
                    }
                }
            }
        }

        // Result Card 7: Care & Maintenance
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .testTag("result_card_care"),
            shape = RoundedCornerShape(18.dp),
            colors = CardDefaults.cardColors(containerColor = WarmCreamCard)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(18.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(36.dp)
                            .clip(RoundedCornerShape(10.dp))
                            .background(OrganicSageGreen.copy(alpha = 0.12f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(Icons.Default.CleaningServices, contentDescription = null, tint = OrganicSageGreen, modifier = Modifier.size(20.dp))
                    }
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(
                        text = "Care & Maintenance",
                        style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold, color = ClayEarthyBrown)
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                result.careAndMaintenance.forEach { careItem ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 3.dp),
                        verticalAlignment = Alignment.Top
                    ) {
                        Icon(
                            imageVector = Icons.Default.CheckCircle,
                            contentDescription = null,
                            tint = OrganicSageGreen,
                            modifier = Modifier
                                .size(16.dp)
                                .padding(top = 2.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = careItem,
                            style = MaterialTheme.typography.bodySmall.copy(color = ClayWarmBrown, lineHeight = 17.sp)
                        )
                    }
                }
            }
        }

        // Result Card 8: Smart Recommendations
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .testTag("result_card_recommendations"),
            shape = RoundedCornerShape(18.dp),
            colors = CardDefaults.cardColors(containerColor = WarmCreamCard)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(18.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(36.dp)
                            .clip(RoundedCornerShape(10.dp))
                            .background(TerracottaPrimary.copy(alpha = 0.12f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(Icons.Default.Lightbulb, contentDescription = null, tint = TerracottaPrimary, modifier = Modifier.size(20.dp))
                    }
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(
                        text = "Smart Pot Recommendations",
                        style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold, color = ClayEarthyBrown)
                    )
                }

                Spacer(modifier = Modifier.height(10.dp))

                result.smartRecommendations.forEach { rec ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 3.dp),
                        verticalAlignment = Alignment.Top
                    ) {
                        Text("•", color = TerracottaPrimary, fontWeight = FontWeight.Bold, modifier = Modifier.padding(end = 6.dp))
                        Text(
                            text = rec,
                            style = MaterialTheme.typography.bodySmall.copy(color = ClayWarmBrown, lineHeight = 17.sp)
                        )
                    }
                }
            }
        }

        // Companion Next Steps
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(18.dp),
            colors = CardDefaults.cardColors(containerColor = WarmCreamSurface),
            border = androidx.compose.foundation.BorderStroke(1.dp, ClayBorder)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "Pot Companion Next Steps",
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Bold,
                        color = ClayEarthyBrown
                    )
                )
                Spacer(modifier = Modifier.height(10.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    OutlinedButton(
                        onClick = onNavigateToCuring,
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(10.dp)
                    ) {
                        Icon(Icons.Default.LocalFireDepartment, contentDescription = null, tint = TerracottaPrimary, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("Cure Pot", fontSize = 11.sp, color = TerracottaPrimary, fontWeight = FontWeight.Bold)
                    }

                    OutlinedButton(
                        onClick = onNavigateToDoctor,
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(10.dp)
                    ) {
                        Icon(Icons.Default.Hearing, contentDescription = null, tint = OrganicSageGreen, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("Test Ring", fontSize = 11.sp, color = OrganicSageGreen, fontWeight = FontWeight.Bold)
                    }

                    OutlinedButton(
                        onClick = onNavigateToKitchen,
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(10.dp)
                    ) {
                        Icon(Icons.Default.Restaurant, contentDescription = null, tint = TerracottaPrimary, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("Recipes", fontSize = 11.sp, color = TerracottaPrimary, fontWeight = FontWeight.Bold)
                    }
                }
            }
        }

        // Action Buttons
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Button(
                onClick = onAnalyzeAnother,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
                    .testTag("analyze_another_button"),
                colors = ButtonDefaults.buttonColors(containerColor = TerracottaPrimary),
                shape = RoundedCornerShape(12.dp)
            ) {
                Icon(Icons.Default.Refresh, contentDescription = null, modifier = Modifier.size(18.dp))
                Spacer(modifier = Modifier.width(8.dp))
                Text("Analyze Another Clay Pot", fontWeight = FontWeight.Bold)
            }

            OutlinedButton(
                onClick = onNavigateToGuide,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
                    .testTag("view_in_guide_button"),
                shape = RoundedCornerShape(12.dp)
            ) {
                Icon(androidx.compose.material.icons.Icons.AutoMirrored.Filled.MenuBook, contentDescription = null, modifier = Modifier.size(18.dp))
                Spacer(modifier = Modifier.width(8.dp))
                Text("Explore Pot Guide for Similar Vessels")
            }
        }
    }
}

@Composable
private fun ResultCardItem(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    iconTint: Color,
    title: String,
    primaryValue: String,
    description: String,
    testTag: String
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .testTag(testTag),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = WarmCreamCard)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(18.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(36.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .background(iconTint.copy(alpha = 0.15f)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(icon, contentDescription = null, tint = iconTint, modifier = Modifier.size(20.dp))
                }
                Spacer(modifier = Modifier.width(10.dp))
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold, color = ClayEarthyBrown)
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = primaryValue,
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.Bold,
                    color = TerracottaPrimary
                )
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = description,
                style = MaterialTheme.typography.bodySmall.copy(color = ClayWarmBrown, lineHeight = 18.sp)
            )
        }
    }
}

@Composable
private fun ErrorStateSection(
    message: String,
    onRetry: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .testTag("error_card"),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = WarmCreamCard)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(androidx.compose.material.icons.Icons.AutoMirrored.Filled.HelpOutline, contentDescription = null, tint = Color(0xFFC62828), modifier = Modifier.size(48.dp))
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = "Unable to Determine from This Image",
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold, color = ClayEarthyBrown),
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = message.ifBlank { "Please upload a clear image of the clay pot with good lighting to enable accurate geometric and material analysis." },
                style = MaterialTheme.typography.bodyMedium.copy(color = ClayWarmBrown, textAlign = TextAlign.Center)
            )
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = onRetry,
                colors = ButtonDefaults.buttonColors(containerColor = TerracottaPrimary),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text("Try Again with Another Photo")
            }
        }
    }
}
