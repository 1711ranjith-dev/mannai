package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
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
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.CleaningServices
import androidx.compose.material.icons.filled.Explore
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.Hearing
import androidx.compose.material.icons.filled.LocalFireDepartment
import androidx.compose.material.icons.filled.MedicalServices
import androidx.compose.material.icons.filled.Opacity
import androidx.compose.material.icons.filled.Psychology
import androidx.compose.material.icons.filled.Restaurant
import androidx.compose.material.icons.filled.Science
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.SquareFoot
import androidx.compose.material.icons.filled.TouchApp
import androidx.compose.material.icons.filled.UploadFile
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.MockData
import com.example.ui.components.ClayPotIllustration
import com.example.ui.theme.ClayBorder
import com.example.ui.theme.ClayEarthyBrown
import com.example.ui.theme.ClayWarmBrown
import com.example.ui.theme.OrganicSageContainer
import com.example.ui.theme.OrganicSageGreen
import com.example.ui.theme.OrganicSageLight
import com.example.ui.theme.TerracottaContainer
import com.example.ui.theme.TerracottaLight
import com.example.ui.theme.TerracottaPrimary
import com.example.ui.theme.WarmCreamBg
import com.example.ui.theme.WarmCreamCard
import com.example.ui.theme.WarmCreamSurface

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun HomeScreen(
    onNavigate: (String) -> Unit,
    onSelectPresetAndAnalyze: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(WarmCreamBg)
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        item {
            Spacer(modifier = Modifier.height(8.dp))
            // Hero Section
            HeroCard(
                onAnalyzeClick = { onNavigate("analyze") },
                onExploreGuideClick = { onNavigate("guide") },
                onChatClick = { onNavigate("chat") }
            )
        }

        // Quick Try Presets Section
        item {
            QuickPresetsSection(
                onSelectPreset = { presetKey ->
                    onSelectPresetAndAnalyze(presetKey)
                    onNavigate("analyze")
                }
            )
        }

        // Interactive Smart Tools & Artisan Features
        item {
            SmartToolsSection(onNavigate = onNavigate)
        }

        // 6 Feature Cards
        item {
            FeatureSection()
        }

        // How It Works Section
        item {
            HowItWorksSection(
                onGetStarted = { onNavigate("analyze") }
            )
        }

        // Bottom CTA Banner
        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 24.dp),
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(containerColor = TerracottaPrimary),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        imageVector = Icons.Default.AutoAwesome,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(36.dp)
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        text = "Rediscover Earthen Culinary Wisdom",
                        style = MaterialTheme.typography.titleLarge.copy(
                            fontWeight = FontWeight.Bold,
                            color = Color.White,
                            textAlign = TextAlign.Center
                        )
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Scan your pottery today to unlock safe temperature ranges, authentic seasoning steps, and dish pairings.",
                        style = MaterialTheme.typography.bodyMedium.copy(
                            color = Color(0xFFFFDBCF),
                            textAlign = TextAlign.Center
                        )
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Button(
                        onClick = { onNavigate("analyze") },
                        colors = ButtonDefaults.buttonColors(containerColor = Color.White),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.testTag("cta_analyze_button")
                    ) {
                        Text(
                            text = "Start Free Analysis",
                            color = TerracottaPrimary,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                            contentDescription = null,
                            tint = TerracottaPrimary,
                            modifier = Modifier.size(18.dp)
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun HeroCard(
    onAnalyzeClick: () -> Unit,
    onExploreGuideClick: () -> Unit,
    onChatClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .testTag("hero_card"),
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
            // Badge: AI-Powered Clay Pot Analysis
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(30.dp))
                    .background(OrganicSageContainer)
                    .padding(horizontal = 14.dp, vertical = 6.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.AutoAwesome,
                        contentDescription = null,
                        tint = OrganicSageGreen,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "AI-Powered Clay Pot Analysis",
                        style = MaterialTheme.typography.labelMedium.copy(
                            color = OrganicSageGreen,
                            fontWeight = FontWeight.Bold
                        )
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Main heading
            Text(
                text = "Know Your Clay Pot.\nUse It Right.",
                style = MaterialTheme.typography.headlineMedium.copy(
                    fontWeight = FontWeight.ExtraBold,
                    color = ClayEarthyBrown,
                    textAlign = TextAlign.Center,
                    lineHeight = 34.sp
                )
            )

            Spacer(modifier = Modifier.height(10.dp))

            // Subtitle
            Text(
                text = "AI-powered identification and smart guidance for traditional clay pots.",
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = ClayWarmBrown,
                    textAlign = TextAlign.Center,
                    lineHeight = 20.sp
                ),
                modifier = Modifier.padding(horizontal = 12.dp)
            )

            Spacer(modifier = Modifier.height(20.dp))

            // Hero visual: Realistic clay pot illustration with active scanning reticles
            ClayPotIllustration(
                presetKey = "handi",
                isScanning = true,
                size = 190.dp
            )

            Spacer(modifier = Modifier.height(20.dp))

            // Action Buttons
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Button(
                    onClick = onAnalyzeClick,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                        .testTag("hero_analyze_button"),
                    colors = ButtonDefaults.buttonColors(containerColor = TerracottaPrimary),
                    shape = RoundedCornerShape(14.dp)
                ) {
                    Icon(Icons.Default.CameraAlt, contentDescription = null, modifier = Modifier.size(20.dp))
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Analyze Your Pot",
                        style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold)
                    )
                }

                Button(
                    onClick = onChatClick,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp)
                        .testTag("hero_chat_button"),
                    colors = ButtonDefaults.buttonColors(containerColor = OrganicSageGreen),
                    shape = RoundedCornerShape(14.dp)
                ) {
                    Icon(Icons.Default.Psychology, contentDescription = null, modifier = Modifier.size(18.dp))
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Ask Master Somnath (AI & Voice)",
                        style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold)
                    )
                }

                OutlinedButton(
                    onClick = onExploreGuideClick,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp)
                        .testTag("hero_guide_button"),
                    shape = RoundedCornerShape(14.dp),
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = TerracottaPrimary)
                ) {
                    Icon(Icons.Default.Explore, contentDescription = null, modifier = Modifier.size(18.dp))
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Explore Pot Guide",
                        style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.SemiBold)
                    )
                }
            }
        }
    }
}

@Composable
private fun QuickPresetsSection(
    onSelectPreset: (String) -> Unit
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = "Quick Sample Presets",
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Bold,
                        color = ClayEarthyBrown
                    )
                )
                Text(
                    text = "Test instant AI analysis with traditional forms",
                    style = MaterialTheme.typography.bodySmall.copy(color = ClayWarmBrown)
                )
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            MockData.samplePresets.take(2).forEach { preset ->
                PresetCard(preset = preset, modifier = Modifier.weight(1f), onClick = { onSelectPreset(preset.key) })
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            MockData.samplePresets.drop(2).take(2).forEach { preset ->
                PresetCard(preset = preset, modifier = Modifier.weight(1f), onClick = { onSelectPreset(preset.key) })
            }
        }
    }
}

@Composable
private fun PresetCard(
    preset: MockData.PresetItem,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .clickable { onClick() }
            .testTag("preset_card_${preset.key}"),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = WarmCreamCard),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            ClayPotIllustration(
                presetKey = preset.key,
                isScanning = false,
                size = 72.dp
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = preset.title,
                style = MaterialTheme.typography.labelLarge.copy(
                    fontWeight = FontWeight.Bold,
                    color = ClayEarthyBrown,
                    textAlign = TextAlign.Center
                ),
                maxLines = 1
            )
            Text(
                text = "Approx ${preset.estimatedCapacity}",
                style = MaterialTheme.typography.bodySmall.copy(
                    fontSize = 11.sp,
                    color = TerracottaPrimary,
                    fontWeight = FontWeight.Medium
                )
            )
        }
    }
}

@Composable
private fun FeatureSection() {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = "Intelligent Pottery Diagnostics",
            style = MaterialTheme.typography.titleMedium.copy(
                fontWeight = FontWeight.Bold,
                color = ClayEarthyBrown
            )
        )
        Text(
            text = "Comprehensive insights tailored to ancestral earthenware",
            style = MaterialTheme.typography.bodySmall.copy(color = ClayWarmBrown)
        )

        Spacer(modifier = Modifier.height(14.dp))

        val features = listOf(
            FeatureData(
                icon = Icons.Default.AutoAwesome,
                title = "AI Identification",
                description = "Identify the type of clay pot from an uploaded image with deep shape recognition.",
                color = TerracottaPrimary
            ),
            FeatureData(
                icon = Icons.Default.SquareFoot,
                title = "Capacity Estimation",
                description = "Estimate approximate liquid & dry volume based on geometric vessel proportions.",
                color = AmberAccent
            ),
            FeatureData(
                icon = Icons.Default.Science,
                title = "Material Information",
                description = "Understand visible finish, micro-porosity, mica inclusions, and clay grain texture.",
                color = OrganicSageGreen
            ),
            FeatureData(
                icon = Icons.Default.LocalFireDepartment,
                title = "Heat Suitability",
                description = "Clear ratings for direct flame, stovetop diffusers, oven baking, or ambient use.",
                color = Color(0xFFD97706)
            ),
            FeatureData(
                icon = Icons.Default.Security,
                title = "Safety Guidance",
                description = "Essential thermal shock protocols, crack inspection, and food contact safety.",
                color = Color(0xFFC62828)
            ),
            FeatureData(
                icon = Icons.Default.CleaningServices,
                title = "Care & Maintenance",
                description = "Step-by-step unglazed seasoning, soap-free cleaning, and moisture-free drying.",
                color = TerracottaLight
            )
        )

        features.chunked(2).forEach { pair ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 5.dp),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                pair.forEach { feature ->
                    Card(
                        modifier = Modifier
                            .weight(1f)
                            .testTag("feature_card_${feature.title.replace(" ", "_").lowercase()}"),
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(containerColor = WarmCreamCard),
                        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(14.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(36.dp)
                                    .clip(RoundedCornerShape(10.dp))
                                    .background(feature.color.copy(alpha = 0.12f)),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = feature.icon,
                                    contentDescription = feature.title,
                                    tint = feature.color,
                                    modifier = Modifier.size(20.dp)
                                )
                            }
                            Spacer(modifier = Modifier.height(10.dp))
                            Text(
                                text = feature.title,
                                style = MaterialTheme.typography.titleSmall.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = ClayEarthyBrown
                                )
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = feature.description,
                                style = MaterialTheme.typography.bodySmall.copy(
                                    fontSize = 11.5.sp,
                                    color = ClayWarmBrown,
                                    lineHeight = 16.sp
                                )
                            )
                        }
                    }
                }
            }
        }
    }
}

private data class FeatureData(
    val icon: ImageVector,
    val title: String,
    val description: String,
    val color: Color
)

@Composable
private fun HowItWorksSection(
    onGetStarted: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = WarmCreamSurface),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)
        ) {
            Text(
                text = "How It Works",
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.Bold,
                    color = TerracottaPrimary
                )
            )
            Text(
                text = "Three simple steps from photo to actionable earthenware wisdom",
                style = MaterialTheme.typography.bodySmall.copy(color = ClayWarmBrown)
            )

            Spacer(modifier = Modifier.height(16.dp))

            StepItem(
                stepNumber = "01",
                title = "Upload",
                description = "Upload or capture a clear photo of your clay pot or select from artisanal presets."
            )
            Spacer(modifier = Modifier.height(12.dp))
            StepItem(
                stepNumber = "02",
                title = "Analyze",
                description = "AI scans rim geometry, curvature, clay color tone, and porous structural features."
            )
            Spacer(modifier = Modifier.height(12.dp))
            StepItem(
                stepNumber = "03",
                title = "Get Insights",
                description = "Receive heat ratings, estimated volume, seasoning recipe, and safety guidelines."
            )
        }
    }
}

@Composable
private fun StepItem(
    stepNumber: String,
    title: String,
    description: String
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.Top
    ) {
        Box(
            modifier = Modifier
                .size(36.dp)
                .clip(CircleShape)
                .background(TerracottaPrimary),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = stepNumber,
                style = MaterialTheme.typography.labelMedium.copy(
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
            )
        }
        Spacer(modifier = Modifier.width(14.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleSmall.copy(
                    fontWeight = FontWeight.Bold,
                    color = ClayEarthyBrown
                )
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = description,
                style = MaterialTheme.typography.bodySmall.copy(
                    color = ClayWarmBrown,
                    lineHeight = 17.sp
                )
            )
        }
    }
}

@Composable
fun SmartToolsSection(
    onNavigate: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(6.dp))
                            .background(TerracottaContainer)
                            .padding(horizontal = 6.dp, vertical = 2.dp)
                    ) {
                        Text(
                            text = "SMART ARTISAN SUITE",
                            style = MaterialTheme.typography.labelSmall.copy(
                                color = TerracottaPrimary,
                                fontWeight = FontWeight.Bold,
                                fontSize = 10.sp
                            )
                        )
                    }
                }
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Pottery Companion Tools",
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontWeight = FontWeight.Bold,
                        color = ClayEarthyBrown
                    )
                )
            }
        }

        Spacer(modifier = Modifier.height(14.dp))

        // Tool Cards
        Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
            // 1. Seasoning Studio
            Card(
                onClick = { onNavigate("curing") },
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = WarmCreamCard),
                border = androidx.compose.foundation.BorderStroke(1.dp, ClayBorder),
                modifier = Modifier.fillMaxWidth().testTag("tool_card_curing")
            ) {
                Row(
                    modifier = Modifier.padding(14.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(44.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .background(TerracottaContainer),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(Icons.Default.LocalFireDepartment, contentDescription = null, tint = TerracottaPrimary)
                    }
                    Spacer(modifier = Modifier.width(14.dp))
                    Column(modifier = Modifier.weight(1f)) {
                        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                            Text(
                                text = "Seasoning & Curing Studio",
                                style = MaterialTheme.typography.titleMedium.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = ClayEarthyBrown
                                )
                            )
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(4.dp))
                                    .background(OrganicSageContainer)
                                    .padding(horizontal = 5.dp, vertical = 1.dp)
                            ) {
                                Text("Guided", fontSize = 9.sp, fontWeight = FontWeight.Bold, color = OrganicSageGreen)
                            }
                        }
                        Text(
                            text = "Step-by-step unglazed clay seasoning with live countdown timers & crack prevention rules.",
                            style = MaterialTheme.typography.bodySmall.copy(color = ClayWarmBrown, lineHeight = 16.sp)
                        )
                    }
                    Icon(Icons.AutoMirrored.Filled.ArrowForward, contentDescription = null, tint = TerracottaPrimary, modifier = Modifier.size(18.dp))
                }
            }

            // 2. Acoustic Ring Tester & Doctor
            Card(
                onClick = { onNavigate("doctor") },
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = WarmCreamCard),
                border = androidx.compose.foundation.BorderStroke(1.dp, ClayBorder),
                modifier = Modifier.fillMaxWidth().testTag("tool_card_doctor")
            ) {
                Row(
                    modifier = Modifier.padding(14.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(44.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .background(OrganicSageContainer),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(Icons.Default.Hearing, contentDescription = null, tint = OrganicSageGreen)
                    }
                    Spacer(modifier = Modifier.width(14.dp))
                    Column(modifier = Modifier.weight(1f)) {
                        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                            Text(
                                text = "Pot Doctor & Acoustic Ring",
                                style = MaterialTheme.typography.titleMedium.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = ClayEarthyBrown
                                )
                            )
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(4.dp))
                                    .background(Color(0xFFFEF3C7))
                                    .padding(horizontal = 5.dp, vertical = 1.dp)
                            ) {
                                Text("Acoustic", fontSize = 9.sp, fontWeight = FontWeight.Bold, color = Color(0xFFD97706))
                            }
                        }
                        Text(
                            text = "Tap-to-test sonic chime for hairline fractures + organic remedies for mold & carbon burn.",
                            style = MaterialTheme.typography.bodySmall.copy(color = ClayWarmBrown, lineHeight = 16.sp)
                        )
                    }
                    Icon(Icons.AutoMirrored.Filled.ArrowForward, contentDescription = null, tint = OrganicSageGreen, modifier = Modifier.size(18.dp))
                }
            }

            // 3. Clay Pot Kitchen
            Card(
                onClick = { onNavigate("kitchen") },
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = WarmCreamCard),
                border = androidx.compose.foundation.BorderStroke(1.dp, ClayBorder),
                modifier = Modifier.fillMaxWidth().testTag("tool_card_kitchen")
            ) {
                Row(
                    modifier = Modifier.padding(14.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(44.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .background(TerracottaContainer),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(Icons.Default.Restaurant, contentDescription = null, tint = TerracottaPrimary)
                    }
                    Spacer(modifier = Modifier.width(14.dp))
                    Column(modifier = Modifier.weight(1f)) {
                        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                            Text(
                                text = "Clay Pot Kitchen",
                                style = MaterialTheme.typography.titleMedium.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = ClayEarthyBrown
                                )
                            )
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(4.dp))
                                    .background(OrganicSageContainer)
                                    .padding(horizontal = 5.dp, vertical = 1.dp)
                            ) {
                                Text("Recipes", fontSize = 9.sp, fontWeight = FontWeight.Bold, color = OrganicSageGreen)
                            }
                        }
                        Text(
                            text = "Earthen thermal cooking recipes: Dum Biryani, Manchatti fish curry, velvety curd, and Kulhar chai.",
                            style = MaterialTheme.typography.bodySmall.copy(color = ClayWarmBrown, lineHeight = 16.sp)
                        )
                    }
                    Icon(Icons.AutoMirrored.Filled.ArrowForward, contentDescription = null, tint = TerracottaPrimary, modifier = Modifier.size(18.dp))
                }
            }
        }
    }
}

private val AmberAccent = Color(0xFFD48B38)

