package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.FastOutSlowInEasing
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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.VolumeUp
import androidx.compose.material.icons.filled.BugReport
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Hearing
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.MedicalServices
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.TouchApp
import androidx.compose.material.icons.filled.VolumeUp
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.PotIssue
import com.example.ui.theme.ClayBorder
import com.example.ui.theme.ClayEarthyBrown
import com.example.ui.theme.ClayWarmBrown
import com.example.ui.theme.OrganicSageContainer
import com.example.ui.theme.OrganicSageGreen
import com.example.ui.theme.OrganicSageLight
import com.example.ui.theme.StatusLimited
import com.example.ui.theme.StatusNotRecommended
import com.example.ui.theme.StatusSuitable
import com.example.ui.theme.TerracottaContainer
import com.example.ui.theme.TerracottaLight
import com.example.ui.theme.TerracottaPrimary
import com.example.ui.theme.WarmCreamBg
import com.example.ui.theme.WarmCreamCard
import com.example.ui.theme.WarmCreamSurface

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun PotDoctorScreen(
    issues: List<PotIssue>,
    searchQuery: String,
    selectedIssue: PotIssue?,
    tapCount: Int,
    lastTappedZone: String,
    resonanceHz: Int,
    healthScore: Int,
    verdictMessage: String,
    hasDefect: Boolean,
    onSearchChange: (String) -> Unit,
    onSelectIssue: (PotIssue?) -> Unit,
    onTapZone: (String, Boolean) -> Unit,
    onResetAcousticTest: () -> Unit,
    onAskSage: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    var simulateFlawChecked by remember { mutableStateOf(false) }
    var selectedSeverityFilter by remember { mutableStateOf("All") }

    val filteredIssues = issues.filter { issue ->
        val matchesQuery = issue.title.contains(searchQuery, ignoreCase = true) ||
                issue.symptom.contains(searchQuery, ignoreCase = true) ||
                issue.cause.contains(searchQuery, ignoreCase = true)
        val matchesSeverity = selectedSeverityFilter == "All" || issue.severity.equals(selectedSeverityFilter, ignoreCase = true)
        matchesQuery && matchesSeverity
    }

    val animatedScore by animateFloatAsState(
        targetValue = healthScore / 100f,
        animationSpec = tween(durationMillis = 500, easing = FastOutSlowInEasing),
        label = "acoustic_score"
    )

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(WarmCreamBg)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
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
                            .background(TerracottaContainer)
                            .padding(horizontal = 8.dp, vertical = 4.dp)
                    ) {
                        Text(
                            text = "DIAGNOSTIC CLINIC",
                            style = MaterialTheme.typography.labelSmall.copy(
                                color = TerracottaPrimary,
                                fontWeight = FontWeight.Bold,
                                letterSpacing = 1.sp
                            )
                        )
                    }
                    Text(
                        text = "• Pot Doctor & Acoustic Ring",
                        style = MaterialTheme.typography.bodySmall.copy(color = ClayWarmBrown)
                    )
                }

                Spacer(modifier = Modifier.height(6.dp))

                Text(
                    text = "Pot Doctor & Acoustic Integrity",
                    style = MaterialTheme.typography.headlineMedium.copy(
                        fontWeight = FontWeight.Bold,
                        color = ClayEarthyBrown
                    )
                )
                Text(
                    text = "Detect hidden hairline cracks before you cook, and solve organic clay problems like mold, carbon crusts, and mineral weeping.",
                    style = MaterialTheme.typography.bodyMedium.copy(color = ClayWarmBrown)
                )
            }
        }

        // Section 1: Acoustic Ring Resonance Tester
        item {
            Card(
                shape = RoundedCornerShape(18.dp),
                colors = CardDefaults.cardColors(containerColor = WarmCreamCard),
                elevation = CardDefaults.cardElevation(defaultElevation = 3.dp),
                border = androidx.compose.foundation.BorderStroke(1.dp, ClayBorder)
            ) {
                Column(modifier = Modifier.padding(18.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            Box(
                                modifier = Modifier
                                    .size(36.dp)
                                    .clip(CircleShape)
                                    .background(TerracottaContainer),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(Icons.Default.Hearing, contentDescription = null, tint = TerracottaPrimary, modifier = Modifier.size(20.dp))
                            }
                            Column {
                                Text(
                                    text = "Acoustic Ring Tester",
                                    style = MaterialTheme.typography.titleLarge.copy(
                                        fontWeight = FontWeight.Bold,
                                        color = ClayEarthyBrown
                                    )
                                )
                                Text(
                                    text = "Traditional Potter's Resonance Check",
                                    style = MaterialTheme.typography.labelSmall.copy(color = ClayWarmBrown)
                                )
                            }
                        }

                        if (tapCount > 0) {
                            TextButton(onClick = onResetAcousticTest) {
                                Icon(Icons.Default.Refresh, contentDescription = null, tint = ClayWarmBrown, modifier = Modifier.size(16.dp))
                                Spacer(modifier = Modifier.width(4.dp))
                                Text("Reset", color = ClayWarmBrown, fontSize = 12.sp)
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    Text(
                        text = "Master artisans gently tap clay: a high bell chime confirms uniform firing and zero internal micro-cracks. A dull 'thud' warns of hidden structural fractures.",
                        style = MaterialTheme.typography.bodySmall.copy(color = ClayWarmBrown, lineHeight = 18.sp)
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // Simulated Pot with 3 Tap Targets
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(14.dp))
                            .background(WarmCreamSurface)
                            .padding(16.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            Text(
                                text = "TAP A POT ZONE TO TEST RING",
                                style = MaterialTheme.typography.labelMedium.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = TerracottaPrimary,
                                    letterSpacing = 1.sp
                                )
                            )

                            Row(
                                horizontalArrangement = Arrangement.spacedBy(10.dp),
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Button(
                                    onClick = { onTapZone("Rim", simulateFlawChecked) },
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = if (lastTappedZone == "Rim") TerracottaPrimary else TerracottaContainer
                                    ),
                                    shape = RoundedCornerShape(10.dp),
                                    modifier = Modifier.weight(1f).testTag("tap_zone_rim")
                                ) {
                                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                        Icon(Icons.AutoMirrored.Filled.VolumeUp, contentDescription = null, tint = if (lastTappedZone == "Rim") Color.White else TerracottaPrimary, modifier = Modifier.size(18.dp))
                                        Text("Rim Tap", fontWeight = FontWeight.Bold, color = if (lastTappedZone == "Rim") Color.White else TerracottaPrimary, fontSize = 12.sp)
                                        Text("High Chime", style = MaterialTheme.typography.labelSmall.copy(fontSize = 10.sp, color = if (lastTappedZone == "Rim") Color.White.copy(alpha = 0.8f) else ClayWarmBrown))
                                    }
                                }

                                Button(
                                    onClick = { onTapZone("Belly", simulateFlawChecked) },
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = if (lastTappedZone == "Belly") TerracottaPrimary else TerracottaContainer
                                    ),
                                    shape = RoundedCornerShape(10.dp),
                                    modifier = Modifier.weight(1f).testTag("tap_zone_belly")
                                ) {
                                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                        Icon(Icons.Default.TouchApp, contentDescription = null, tint = if (lastTappedZone == "Belly") Color.White else TerracottaPrimary, modifier = Modifier.size(18.dp))
                                        Text("Belly Tap", fontWeight = FontWeight.Bold, color = if (lastTappedZone == "Belly") Color.White else TerracottaPrimary, fontSize = 12.sp)
                                        Text("Mid Tone", style = MaterialTheme.typography.labelSmall.copy(fontSize = 10.sp, color = if (lastTappedZone == "Belly") Color.White.copy(alpha = 0.8f) else ClayWarmBrown))
                                    }
                                }

                                Button(
                                    onClick = { onTapZone("Base", simulateFlawChecked) },
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = if (lastTappedZone == "Base") TerracottaPrimary else TerracottaContainer
                                    ),
                                    shape = RoundedCornerShape(10.dp),
                                    modifier = Modifier.weight(1f).testTag("tap_zone_base")
                                ) {
                                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                        Icon(Icons.Default.Hearing, contentDescription = null, tint = if (lastTappedZone == "Base") Color.White else TerracottaPrimary, modifier = Modifier.size(18.dp))
                                        Text("Base Tap", fontWeight = FontWeight.Bold, color = if (lastTappedZone == "Base") Color.White else TerracottaPrimary, fontSize = 12.sp)
                                        Text("Deep Ring", style = MaterialTheme.typography.labelSmall.copy(fontSize = 10.sp, color = if (lastTappedZone == "Base") Color.White.copy(alpha = 0.8f) else ClayWarmBrown))
                                    }
                                }
                            }

                            // Simulation switch
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(6.dp),
                                modifier = Modifier
                                    .clip(RoundedCornerShape(8.dp))
                                    .clickable { simulateFlawChecked = !simulateFlawChecked }
                                    .padding(horizontal = 8.dp, vertical = 4.dp)
                            ) {
                                Icon(
                                    imageVector = if (simulateFlawChecked) Icons.Default.Warning else Icons.Default.CheckCircle,
                                    contentDescription = null,
                                    tint = if (simulateFlawChecked) StatusNotRecommended else OrganicSageGreen,
                                    modifier = Modifier.size(16.dp)
                                )
                                Text(
                                    text = if (simulateFlawChecked) "Mode: Simulating Damaged Pot (Dull Thud)" else "Mode: Healthy Pristine Pot",
                                    style = MaterialTheme.typography.labelSmall.copy(
                                        color = if (simulateFlawChecked) StatusNotRecommended else OrganicSageGreen,
                                        fontWeight = FontWeight.Bold
                                    )
                                )
                            }
                        }
                    }

                    if (tapCount > 0) {
                        Spacer(modifier = Modifier.height(16.dp))

                        // Results Metrics Box
                        Surface(
                            shape = RoundedCornerShape(12.dp),
                            color = if (hasDefect) Color(0xFFFEF2F2) else OrganicSageContainer.copy(alpha = 0.7f),
                            border = androidx.compose.foundation.BorderStroke(
                                1.dp,
                                if (hasDefect) Color(0xFFFCA5A5) else OrganicSageLight
                            )
                        ) {
                            Column(modifier = Modifier.padding(14.dp)) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Column {
                                        Text(
                                            text = "Resonance Pitch",
                                            style = MaterialTheme.typography.labelSmall.copy(color = ClayWarmBrown)
                                        )
                                        Text(
                                            text = "$resonanceHz Hz",
                                            style = MaterialTheme.typography.titleLarge.copy(
                                                fontWeight = FontWeight.Bold,
                                                color = if (hasDefect) StatusNotRecommended else OrganicSageGreen
                                            )
                                        )
                                    }

                                    Column(horizontalAlignment = Alignment.End) {
                                        Text(
                                            text = "Structural Health",
                                            style = MaterialTheme.typography.labelSmall.copy(color = ClayWarmBrown)
                                        )
                                        Text(
                                            text = "$healthScore%",
                                            style = MaterialTheme.typography.titleLarge.copy(
                                                fontWeight = FontWeight.Bold,
                                                color = if (hasDefect) StatusNotRecommended else OrganicSageGreen
                                            )
                                        )
                                    }
                                }

                                Spacer(modifier = Modifier.height(8.dp))

                                LinearProgressIndicator(
                                    progress = { animatedScore },
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(8.dp)
                                        .clip(CircleShape),
                                    color = if (hasDefect) StatusNotRecommended else OrganicSageGreen,
                                    trackColor = WarmCreamCard
                                )

                                Spacer(modifier = Modifier.height(10.dp))

                                Text(
                                    text = verdictMessage,
                                    style = MaterialTheme.typography.bodySmall.copy(
                                        color = if (hasDefect) Color(0xFF991B1B) else OrganicSageGreen,
                                        fontWeight = FontWeight.SemiBold,
                                        lineHeight = 18.sp
                                    )
                                )
                            }
                        }
                    }
                }
            }
        }

        // Section 2: Clay Pot Doctor Issue & Remedy Directory
        item {
            Column {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = "Clay Pot Doctor Clinic",
                            style = MaterialTheme.typography.titleLarge.copy(
                                fontWeight = FontWeight.Bold,
                                color = ClayEarthyBrown
                            )
                        )
                        Text(
                            text = "Natural organic solutions for common earthen cookware issues",
                            style = MaterialTheme.typography.bodySmall.copy(color = ClayWarmBrown)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                // Search field
                OutlinedTextField(
                    value = searchQuery,
                    onValueChange = onSearchChange,
                    modifier = Modifier.fillMaxWidth().testTag("issue_search_field"),
                    placeholder = { Text("Search problems: mold, cracks, stains, weeping...") },
                    leadingIcon = { Icon(Icons.Default.Search, contentDescription = null, tint = TerracottaPrimary) },
                    trailingIcon = {
                        if (searchQuery.isNotBlank()) {
                            IconButton(onClick = { onSearchChange("") }) {
                                Icon(Icons.Default.Close, contentDescription = "Clear", tint = ClayWarmBrown)
                            }
                        }
                    },
                    shape = RoundedCornerShape(12.dp),
                    singleLine = true
                )

                Spacer(modifier = Modifier.height(10.dp))

                // Severity Filter Chips
                LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    val filters = listOf("All", "Mild", "Moderate", "Critical")
                    items(filters) { filter ->
                        FilterChip(
                            selected = selectedSeverityFilter == filter,
                            onClick = { selectedSeverityFilter = filter },
                            label = { Text(filter) },
                            colors = FilterChipDefaults.filterChipColors(
                                selectedContainerColor = TerracottaPrimary,
                                selectedLabelColor = Color.White
                            )
                        )
                    }
                }
            }
        }

        // Issue List
        items(filteredIssues) { issue ->
            val isSelected = selectedIssue?.id == issue.id

            val severityBg = when (issue.severity) {
                "Critical" -> Color(0xFFFEE2E2)
                "Moderate" -> Color(0xFFFEF3C7)
                else -> Color(0xFFE0F2FE)
            }
            val severityColor = when (issue.severity) {
                "Critical" -> StatusNotRecommended
                "Moderate" -> StatusLimited
                else -> Color(0xFF0369A1)
            }

            Card(
                onClick = {
                    onSelectIssue(if (isSelected) null else issue)
                },
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(
                    containerColor = if (isSelected) WarmCreamSurface else WarmCreamCard
                ),
                border = androidx.compose.foundation.BorderStroke(
                    1.dp,
                    if (isSelected) TerracottaPrimary else ClayBorder
                ),
                modifier = Modifier.fillMaxWidth().testTag("issue_card_${issue.id}")
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            modifier = Modifier.weight(1f)
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(32.dp)
                                    .clip(CircleShape)
                                    .background(severityBg),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(Icons.Default.MedicalServices, contentDescription = null, tint = severityColor, modifier = Modifier.size(18.dp))
                            }
                            Text(
                                text = issue.title,
                                style = MaterialTheme.typography.titleMedium.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = ClayEarthyBrown
                                )
                            )
                        }

                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(6.dp))
                                .background(severityBg)
                                .padding(horizontal = 8.dp, vertical = 4.dp)
                        ) {
                            Text(
                                text = issue.severity,
                                style = MaterialTheme.typography.labelSmall.copy(
                                    color = severityColor,
                                    fontWeight = FontWeight.Bold
                                )
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    Text(
                        text = "Symptom: ${issue.symptom}",
                        style = MaterialTheme.typography.bodySmall.copy(
                            color = ClayWarmBrown,
                            fontWeight = FontWeight.Medium
                        )
                    )

                    AnimatedVisibility(visible = isSelected) {
                        Column(modifier = Modifier.padding(top = 12.dp)) {
                            // Cause
                            Surface(
                                shape = RoundedCornerShape(8.dp),
                                color = WarmCreamBg,
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Column(modifier = Modifier.padding(10.dp)) {
                                    Text(
                                        text = "Root Cause:",
                                        style = MaterialTheme.typography.labelMedium.copy(
                                            fontWeight = FontWeight.Bold,
                                            color = ClayEarthyBrown
                                        )
                                    )
                                    Text(
                                        text = issue.cause,
                                        style = MaterialTheme.typography.bodySmall.copy(color = ClayWarmBrown)
                                    )
                                }
                            }

                            Spacer(modifier = Modifier.height(12.dp))

                            Text(
                                text = "Natural Remedy Protocol:",
                                style = MaterialTheme.typography.titleSmall.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = OrganicSageGreen
                                )
                            )

                            Spacer(modifier = Modifier.height(6.dp))

                            issue.remedySteps.forEachIndexed { idx, step ->
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(vertical = 4.dp),
                                    verticalAlignment = Alignment.Top,
                                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                                ) {
                                    Box(
                                        modifier = Modifier
                                            .size(20.dp)
                                            .clip(CircleShape)
                                            .background(OrganicSageContainer),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Text(
                                            text = "${idx + 1}",
                                            style = MaterialTheme.typography.labelSmall.copy(
                                                color = OrganicSageGreen,
                                                fontWeight = FontWeight.Bold,
                                                fontSize = 11.sp
                                            )
                                        )
                                    }
                                    Text(
                                        text = step,
                                        style = MaterialTheme.typography.bodySmall.copy(
                                            color = ClayEarthyBrown,
                                            lineHeight = 18.sp
                                        ),
                                        modifier = Modifier.weight(1f)
                                    )
                                }
                            }

                            Spacer(modifier = Modifier.height(10.dp))

                            // Prevention
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(TerracottaContainer.copy(alpha = 0.5f))
                                    .padding(10.dp)
                            ) {
                                Text(
                                    text = "Prevention Rule: ${issue.preventionTip}",
                                    style = MaterialTheme.typography.bodySmall.copy(
                                        color = TerracottaPrimary,
                                        fontWeight = FontWeight.Medium
                                    )
                                )
                            }

                            Spacer(modifier = Modifier.height(10.dp))

                            Button(
                                onClick = { onAskSage("How do I fix this issue with my clay pot: ${issue.title}?") },
                                colors = ButtonDefaults.buttonColors(containerColor = TerracottaPrimary),
                                shape = RoundedCornerShape(8.dp),
                                modifier = Modifier.align(Alignment.End)
                            ) {
                                Text("Ask Sage AI More", fontSize = 12.sp)
                            }
                        }
                    }

                    if (!isSelected) {
                        Spacer(modifier = Modifier.height(8.dp))
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            Text(
                                text = "Tap to view natural remedy",
                                style = MaterialTheme.typography.labelSmall.copy(
                                    color = TerracottaPrimary,
                                    fontWeight = FontWeight.Bold
                                )
                            )
                            Icon(Icons.Default.ChevronRight, contentDescription = null, tint = TerracottaPrimary, modifier = Modifier.size(16.dp))
                        }
                    }
                }
            }
        }

        item {
            Spacer(modifier = Modifier.height(20.dp))
        }
    }
}
