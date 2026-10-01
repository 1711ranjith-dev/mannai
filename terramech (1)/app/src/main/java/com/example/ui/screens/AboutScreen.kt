package com.example.ui.screens

import androidx.compose.foundation.background
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.HistoryEdu
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.material.icons.filled.Psychology
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.components.ClayPotIllustration
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

@Composable
fun AboutScreen(
    modifier: Modifier = Modifier
) {
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
                    text = "About TerraMech",
                    style = MaterialTheme.typography.headlineMedium.copy(
                        fontWeight = FontWeight.Bold,
                        color = ClayEarthyBrown
                    )
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Bridging Millennia of Earthenware Wisdom with Modern Artificial Intelligence",
                    style = MaterialTheme.typography.bodyMedium.copy(color = ClayWarmBrown)
                )
            }
        }

        // Hero Graphic Card
        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("about_hero_card"),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = WarmCreamCard),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(18.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    ClayPotIllustration(
                        presetKey = "handi",
                        isScanning = true,
                        size = 100.dp
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "TerraMech AI",
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontWeight = FontWeight.Bold,
                                color = TerracottaPrimary
                            )
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "A tribute to traditional village potters, health-conscious home cooks, and sustainable culinary heritage.",
                            style = MaterialTheme.typography.bodySmall.copy(color = ClayWarmBrown, lineHeight = 16.sp)
                        )
                    }
                }
            }
        }

        // Our Purpose Card
        item {
            AboutSectionCard(
                icon = Icons.Default.Favorite,
                iconColor = TerracottaPrimary,
                title = "Our Purpose",
                content = "TerraMech was created to empower everyone to confidently embrace clay pot cooking. Earthen cookware imparts essential minerals (calcium, magnesium, iron, phosphorus), neutralizes dietary acidity through alkaline clay, and elevates flavor through slow thermal circulation. Our purpose is to make this traditional knowledge safe, accessible, and intuitive."
            )
        }

        // The Problem Card
        item {
            AboutSectionCard(
                icon = Icons.Default.Warning,
                iconColor = Color(0xFFD97706),
                title = "The Problem",
                content = "Traditional clay pot knowledge is disappearing. Modern consumers frequently experience cracked pots from thermal shock, accidental chemical contamination from dish soap, or confusion about whether a decorative glazed vessel can be placed on an open gas stove. Without guidance, users revert to non-stick pans coated with PFAS chemicals."
            )
        }

        // Our Solution Card
        item {
            AboutSectionCard(
                icon = Icons.Default.AutoAwesome,
                iconColor = OrganicSageGreen,
                title = "Our Solution",
                content = "TerraMech delivers instant visual diagnostics. By analyzing vessel silhouette, wall thickness, clay finish, and curvature, our AI provides clear heat ratings, capacity estimates, step-by-step seasoning recipes, and dish compatibility before your pot touches the flame."
            )
        }

        // Our Vision Card
        item {
            AboutSectionCard(
                icon = Icons.Default.Lightbulb,
                iconColor = Color(0xFF0284C7),
                title = "Our Vision",
                content = "We envision a future where modern culinary technology elevates sustainable, zero-waste, artisanal pottery. By connecting households with proper clay care, we support local artisan guilds while helping families enjoy healthier, chemical-free home cooking."
            )
        }

        // Clay Types Breakdown
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = WarmCreamCard)
            ) {
                Column(modifier = Modifier.padding(18.dp)) {
                    Text(
                        text = "The Three Traditional Pottery Classes",
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.Bold,
                            color = ClayEarthyBrown
                        )
                    )
                    Spacer(modifier = Modifier.height(12.dp))

                    ClayTypeItem(
                        name = "1. Raw Unglazed Terracotta",
                        desc = "Highly porous and breathable. Best for slow stews, curries, and evaporative water cooling. Requires initial water & oil seasoning.",
                        badge = "Heat Safe with Diffuser"
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    ClayTypeItem(
                        name = "2. Black Earthen Pottery (Nizamabad)",
                        desc = "Fired in an oxygen-deprived smoke pit. Infused with carbon luster. Excellent heat retention and naturally slick surface.",
                        badge = "Exceptional Flavor"
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    ClayTypeItem(
                        name = "3. Vitrified Glazed Ceramics",
                        desc = "Sealed with silica glaze. Impermeable to moisture and acid. Perfect for serving and pickling, but NOT suitable for direct stovetop flames.",
                        badge = "Ambient & Serving Only"
                    )
                }
            }
        }

        item {
            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

@Composable
private fun AboutSectionCard(
    icon: ImageVector,
    iconColor: Color,
    title: String,
    content: String
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = WarmCreamCard),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
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
                        .background(iconColor.copy(alpha = 0.14f)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(imageVector = icon, contentDescription = null, tint = iconColor, modifier = Modifier.size(20.dp))
                }
                Spacer(modifier = Modifier.width(12.dp))
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Bold,
                        color = ClayEarthyBrown
                    )
                )
            }
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = content,
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = ClayWarmBrown,
                    lineHeight = 20.sp
                )
            )
        }
    }
}

@Composable
private fun ClayTypeItem(
    name: String,
    desc: String,
    badge: String
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(WarmCreamSurface)
            .padding(12.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(name, fontWeight = FontWeight.Bold, color = ClayEarthyBrown, style = MaterialTheme.typography.titleSmall)
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(6.dp))
                    .background(TerracottaContainer.copy(alpha = 0.6f))
                    .padding(horizontal = 6.dp, vertical = 2.dp)
            ) {
                Text(badge, color = TerracottaPrimary, fontWeight = FontWeight.Bold, fontSize = 10.sp)
            }
        }
        Spacer(modifier = Modifier.height(4.dp))
        Text(desc, style = MaterialTheme.typography.bodySmall.copy(color = ClayWarmBrown, lineHeight = 16.sp))
    }
}
