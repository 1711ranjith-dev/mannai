package com.example.ui.screens

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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CleaningServices
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.LocalFireDepartment
import androidx.compose.material.icons.filled.Science
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.SquareFoot
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.model.ClayPot
import com.example.model.HeatSuitability
import com.example.model.PotCategory
import com.example.ui.components.ClayPotIllustration
import com.example.ui.theme.ClayBorder
import com.example.ui.theme.ClayEarthyBrown
import com.example.ui.theme.ClayWarmBrown
import com.example.ui.theme.OrganicSageGreen
import com.example.ui.theme.StatusLimited
import com.example.ui.theme.StatusNotRecommended
import com.example.ui.theme.StatusSuitable
import com.example.ui.theme.StatusUnknown
import com.example.ui.theme.TerracottaContainer
import com.example.ui.theme.TerracottaPrimary
import com.example.ui.theme.WarmCreamBg
import com.example.ui.theme.WarmCreamCard
import com.example.ui.theme.WarmCreamSurface

@Composable
fun PotGuideScreen(
    pots: List<ClayPot>,
    searchQuery: String,
    selectedCategory: PotCategory,
    selectedPotDetail: ClayPot?,
    onSearchChange: (String) -> Unit,
    onCategorySelect: (PotCategory) -> Unit,
    onOpenPotDetail: (ClayPot) -> Unit,
    onClosePotDetail: () -> Unit,
    onAnalyzePreset: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val filteredPots = pots.filter { pot ->
        val matchesCategory = selectedCategory == PotCategory.ALL || pot.category == selectedCategory
        val matchesQuery = searchQuery.isBlank() ||
                pot.name.contains(searchQuery, ignoreCase = true) ||
                pot.description.contains(searchQuery, ignoreCase = true) ||
                pot.material.contains(searchQuery, ignoreCase = true) ||
                pot.usage.any { it.contains(searchQuery, ignoreCase = true) }
        matchesCategory && matchesQuery
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
            Column(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = "Traditional Clay Pot Guide",
                    style = MaterialTheme.typography.headlineMedium.copy(
                        fontWeight = FontWeight.Bold,
                        color = ClayEarthyBrown
                    )
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Explore traditional terracotta vessels, thermal properties, and heritage culinary lore.",
                    style = MaterialTheme.typography.bodyMedium.copy(color = ClayWarmBrown)
                )
            }
        }

        // Search Bar
        item {
            OutlinedTextField(
                value = searchQuery,
                onValueChange = onSearchChange,
                placeholder = { Text("Search pots by name, curry type, or clay material...") },
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = null, tint = TerracottaPrimary) },
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("guide_search_bar"),
                shape = RoundedCornerShape(14.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = TerracottaPrimary,
                    unfocusedBorderColor = ClayBorder,
                    focusedContainerColor = WarmCreamCard,
                    unfocusedContainerColor = WarmCreamCard
                ),
                singleLine = true
            )
        }

        // Category Filter Chips
        item {
            val categories = listOf(
                PotCategory.ALL,
                PotCategory.COOKING,
                PotCategory.WATER_STORAGE,
                PotCategory.SERVING,
                PotCategory.STORAGE,
                PotCategory.FERMENTATION,
                PotCategory.TRADITIONAL,
                PotCategory.DECORATIVE
            )

            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                items(categories) { cat ->
                    val isSelected = selectedCategory == cat
                    FilterChip(
                        selected = isSelected,
                        onClick = { onCategorySelect(cat) },
                        label = {
                            Text(
                                text = cat.displayName,
                                style = MaterialTheme.typography.labelMedium.copy(
                                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                                )
                            )
                        },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = TerracottaPrimary,
                            selectedLabelColor = Color.White,
                            containerColor = WarmCreamCard,
                            labelColor = ClayEarthyBrown
                        ),
                        border = FilterChipDefaults.filterChipBorder(
                            enabled = true,
                            selected = isSelected,
                            borderColor = if (isSelected) TerracottaPrimary else ClayBorder
                        ),
                        shape = RoundedCornerShape(20.dp),
                        modifier = Modifier.testTag("filter_chip_${cat.name}")
                    )
                }
            }
        }

        // Pots Count
        item {
            Text(
                text = "Showing ${filteredPots.size} Earthen Vessels",
                style = MaterialTheme.typography.labelMedium.copy(
                    color = ClayWarmBrown,
                    fontWeight = FontWeight.SemiBold
                )
            )
        }

        // Pot Cards List
        if (filteredPots.isEmpty()) {
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(18.dp),
                    colors = CardDefaults.cardColors(containerColor = WarmCreamCard)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(32.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "No matching clay pots found",
                            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold, color = ClayEarthyBrown)
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "Try clearing your search query or choosing another category.",
                            style = MaterialTheme.typography.bodySmall.copy(color = ClayWarmBrown)
                        )
                    }
                }
            }
        } else {
            items(filteredPots) { pot ->
                PotCardItem(
                    pot = pot,
                    onViewDetails = { onOpenPotDetail(pot) },
                    onAnalyzePreset = { onAnalyzePreset(pot.presetKey) }
                )
            }
        }

        item {
            Spacer(modifier = Modifier.height(24.dp))
        }
    }

    // Detailed Modal Dialog when a pot is clicked
    if (selectedPotDetail != null) {
        PotDetailsModal(
            pot = selectedPotDetail,
            onDismiss = onClosePotDetail,
            onAnalyzePreset = {
                onClosePotDetail()
                onAnalyzePreset(selectedPotDetail.presetKey)
            }
        )
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun PotCardItem(
    pot: ClayPot,
    onViewDetails: () -> Unit,
    onAnalyzePreset: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onViewDetails() }
            .testTag("pot_card_${pot.id}"),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = WarmCreamCard),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                ClayPotIllustration(
                    presetKey = pot.presetKey,
                    isScanning = false,
                    size = 90.dp
                )

                Spacer(modifier = Modifier.width(14.dp))

                Column(modifier = Modifier.weight(1f)) {
                    // Category & Heat badge
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = pot.category.displayName,
                            style = MaterialTheme.typography.labelSmall.copy(
                                color = TerracottaPrimary,
                                fontWeight = FontWeight.Bold
                            )
                        )

                        val (heatColor, heatBg) = when (pot.heatSuitability) {
                            HeatSuitability.SUITABLE -> StatusSuitable to StatusSuitable.copy(alpha = 0.15f)
                            HeatSuitability.LIMITED -> StatusLimited to StatusLimited.copy(alpha = 0.15f)
                            HeatSuitability.NOT_RECOMMENDED -> StatusNotRecommended to StatusNotRecommended.copy(alpha = 0.15f)
                            HeatSuitability.UNKNOWN -> StatusUnknown to StatusUnknown.copy(alpha = 0.15f)
                        }

                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(12.dp))
                                .background(heatBg)
                                .padding(horizontal = 8.dp, vertical = 2.dp)
                        ) {
                            Text(
                                text = pot.heatSuitability.displayName,
                                style = MaterialTheme.typography.labelSmall.copy(
                                    color = heatColor,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 10.sp
                                )
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        text = pot.name,
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.Bold,
                            color = ClayEarthyBrown
                        ),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )

                    Spacer(modifier = Modifier.height(2.dp))

                    Text(
                        text = pot.description,
                        style = MaterialTheme.typography.bodySmall.copy(
                            color = ClayWarmBrown,
                            lineHeight = 16.sp
                        ),
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Metadata Row: Capacity & Material
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(12.dp))
                    .background(WarmCreamSurface)
                    .padding(horizontal = 12.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.SquareFoot, contentDescription = null, tint = TerracottaPrimary, modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = pot.capacity,
                        style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Bold, color = ClayEarthyBrown)
                    )
                }
                Text(
                    text = pot.material,
                    style = MaterialTheme.typography.bodySmall.copy(
                        color = ClayWarmBrown,
                        fontSize = 11.sp
                    ),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.padding(start = 12.dp)
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Usages tags
            FlowRow(
                horizontalArrangement = Arrangement.spacedBy(6.dp),
                verticalArrangement = Arrangement.spacedBy(6.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                pot.usage.take(3).forEach { u ->
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(8.dp))
                            .background(TerracottaContainer.copy(alpha = 0.4f))
                            .padding(horizontal = 8.dp, vertical = 2.dp)
                    ) {
                        Text(
                            text = u,
                            style = MaterialTheme.typography.labelSmall.copy(
                                color = TerracottaPrimary,
                                fontWeight = FontWeight.Medium
                            )
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            // Action: View Details Button
            Button(
                onClick = onViewDetails,
                colors = ButtonDefaults.buttonColors(containerColor = TerracottaPrimary),
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(42.dp)
                    .testTag("view_details_button_${pot.id}")
            ) {
                Icon(Icons.Default.Visibility, contentDescription = null, modifier = Modifier.size(16.dp))
                Spacer(modifier = Modifier.width(6.dp))
                Text("View Full Details", fontWeight = FontWeight.Bold)
            }
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun PotDetailsModal(
    pot: ClayPot,
    onDismiss: () -> Unit,
    onAnalyzePreset: () -> Unit
) {
    Dialog(onDismissRequest = onDismiss) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp)
                .testTag("pot_details_modal"),
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(containerColor = WarmCreamCard),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                // Header with Close
                item {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = pot.category.displayName,
                            style = MaterialTheme.typography.labelMedium.copy(
                                color = TerracottaPrimary,
                                fontWeight = FontWeight.Bold
                            )
                        )
                        IconButton(onClick = onDismiss) {
                            Icon(Icons.Default.Close, contentDescription = "Close", tint = ClayWarmBrown)
                        }
                    }
                }

                // Large illustration & Title
                item {
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        ClayPotIllustration(
                            presetKey = pot.presetKey,
                            isScanning = false,
                            size = 150.dp
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                        Text(
                            text = pot.name,
                            style = MaterialTheme.typography.titleLarge.copy(
                                fontWeight = FontWeight.Bold,
                                color = ClayEarthyBrown
                            )
                        )
                        Text(
                            text = "Heritage: ${pot.origin}",
                            style = MaterialTheme.typography.bodySmall.copy(color = ClayWarmBrown)
                        )
                    }
                }

                // Description
                item {
                    Text(
                        text = pot.description,
                        style = MaterialTheme.typography.bodyMedium.copy(color = ClayWarmBrown, lineHeight = 20.sp)
                    )
                }

                // Spec Grid
                item {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        SpecBox("Capacity", pot.capacity, Icons.Default.SquareFoot, Modifier.weight(1f))
                        SpecBox("Heat Rating", pot.heatSuitability.displayName, Icons.Default.LocalFireDepartment, Modifier.weight(1f))
                    }
                }

                // Material & Heat Notes
                item {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(14.dp),
                        colors = CardDefaults.cardColors(containerColor = WarmCreamSurface)
                    ) {
                        Column(modifier = Modifier.padding(14.dp)) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(Icons.Default.Science, contentDescription = null, tint = OrganicSageGreen, modifier = Modifier.size(18.dp))
                                Spacer(modifier = Modifier.width(6.dp))
                                Text("Material Formulation", fontWeight = FontWeight.Bold, color = ClayEarthyBrown)
                            }
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(pot.material, style = MaterialTheme.typography.bodySmall.copy(color = ClayWarmBrown))

                            Spacer(modifier = Modifier.height(10.dp))

                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(Icons.Default.LocalFireDepartment, contentDescription = null, tint = TerracottaPrimary, modifier = Modifier.size(18.dp))
                                Spacer(modifier = Modifier.width(6.dp))
                                Text("Heat Suitability Guidance", fontWeight = FontWeight.Bold, color = ClayEarthyBrown)
                            }
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(pot.heatSuitabilityNote, style = MaterialTheme.typography.bodySmall.copy(color = ClayWarmBrown))
                        }
                    }
                }

                // Recommended Usage Tags
                item {
                    Text("Recommended Usages", fontWeight = FontWeight.Bold, color = ClayEarthyBrown)
                    Spacer(modifier = Modifier.height(6.dp))
                    FlowRow(
                        horizontalArrangement = Arrangement.spacedBy(6.dp),
                        verticalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        pot.usage.forEach { u ->
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(12.dp))
                                    .background(TerracottaContainer.copy(alpha = 0.5f))
                                    .padding(horizontal = 10.dp, vertical = 4.dp)
                            ) {
                                Text(u, color = TerracottaPrimary, fontWeight = FontWeight.Medium, fontSize = 12.sp)
                            }
                        }
                    }
                }

                // Safety Guidelines
                item {
                    Text("Safety Protocols", fontWeight = FontWeight.Bold, color = ClayEarthyBrown)
                    Spacer(modifier = Modifier.height(6.dp))
                    pot.safetyGuidelines.forEach { g ->
                        Row(modifier = Modifier.padding(vertical = 2.dp), verticalAlignment = Alignment.Top) {
                            Icon(Icons.Default.Warning, contentDescription = null, tint = Color(0xFFD97706), modifier = Modifier.size(15.dp).padding(top = 2.dp))
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(g, style = MaterialTheme.typography.bodySmall.copy(color = ClayWarmBrown))
                        }
                    }
                }

                // Care & Seasoning
                item {
                    Text("Care & Seasoning Instructions", fontWeight = FontWeight.Bold, color = ClayEarthyBrown)
                    Spacer(modifier = Modifier.height(6.dp))
                    pot.careAndMaintenance.forEach { c ->
                        Row(modifier = Modifier.padding(vertical = 2.dp), verticalAlignment = Alignment.Top) {
                            Icon(Icons.Default.CleaningServices, contentDescription = null, tint = OrganicSageGreen, modifier = Modifier.size(15.dp).padding(top = 2.dp))
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(c, style = MaterialTheme.typography.bodySmall.copy(color = ClayWarmBrown))
                        }
                    }
                }

                // Bottom Action: Run Simulation with this pot
                item {
                    Spacer(modifier = Modifier.height(6.dp))
                    Button(
                        onClick = onAnalyzePreset,
                        colors = ButtonDefaults.buttonColors(containerColor = TerracottaPrimary),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.fillMaxWidth().height(46.dp)
                    ) {
                        Text("Simulate AI Scan on This Pot", fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
    }
}

@Composable
private fun SpecBox(
    title: String,
    value: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(12.dp))
            .background(WarmCreamSurface)
            .padding(10.dp)
    ) {
        Column {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(icon, contentDescription = null, tint = TerracottaPrimary, modifier = Modifier.size(14.dp))
                Spacer(modifier = Modifier.width(4.dp))
                Text(title, style = MaterialTheme.typography.labelSmall.copy(color = ClayWarmBrown, fontSize = 10.sp))
            }
            Spacer(modifier = Modifier.height(2.dp))
            Text(value, style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold, color = ClayEarthyBrown))
        }
    }
}
