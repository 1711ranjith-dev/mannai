package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.LocalFireDepartment
import androidx.compose.material.icons.filled.OutdoorGrill
import androidx.compose.material.icons.filled.Restaurant
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.SetMeal
import androidx.compose.material.icons.filled.SoupKitchen
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.VolunteerActivism
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
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
import com.example.model.ClayRecipe
import com.example.ui.theme.ClayBorder
import com.example.ui.theme.ClayEarthyBrown
import com.example.ui.theme.ClayWarmBrown
import com.example.ui.theme.OrganicSageContainer
import com.example.ui.theme.OrganicSageGreen
import com.example.ui.theme.StatusLimited
import com.example.ui.theme.TerracottaContainer
import com.example.ui.theme.TerracottaPrimary
import com.example.ui.theme.WarmCreamBg
import com.example.ui.theme.WarmCreamCard
import com.example.ui.theme.WarmCreamSurface

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ClayKitchenScreen(
    recipes: List<ClayRecipe>,
    searchQuery: String,
    selectedRecipe: ClayRecipe?,
    onSearchChange: (String) -> Unit,
    onSelectRecipe: (ClayRecipe?) -> Unit,
    onCookWithPot: (String) -> Unit,
    onAskSage: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val filteredRecipes = recipes.filter { recipe ->
        recipe.title.contains(searchQuery, ignoreCase = true) ||
                recipe.potType.contains(searchQuery, ignoreCase = true) ||
                recipe.description.contains(searchQuery, ignoreCase = true)
    }

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
                            .background(OrganicSageContainer)
                            .padding(horizontal = 8.dp, vertical = 4.dp)
                    ) {
                        Text(
                            text = "CLAY KITCHEN",
                            style = MaterialTheme.typography.labelSmall.copy(
                                color = OrganicSageGreen,
                                fontWeight = FontWeight.Bold,
                                letterSpacing = 1.sp
                            )
                        )
                    }
                    Text(
                        text = "• Earthen Gastronomy",
                        style = MaterialTheme.typography.bodySmall.copy(color = ClayWarmBrown)
                    )
                }

                Spacer(modifier = Modifier.height(6.dp))

                Text(
                    text = "Earthen Pot Recipes & Thermal Science",
                    style = MaterialTheme.typography.headlineMedium.copy(
                        fontWeight = FontWeight.Bold,
                        color = ClayEarthyBrown
                    )
                )
                Text(
                    text = "Cooking in unglazed earthenware neutralizes food acidity, retains 100% steam moisture, and enhances flavors with natural roasted minerals.",
                    style = MaterialTheme.typography.bodyMedium.copy(color = ClayWarmBrown)
                )
            }
        }

        // Thermal Golden Rules Banner
        item {
            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = WarmCreamCard),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                border = androidx.compose.foundation.BorderStroke(1.dp, ClayBorder)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Icon(Icons.Default.LocalFireDepartment, contentDescription = null, tint = TerracottaPrimary)
                        Text(
                            text = "Earthen Flame Golden Rules",
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontWeight = FontWeight.Bold,
                                color = TerracottaPrimary
                            )
                        )
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Surface(
                            shape = RoundedCornerShape(8.dp),
                            color = TerracottaContainer,
                            modifier = Modifier.weight(1f)
                        ) {
                            Column(modifier = Modifier.padding(8.dp)) {
                                Text("1. Slow Heating", fontWeight = FontWeight.Bold, style = MaterialTheme.typography.labelSmall.copy(color = TerracottaPrimary))
                                Text("Always begin on low flame for 5 mins before raising.", style = MaterialTheme.typography.bodySmall.copy(fontSize = 11.sp, color = ClayWarmBrown))
                            }
                        }

                        Surface(
                            shape = RoundedCornerShape(8.dp),
                            color = OrganicSageContainer,
                            modifier = Modifier.weight(1f)
                        ) {
                            Column(modifier = Modifier.padding(8.dp)) {
                                Text("2. Heat Diffuser", fontWeight = FontWeight.Bold, style = MaterialTheme.typography.labelSmall.copy(color = OrganicSageGreen))
                                Text("Use a metal diffuser plate on gas & induction stoves.", style = MaterialTheme.typography.bodySmall.copy(fontSize = 11.sp, color = ClayWarmBrown))
                            }
                        }
                    }
                }
            }
        }

        // Search Bar
        item {
            OutlinedTextField(
                value = searchQuery,
                onValueChange = onSearchChange,
                modifier = Modifier.fillMaxWidth().testTag("recipe_search_field"),
                placeholder = { Text("Search recipes: Biryani, Fish Curry, Curd, Dal...") },
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
        }

        // Recipe Cards List
        items(filteredRecipes) { recipe ->
            val isSelected = selectedRecipe?.id == recipe.id

            Card(
                onClick = { onSelectRecipe(if (isSelected) null else recipe) },
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = if (isSelected) WarmCreamSurface else WarmCreamCard
                ),
                border = androidx.compose.foundation.BorderStroke(
                    1.dp,
                    if (isSelected) TerracottaPrimary else ClayBorder
                ),
                modifier = Modifier.fillMaxWidth().testTag("recipe_card_${recipe.id}")
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = recipe.title,
                                style = MaterialTheme.typography.titleLarge.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = ClayEarthyBrown
                                )
                            )
                            Text(
                                text = "Best in: ${recipe.potType}",
                                style = MaterialTheme.typography.labelMedium.copy(
                                    color = TerracottaPrimary,
                                    fontWeight = FontWeight.SemiBold
                                )
                            )
                        }

                        Surface(
                            shape = RoundedCornerShape(8.dp),
                            color = TerracottaContainer
                        ) {
                            Row(
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(4.dp)
                            ) {
                                Icon(Icons.Default.LocalFireDepartment, contentDescription = null, tint = TerracottaPrimary, modifier = Modifier.size(14.dp))
                                Text(
                                    text = recipe.flameLevel,
                                    style = MaterialTheme.typography.labelSmall.copy(
                                        color = TerracottaPrimary,
                                        fontWeight = FontWeight.Bold
                                    )
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    Text(
                        text = recipe.description,
                        style = MaterialTheme.typography.bodyMedium.copy(
                            color = ClayEarthyBrown,
                            lineHeight = 20.sp
                        )
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    FlowRow(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Surface(
                            shape = RoundedCornerShape(6.dp),
                            color = WarmCreamSurface
                        ) {
                            Row(
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(4.dp)
                            ) {
                                Icon(Icons.Default.AccessTime, contentDescription = null, tint = ClayWarmBrown, modifier = Modifier.size(14.dp))
                                Text(recipe.cookTime, style = MaterialTheme.typography.labelSmall.copy(color = ClayWarmBrown, fontWeight = FontWeight.Bold))
                            }
                        }
                    }

                    // Expanded recipe content
                    AnimatedVisibility(visible = isSelected) {
                        Column(modifier = Modifier.padding(top = 14.dp)) {
                            // Earthen benefit callout
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clip(RoundedCornerShape(10.dp))
                                    .background(OrganicSageContainer)
                                    .padding(12.dp)
                            ) {
                                Row(
                                    verticalAlignment = Alignment.Top,
                                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                                ) {
                                    Icon(Icons.Default.Star, contentDescription = null, tint = OrganicSageGreen, modifier = Modifier.size(18.dp))
                                    Column {
                                        Text(
                                            text = "Why Clay Pot Matters:",
                                            style = MaterialTheme.typography.labelMedium.copy(
                                                fontWeight = FontWeight.Bold,
                                                color = OrganicSageGreen
                                            )
                                        )
                                        Text(
                                            text = recipe.earthenBenefit,
                                            style = MaterialTheme.typography.bodySmall.copy(
                                                color = ClayEarthyBrown,
                                                lineHeight = 18.sp
                                            )
                                        )
                                    }
                                }
                            }

                            Spacer(modifier = Modifier.height(14.dp))

                            Text(
                                text = "Ingredients:",
                                style = MaterialTheme.typography.titleMedium.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = ClayEarthyBrown
                                )
                            )

                            recipe.ingredients.forEach { item ->
                                Row(
                                    modifier = Modifier.padding(vertical = 2.dp),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                                ) {
                                    Box(modifier = Modifier.size(6.dp).clip(CircleShape).background(TerracottaPrimary))
                                    Text(text = item, style = MaterialTheme.typography.bodySmall.copy(color = ClayWarmBrown))
                                }
                            }

                            Spacer(modifier = Modifier.height(14.dp))

                            Text(
                                text = "Artisanal Method:",
                                style = MaterialTheme.typography.titleMedium.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = ClayEarthyBrown
                                )
                            )

                            recipe.cookingSteps.forEachIndexed { idx, step ->
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
                                            .background(TerracottaContainer),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Text(
                                            text = "${idx + 1}",
                                            style = MaterialTheme.typography.labelSmall.copy(
                                                color = TerracottaPrimary,
                                                fontWeight = FontWeight.Bold
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

                            Spacer(modifier = Modifier.height(14.dp))

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                Button(
                                    onClick = { onAskSage("Give me cooking tips for this recipe in a clay pot: ${recipe.title}") },
                                    colors = ButtonDefaults.buttonColors(containerColor = TerracottaPrimary),
                                    shape = RoundedCornerShape(10.dp),
                                    modifier = Modifier.weight(1f)
                                ) {
                                    Text("Ask Sage Chef Tips", fontSize = 12.sp)
                                }
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
                                text = "View recipe & clay thermal tips",
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
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}
