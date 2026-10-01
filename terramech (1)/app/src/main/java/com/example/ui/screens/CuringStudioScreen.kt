package com.example.ui.screens

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.material.icons.filled.Opacity
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.CuringGuide
import com.example.ui.theme.ClayBorder
import com.example.ui.theme.ClayEarthyBrown
import com.example.ui.theme.ClayWarmBrown
import com.example.ui.theme.OrganicSageContainer
import com.example.ui.theme.OrganicSageGreen
import com.example.ui.theme.OrganicSageLight
import com.example.ui.theme.TerracottaContainer
import com.example.ui.theme.TerracottaPrimary
import com.example.ui.theme.WarmCreamBg
import com.example.ui.theme.WarmCreamCard
import com.example.ui.theme.WarmCreamSurface

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun CuringStudioScreen(
    guides: List<CuringGuide>,
    selectedGuide: CuringGuide,
    completedSteps: Set<String>,
    timerSecondsRemaining: Int,
    isTimerActive: Boolean,
    activeTimerTitle: String,
    onSelectGuide: (CuringGuide) -> Unit,
    onToggleStep: (String, Int) -> Unit,
    onStartTimer: (Int, String) -> Unit,
    onPauseTimer: () -> Unit,
    onResumeTimer: () -> Unit,
    onResetTimer: () -> Unit,
    onAskSage: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val totalSteps = selectedGuide.steps.size
    val completedCount = selectedGuide.steps.count {
        completedSteps.contains("${selectedGuide.id}_${it.stepNumber}")
    }
    val progressFraction = if (totalSteps > 0) completedCount.toFloat() / totalSteps else 0f
    val animatedProgress by animateFloatAsState(
        targetValue = progressFraction,
        animationSpec = tween(durationMillis = 400, easing = LinearEasing),
        label = "curing_progress"
    )

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(WarmCreamBg)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(18.dp)
    ) {
        // Header
        item {
            Column {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(8.dp))
                            .background(OrganicSageContainer)
                            .padding(horizontal = 8.dp, vertical = 4.dp)
                    ) {
                        Text(
                            text = "SEASONING STUDIO",
                            style = MaterialTheme.typography.labelSmall.copy(
                                color = OrganicSageGreen,
                                fontWeight = FontWeight.Bold,
                                letterSpacing = 1.sp
                            )
                        )
                    }
                    Text(
                        text = "• Pot Longevity",
                        style = MaterialTheme.typography.bodySmall.copy(color = ClayWarmBrown)
                    )
                }

                Spacer(modifier = Modifier.height(6.dp))

                Text(
                    text = "Pot Seasoning & Curing Companion",
                    style = MaterialTheme.typography.headlineMedium.copy(
                        fontWeight = FontWeight.Bold,
                        color = ClayEarthyBrown
                    )
                )
                Text(
                    text = "Unseasoned raw clay cracks on flame. Follow these guided curing stages to temper micro-pores, eliminate earthy odors, and build a natural non-stick patina.",
                    style = MaterialTheme.typography.bodyMedium.copy(color = ClayWarmBrown)
                )
            }
        }

        // Pot Type Tabs
        item {
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                items(guides) { guide ->
                    val isSelected = guide.id == selectedGuide.id
                    Card(
                        onClick = { onSelectGuide(guide) },
                        shape = RoundedCornerShape(12.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = if (isSelected) TerracottaPrimary else WarmCreamCard
                        ),
                        border = if (isSelected) null else androidx.compose.foundation.BorderStroke(1.dp, ClayBorder),
                        modifier = Modifier.testTag("curing_tab_${guide.id}")
                    ) {
                        Text(
                            text = guide.potType,
                            style = MaterialTheme.typography.bodyMedium.copy(
                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                                color = if (isSelected) Color.White else ClayEarthyBrown
                            ),
                            modifier = Modifier.padding(horizontal = 14.dp, vertical = 10.dp)
                        )
                    }
                }
            }
        }

        // Selected Guide Overview Card
        item {
            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = WarmCreamCard),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(
                                text = selectedGuide.potType,
                                style = MaterialTheme.typography.titleLarge.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = TerracottaPrimary
                                )
                            )
                            Text(
                                text = selectedGuide.summary,
                                style = MaterialTheme.typography.bodyMedium.copy(color = ClayWarmBrown)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    FlowRow(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Surface(
                            shape = RoundedCornerShape(8.dp),
                            color = TerracottaContainer
                        ) {
                            Row(
                                modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(6.dp)
                            ) {
                                Icon(Icons.Default.AccessTime, contentDescription = null, tint = TerracottaPrimary, modifier = Modifier.size(16.dp))
                                Text(
                                    text = "Est. Time: ${selectedGuide.estimatedDays}",
                                    style = MaterialTheme.typography.labelMedium.copy(
                                        color = TerracottaPrimary,
                                        fontWeight = FontWeight.Bold
                                    )
                                )
                            }
                        }

                        Surface(
                            shape = RoundedCornerShape(8.dp),
                            color = OrganicSageContainer
                        ) {
                            Row(
                                modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(6.dp)
                            ) {
                                Icon(Icons.Default.Opacity, contentDescription = null, tint = OrganicSageGreen, modifier = Modifier.size(16.dp))
                                Text(
                                    text = selectedGuide.recommendedOil,
                                    style = MaterialTheme.typography.labelMedium.copy(
                                        color = OrganicSageGreen,
                                        fontWeight = FontWeight.Bold
                                    )
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    // Progress bar
                    Column {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = "Seasoning Progress",
                                style = MaterialTheme.typography.labelMedium.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = ClayEarthyBrown
                                )
                            )
                            Text(
                                text = "$completedCount of $totalSteps steps (${(animatedProgress * 100).toInt()}%)",
                                style = MaterialTheme.typography.labelMedium.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = TerracottaPrimary
                                )
                            )
                        }
                        Spacer(modifier = Modifier.height(6.dp))
                        LinearProgressIndicator(
                            progress = { animatedProgress },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(8.dp)
                                .clip(CircleShape),
                            color = TerracottaPrimary,
                            trackColor = WarmCreamSurface
                        )
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    // Golden rule callout
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(10.dp))
                            .background(Color(0xFFFEF2F2))
                            .border(1.dp, Color(0xFFFCA5A5), RoundedCornerShape(10.dp))
                            .padding(12.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Icon(Icons.Default.Warning, contentDescription = null, tint = Color(0xFFDC2626), modifier = Modifier.size(20.dp))
                            Text(
                                text = "Golden Rule: ${selectedGuide.goldenRule}",
                                style = MaterialTheme.typography.bodySmall.copy(
                                    color = Color(0xFF991B1B),
                                    fontWeight = FontWeight.Medium
                                )
                            )
                        }
                    }
                }
            }
        }

        // Active Timer Card (if running or has seconds)
        if (timerSecondsRemaining > 0 || isTimerActive) {
            item {
                Card(
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = TerracottaContainer),
                    border = androidx.compose.foundation.BorderStroke(1.5.dp, TerracottaPrimary)
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                                Icon(Icons.Default.AccessTime, contentDescription = null, tint = TerracottaPrimary)
                                Text(
                                    text = "Active Curing Timer",
                                    style = MaterialTheme.typography.titleMedium.copy(
                                        fontWeight = FontWeight.Bold,
                                        color = TerracottaPrimary
                                    )
                                )
                            }
                            IconButton(onClick = onResetTimer) {
                                Icon(Icons.Default.Refresh, contentDescription = "Reset timer", tint = TerracottaPrimary)
                            }
                        }

                        Text(
                            text = activeTimerTitle,
                            style = MaterialTheme.typography.bodyMedium.copy(color = ClayWarmBrown),
                            modifier = Modifier.padding(vertical = 4.dp)
                        )

                        val mins = timerSecondsRemaining / 60
                        val secs = timerSecondsRemaining % 60
                        Text(
                            text = String.format("%02d:%02d", mins, secs),
                            style = MaterialTheme.typography.displayMedium.copy(
                                fontWeight = FontWeight.ExtraBold,
                                color = TerracottaPrimary
                            )
                        )

                        Spacer(modifier = Modifier.height(10.dp))

                        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                            if (isTimerActive) {
                                Button(
                                    onClick = onPauseTimer,
                                    colors = ButtonDefaults.buttonColors(containerColor = TerracottaPrimary),
                                    shape = RoundedCornerShape(10.dp)
                                ) {
                                    Icon(Icons.Default.Pause, contentDescription = null, modifier = Modifier.size(18.dp))
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Text("Pause")
                                }
                            } else {
                                Button(
                                    onClick = onResumeTimer,
                                    colors = ButtonDefaults.buttonColors(containerColor = OrganicSageGreen),
                                    shape = RoundedCornerShape(10.dp)
                                ) {
                                    Icon(Icons.Default.PlayArrow, contentDescription = null, modifier = Modifier.size(18.dp))
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Text("Resume")
                                }
                            }

                            OutlinedButton(
                                onClick = onResetTimer,
                                shape = RoundedCornerShape(10.dp)
                            ) {
                                Text("Stop Timer", color = ClayWarmBrown)
                            }
                        }
                    }
                }
            }
        }

        // Steps Title
        item {
            Text(
                text = "Guided Curing Steps",
                style = MaterialTheme.typography.titleLarge.copy(
                    fontWeight = FontWeight.Bold,
                    color = ClayEarthyBrown
                )
            )
        }

        // List of Steps
        items(selectedGuide.steps) { step ->
            val isDone = completedSteps.contains("${selectedGuide.id}_${step.stepNumber}")
            Card(
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(
                    containerColor = if (isDone) WarmCreamSurface else WarmCreamCard
                ),
                border = androidx.compose.foundation.BorderStroke(
                    1.dp,
                    if (isDone) OrganicSageLight else ClayBorder
                ),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .size(32.dp)
                                .clip(CircleShape)
                                .background(if (isDone) OrganicSageGreen else TerracottaPrimary),
                            contentAlignment = Alignment.Center
                        ) {
                            if (isDone) {
                                Icon(Icons.Default.CheckCircle, contentDescription = null, tint = Color.White, modifier = Modifier.size(20.dp))
                            } else {
                                Text(
                                    text = "${step.stepNumber}",
                                    style = MaterialTheme.typography.titleSmall.copy(
                                        color = Color.White,
                                        fontWeight = FontWeight.Bold
                                    )
                                )
                            }
                        }

                        Spacer(modifier = Modifier.width(12.dp))

                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = step.title,
                                style = MaterialTheme.typography.titleMedium.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = ClayEarthyBrown
                                )
                            )
                            if (step.durationMinutes > 0) {
                                val durationLabel = if (step.durationMinutes >= 60) {
                                    "${step.durationMinutes / 60} hrs"
                                } else {
                                    "${step.durationMinutes} mins"
                                }
                                Text(
                                    text = "Estimated Time: $durationLabel",
                                    style = MaterialTheme.typography.labelSmall.copy(color = ClayWarmBrown)
                                )
                            }
                        }

                        Checkbox(
                            checked = isDone,
                            onCheckedChange = { onToggleStep(selectedGuide.id, step.stepNumber) },
                            colors = CheckboxDefaults.colors(
                                checkedColor = OrganicSageGreen,
                                uncheckedColor = ClayBorder
                            )
                        )
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    Text(
                        text = step.instruction,
                        style = MaterialTheme.typography.bodyMedium.copy(
                            color = ClayEarthyBrown,
                            lineHeight = 20.sp
                        )
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    // Pro tip callout
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(8.dp))
                            .background(OrganicSageContainer.copy(alpha = 0.6f))
                            .padding(10.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.Top,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Icon(Icons.Default.Lightbulb, contentDescription = null, tint = OrganicSageGreen, modifier = Modifier.size(18.dp))
                            Text(
                                text = "Artisan Tip: ${step.proTip}",
                                style = MaterialTheme.typography.bodySmall.copy(
                                    color = OrganicSageGreen,
                                    fontWeight = FontWeight.Medium
                                )
                            )
                        }
                    }

                    if (step.durationMinutes > 0 && !isDone) {
                        Spacer(modifier = Modifier.height(10.dp))
                        OutlinedButton(
                            onClick = { onStartTimer(step.durationMinutes, "${selectedGuide.potType}: ${step.title}") },
                            shape = RoundedCornerShape(8.dp),
                            modifier = Modifier.align(Alignment.End)
                        ) {
                            Icon(Icons.Default.AccessTime, contentDescription = null, tint = TerracottaPrimary, modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(6.dp))
                            Text("Set Companion Timer", color = TerracottaPrimary, fontSize = 12.sp)
                        }
                    }
                }
            }
        }

        // Ask Sage AI Assistance Banner
        item {
            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = WarmCreamSurface),
                border = androidx.compose.foundation.BorderStroke(1.dp, ClayBorder)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(14.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(44.dp)
                            .clip(CircleShape)
                            .background(TerracottaPrimary),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(Icons.Default.Info, contentDescription = null, tint = Color.White)
                    }

                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "Need Custom Curing Advice?",
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontWeight = FontWeight.Bold,
                                color = ClayEarthyBrown
                            )
                        )
                        Text(
                            text = "Ask Master Somnath via Sage AI for personalized curing ratios or antique clay seasoning.",
                            style = MaterialTheme.typography.bodySmall.copy(color = ClayWarmBrown)
                        )
                    }

                    Button(
                        onClick = { onAskSage("How do I cure my ${selectedGuide.potType}?") },
                        colors = ButtonDefaults.buttonColors(containerColor = TerracottaPrimary),
                        shape = RoundedCornerShape(10.dp)
                    ) {
                        Text("Ask Sage", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                    }
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}
