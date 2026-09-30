package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.DeleteSweep
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.SquareFoot
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.AnalysisResult
import com.example.model.HeatSuitability
import com.example.ui.components.ClayPotIllustration
import com.example.ui.theme.ClayBorder
import com.example.ui.theme.ClayEarthyBrown
import com.example.ui.theme.ClayWarmBrown
import com.example.ui.theme.StatusLimited
import com.example.ui.theme.StatusNotRecommended
import com.example.ui.theme.StatusSuitable
import com.example.ui.theme.StatusUnknown
import com.example.ui.theme.TerracottaContainer
import com.example.ui.theme.TerracottaPrimary
import com.example.ui.theme.WarmCreamBg
import com.example.ui.theme.WarmCreamCard
import com.example.ui.theme.WarmCreamSurface
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun HistoryScreen(
    historyItems: List<AnalysisResult>,
    searchQuery: String,
    onSearchChange: (String) -> Unit,
    onViewResult: (AnalysisResult) -> Unit,
    onDeleteItem: (String) -> Unit,
    onClearAll: () -> Unit,
    onNavigateToAnalyze: () -> Unit,
    modifier: Modifier = Modifier
) {
    val filteredItems = historyItems.filter { item ->
        searchQuery.isBlank() ||
                item.potType.contains(searchQuery, ignoreCase = true) ||
                item.material.contains(searchQuery, ignoreCase = true) ||
                item.recommendedUsage.any { it.contains(searchQuery, ignoreCase = true) }
    }

    val dateFormatter = SimpleDateFormat("MMM dd, yyyy • hh:mm a", Locale.getDefault())

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(WarmCreamBg)
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Spacer(modifier = Modifier.height(4.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = "Analysis History",
                        style = MaterialTheme.typography.headlineMedium.copy(
                            fontWeight = FontWeight.Bold,
                            color = ClayEarthyBrown
                        )
                    )
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        text = "Local database storage of previous pottery diagnostics",
                        style = MaterialTheme.typography.bodySmall.copy(color = ClayWarmBrown)
                    )
                }

                if (historyItems.isNotEmpty()) {
                    IconButton(
                        onClick = onClearAll,
                        modifier = Modifier.testTag("clear_history_button")
                    ) {
                        Icon(
                            imageVector = Icons.Default.DeleteSweep,
                            contentDescription = "Clear All History",
                            tint = Color(0xFFC62828)
                        )
                    }
                }
            }
        }

        // Search Bar
        if (historyItems.isNotEmpty()) {
            item {
                OutlinedTextField(
                    value = searchQuery,
                    onValueChange = onSearchChange,
                    placeholder = { Text("Search scanned pots by type or recipe...") },
                    leadingIcon = { Icon(Icons.Default.Search, contentDescription = null, tint = TerracottaPrimary) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("history_search_bar"),
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
        }

        // Empty state
        if (historyItems.isEmpty()) {
            item {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 24.dp)
                        .testTag("empty_history_card"),
                    shape = RoundedCornerShape(24.dp),
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
                                .size(64.dp)
                                .clip(RoundedCornerShape(16.dp))
                                .background(TerracottaContainer.copy(alpha = 0.5f)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.History,
                                contentDescription = null,
                                tint = TerracottaPrimary,
                                modifier = Modifier.size(36.dp)
                            )
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        Text(
                            text = "No Analysis History Yet",
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontWeight = FontWeight.Bold,
                                color = ClayEarthyBrown
                            )
                        )

                        Spacer(modifier = Modifier.height(6.dp))

                        Text(
                            text = "Upload or scan your first clay pot to generate thermal guidance, capacity estimates, and persistent local records.",
                            style = MaterialTheme.typography.bodySmall.copy(
                                color = ClayWarmBrown,
                                textAlign = TextAlign.Center,
                                lineHeight = 17.sp
                            )
                        )

                        Spacer(modifier = Modifier.height(20.dp))

                        Button(
                            onClick = onNavigateToAnalyze,
                            colors = ButtonDefaults.buttonColors(containerColor = TerracottaPrimary),
                            shape = RoundedCornerShape(12.dp),
                            modifier = Modifier.testTag("empty_history_analyze_button")
                        ) {
                            Icon(Icons.Default.CameraAlt, contentDescription = null, modifier = Modifier.size(18.dp))
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("Analyze Your First Pot")
                        }
                    }
                }
            }
        } else if (filteredItems.isEmpty()) {
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = WarmCreamCard)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "No matching history entries",
                            style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold, color = ClayEarthyBrown)
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "Try searching with a different term.",
                            style = MaterialTheme.typography.bodySmall.copy(color = ClayWarmBrown)
                        )
                    }
                }
            }
        } else {
            items(filteredItems, key = { it.id }) { item ->
                HistoryItemCard(
                    item = item,
                    dateString = dateFormatter.format(Date(item.timestamp)),
                    onView = { onViewResult(item) },
                    onDelete = { onDeleteItem(item.id) }
                )
            }
        }

        item {
            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

@Composable
private fun HistoryItemCard(
    item: AnalysisResult,
    dateString: String,
    onView: () -> Unit,
    onDelete: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onView() }
            .testTag("history_item_${item.id}"),
        shape = RoundedCornerShape(18.dp),
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
                    presetKey = item.presetKey,
                    isScanning = false,
                    size = 72.dp
                )

                Spacer(modifier = Modifier.width(14.dp))

                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = dateString,
                        style = MaterialTheme.typography.labelSmall.copy(
                            color = ClayWarmBrown,
                            fontSize = 11.sp
                        )
                    )

                    Spacer(modifier = Modifier.height(2.dp))

                    Text(
                        text = item.potType,
                        style = MaterialTheme.typography.titleSmall.copy(
                            fontWeight = FontWeight.Bold,
                            color = ClayEarthyBrown
                        ),
                        maxLines = 1
                    )

                    Spacer(modifier = Modifier.height(2.dp))

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.SquareFoot, contentDescription = null, tint = TerracottaPrimary, modifier = Modifier.size(14.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = item.estimatedCapacity,
                            style = MaterialTheme.typography.bodySmall.copy(
                                fontWeight = FontWeight.SemiBold,
                                color = ClayEarthyBrown
                            )
                        )
                        Spacer(modifier = Modifier.width(8.dp))

                        val (heatColor, heatBg) = when (item.heatSuitability) {
                            HeatSuitability.SUITABLE -> StatusSuitable to StatusSuitable.copy(alpha = 0.15f)
                            HeatSuitability.LIMITED -> StatusLimited to StatusLimited.copy(alpha = 0.15f)
                            HeatSuitability.NOT_RECOMMENDED -> StatusNotRecommended to StatusNotRecommended.copy(alpha = 0.15f)
                            HeatSuitability.UNKNOWN -> StatusUnknown to StatusUnknown.copy(alpha = 0.15f)
                        }

                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(8.dp))
                                .background(heatBg)
                                .padding(horizontal = 6.dp, vertical = 2.dp)
                        ) {
                            Text(
                                text = item.heatSuitability.displayName,
                                style = MaterialTheme.typography.labelSmall.copy(
                                    color = heatColor,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 10.sp
                                )
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Action Buttons
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Button(
                    onClick = onView,
                    colors = ButtonDefaults.buttonColors(containerColor = TerracottaPrimary),
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier.weight(1f).height(38.dp)
                ) {
                    Icon(Icons.Default.Visibility, contentDescription = null, modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(6.dp))
                    Text("View Result", style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold))
                }

                OutlinedButton(
                    onClick = onDelete,
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = Color(0xFFC62828)),
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier.height(38.dp)
                ) {
                    Icon(Icons.Default.Delete, contentDescription = "Delete", modifier = Modifier.size(16.dp))
                }
            }
        }
    }
}
