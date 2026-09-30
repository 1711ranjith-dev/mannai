package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.MenuBook
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.LocalFireDepartment
import androidx.compose.material.icons.filled.Psychology
import androidx.compose.material.icons.filled.SquareFoot
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
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
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.ClayPot
import com.example.model.PotRecommendation
import com.example.ui.components.ClayPotIllustration
import com.example.ui.theme.ClayBorder
import com.example.ui.theme.ClayEarthyBrown
import com.example.ui.theme.ClayWarmBrown
import com.example.ui.theme.OrganicSageContainer
import com.example.ui.theme.OrganicSageGreen
import com.example.ui.theme.TerracottaContainer
import com.example.ui.theme.TerracottaPrimary
import com.example.ui.theme.WarmCreamBg
import com.example.ui.theme.WarmCreamCard
import com.example.ui.theme.WarmCreamSurface

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun RecommendationWizardScreen(
    selectedUsage: String,
    selectedCapacity: String,
    selectedHeat: String,
    results: List<PotRecommendation>?,
    isLoading: Boolean,
    onUsageChange: (String) -> Unit,
    onCapacityChange: (String) -> Unit,
    onHeatChange: (String) -> Unit,
    onFindPot: () -> Unit,
    onOpenPotDetail: (ClayPot) -> Unit,
    modifier: Modifier = Modifier
) {
    val usageOptions = listOf("Cooking", "Water Storage", "Serving", "Fermentation", "Decoration", "General Household Use")
    val capacityOptions = listOf("Under 1 L", "1–2 L", "2–3 L", "3–5 L", "5 L+")
    val heatOptions = listOf("Yes", "No", "Not Sure")

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(WarmCreamBg)
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Spacer(modifier = Modifier.height(4.dp))
            Column(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = "Find the Right Clay Pot for You",
                    style = MaterialTheme.typography.headlineMedium.copy(
                        fontWeight = FontWeight.Bold,
                        color = ClayEarthyBrown
                    )
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Answer 3 quick questions and our pottery advisor will match the ideal traditional earthen vessel for your lifestyle.",
                    style = MaterialTheme.typography.bodyMedium.copy(color = ClayWarmBrown)
                )
            }
        }

        // Wizard Question 1: Intended Usage
        item {
            WizardQuestionCard(
                stepNumber = "1",
                question = "What do you want to use the pot for?",
                testTag = "wizard_question_usage"
            ) {
                FlowRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    usageOptions.forEach { opt ->
                        SelectableChip(
                            label = opt,
                            isSelected = selectedUsage == opt,
                            onClick = { onUsageChange(opt) }
                        )
                    }
                }
            }
        }

        // Wizard Question 2: Capacity
        item {
            WizardQuestionCard(
                stepNumber = "2",
                question = "What capacity do you need?",
                testTag = "wizard_question_capacity"
            ) {
                FlowRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    capacityOptions.forEach { opt ->
                        SelectableChip(
                            label = opt,
                            isSelected = selectedCapacity == opt,
                            onClick = { onCapacityChange(opt) }
                        )
                    }
                }
            }
        }

        // Wizard Question 3: Heat Suitability
        item {
            WizardQuestionCard(
                stepNumber = "3",
                question = "Do you need direct flame / heat suitability?",
                testTag = "wizard_question_heat"
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    heatOptions.forEach { opt ->
                        SelectableChip(
                            label = opt,
                            isSelected = selectedHeat == opt,
                            onClick = { onHeatChange(opt) },
                            modifier = Modifier.weight(1f)
                        )
                    }
                }
            }
        }

        // Submit Button
        item {
            Button(
                onClick = onFindPot,
                enabled = !isLoading,
                colors = ButtonDefaults.buttonColors(containerColor = TerracottaPrimary),
                shape = RoundedCornerShape(14.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp)
                    .testTag("find_my_pot_button")
            ) {
                if (isLoading) {
                    CircularProgressIndicator(color = Color.White, modifier = Modifier.size(22.dp), strokeWidth = 2.5.dp)
                    Spacer(modifier = Modifier.width(10.dp))
                    Text("Matching Earthen Vessels...")
                } else {
                    Icon(Icons.Default.AutoAwesome, contentDescription = null, modifier = Modifier.size(20.dp))
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Find My Pot", style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold))
                }
            }
        }

        // Results Section
        if (results != null) {
            item {
                Spacer(modifier = Modifier.height(8.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.Psychology, contentDescription = null, tint = TerracottaPrimary, modifier = Modifier.size(22.dp))
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Top Recommended Clay Pots",
                        style = MaterialTheme.typography.titleLarge.copy(
                            fontWeight = FontWeight.Bold,
                            color = ClayEarthyBrown
                        )
                    )
                }
            }

            items(results) { rec ->
                RecommendationCardItem(
                    rec = rec,
                    onViewDetails = { onOpenPotDetail(rec.pot) }
                )
            }
        }

        item {
            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

@Composable
private fun WizardQuestionCard(
    stepNumber: String,
    question: String,
    testTag: String,
    content: @Composable () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .testTag(testTag),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = WarmCreamCard),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(18.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(28.dp)
                        .clip(CircleShape)
                        .background(TerracottaPrimary),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = stepNumber,
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontSize = 13.sp
                    )
                }
                Spacer(modifier = Modifier.width(10.dp))
                Text(
                    text = question,
                    style = MaterialTheme.typography.titleSmall.copy(
                        fontWeight = FontWeight.Bold,
                        color = ClayEarthyBrown
                    )
                )
            }

            Spacer(modifier = Modifier.height(14.dp))

            content()
        }
    }
}

@Composable
private fun SelectableChip(
    label: String,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(12.dp))
            .background(if (isSelected) TerracottaPrimary else WarmCreamSurface)
            .border(
                1.dp,
                if (isSelected) TerracottaPrimary else ClayBorder,
                RoundedCornerShape(12.dp)
            )
            .clickable { onClick() }
            .padding(horizontal = 14.dp, vertical = 10.dp),
        contentAlignment = Alignment.Center
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            if (isSelected) {
                Icon(Icons.Default.Check, contentDescription = null, tint = Color.White, modifier = Modifier.size(16.dp))
                Spacer(modifier = Modifier.width(6.dp))
            }
            Text(
                text = label,
                style = MaterialTheme.typography.labelMedium.copy(
                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                    color = if (isSelected) Color.White else ClayEarthyBrown
                )
            )
        }
    }
}

@Composable
private fun RecommendationCardItem(
    rec: PotRecommendation,
    onViewDetails: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .testTag("recommendation_card_${rec.pot.id}"),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = WarmCreamCard),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(18.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                ClayPotIllustration(
                    presetKey = rec.pot.presetKey,
                    isScanning = false,
                    size = 80.dp
                )

                Spacer(modifier = Modifier.width(14.dp))

                Column(modifier = Modifier.weight(1f)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = rec.pot.category.displayName,
                            style = MaterialTheme.typography.labelSmall.copy(
                                color = TerracottaPrimary,
                                fontWeight = FontWeight.Bold
                            )
                        )

                        // Match Score Badge
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(12.dp))
                                .background(OrganicSageContainer)
                                .padding(horizontal = 8.dp, vertical = 3.dp)
                        ) {
                            Text(
                                text = "${rec.matchScore}% Match",
                                style = MaterialTheme.typography.labelSmall.copy(
                                    color = OrganicSageGreen,
                                    fontWeight = FontWeight.Bold
                                )
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        text = rec.pot.name,
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.Bold,
                            color = ClayEarthyBrown
                        )
                    )

                    Spacer(modifier = Modifier.height(2.dp))

                    Text(
                        text = "Capacity: ${rec.pot.capacity} • Heat: ${rec.pot.heatSuitability.displayName}",
                        style = MaterialTheme.typography.bodySmall.copy(color = ClayWarmBrown)
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Match Reason Callout
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(12.dp))
                    .background(WarmCreamSurface)
                    .padding(12.dp)
            ) {
                Text(
                    text = "💡 ${rec.matchReason}",
                    style = MaterialTheme.typography.bodySmall.copy(
                        color = ClayEarthyBrown,
                        fontWeight = FontWeight.Medium,
                        lineHeight = 16.sp
                    )
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            Button(
                onClick = onViewDetails,
                colors = ButtonDefaults.buttonColors(containerColor = TerracottaPrimary),
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.fillMaxWidth().height(42.dp)
            ) {
                Icon(Icons.AutoMirrored.Filled.MenuBook, contentDescription = null, modifier = Modifier.size(16.dp))
                Spacer(modifier = Modifier.width(6.dp))
                Text("View Pot Details & Care", fontWeight = FontWeight.Bold)
            }
        }
    }
}
