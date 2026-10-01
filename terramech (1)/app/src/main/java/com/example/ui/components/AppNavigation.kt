package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.automirrored.filled.MenuBook
import androidx.compose.material.icons.automirrored.outlined.MenuBook
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Psychology
import androidx.compose.material.icons.filled.Hearing
import androidx.compose.material.icons.filled.LocalFireDepartment
import androidx.compose.material.icons.filled.Restaurant
import androidx.compose.material.icons.outlined.BookmarkBorder
import androidx.compose.material.icons.outlined.CameraAlt
import androidx.compose.material.icons.outlined.History
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.Psychology
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.DrawerState
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.NavigationRailItem
import androidx.compose.material3.NavigationRailItemDefaults
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
import com.example.ui.theme.ClayEarthyBrown
import com.example.ui.theme.ClayWarmBrown
import com.example.ui.theme.OrganicSageGreen
import com.example.ui.theme.TerracottaContainer
import com.example.ui.theme.TerracottaPrimary
import com.example.ui.theme.WarmCreamBg
import com.example.ui.theme.WarmCreamCard
import com.example.ui.theme.WarmCreamSurface

data class NavItem(
    val route: String,
    val title: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val badgeCount: Int = 0
)

val mainNavItems = listOf(
    NavItem("home", "Home", Icons.Filled.Home, Icons.Outlined.Home),
    NavItem("analyze", "Analyze", Icons.Filled.CameraAlt, Icons.Outlined.CameraAlt),
    NavItem("chat", "Sage AI", Icons.Filled.Psychology, Icons.Outlined.Psychology),
    NavItem("guide", "Pot Guide", Icons.AutoMirrored.Filled.MenuBook, Icons.AutoMirrored.Outlined.MenuBook),
    NavItem("history", "History", Icons.Filled.History, Icons.Outlined.History)
)

val drawerExtraNavItems = listOf(
    NavItem("curing", "Seasoning Studio", Icons.Filled.LocalFireDepartment, Icons.Filled.LocalFireDepartment),
    NavItem("doctor", "Pot Doctor & Ring", Icons.Filled.Hearing, Icons.Filled.Hearing),
    NavItem("kitchen", "Clay Kitchen", Icons.Filled.Restaurant, Icons.Filled.Restaurant),
    NavItem("recommend", "Pot Advisor", Icons.Filled.AutoAwesome, Icons.Filled.AutoAwesome),
    NavItem("about", "About TerraMech", Icons.Filled.Info, Icons.Outlined.Info)
)

@Composable
fun TerraMechBottomBar(
    currentScreen: String,
    onNavigate: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    NavigationBar(
        modifier = modifier
            .navigationBarsPadding()
            .testTag("bottom_nav_bar"),
        containerColor = WarmCreamCard,
        tonalElevation = 6.dp
    ) {
        mainNavItems.forEach { item ->
            val isSelected = currentScreen == item.route
            NavigationBarItem(
                selected = isSelected,
                onClick = { onNavigate(item.route) },
                icon = {
                    Icon(
                        imageVector = if (isSelected) item.selectedIcon else item.unselectedIcon,
                        contentDescription = item.title,
                        modifier = Modifier.size(24.dp)
                    )
                },
                label = {
                    Text(
                        text = item.title,
                        style = MaterialTheme.typography.labelSmall.copy(
                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                            fontSize = 10.5.sp
                        )
                    )
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = Color.White,
                    selectedTextColor = TerracottaPrimary,
                    indicatorColor = TerracottaPrimary,
                    unselectedIconColor = ClayWarmBrown,
                    unselectedTextColor = ClayWarmBrown
                ),
                modifier = Modifier.testTag("nav_item_${item.route}")
            )
        }
    }
}

val webRailNavItems = listOf(
    NavItem("home", "Home", Icons.Filled.Home, Icons.Outlined.Home),
    NavItem("analyze", "Analyze", Icons.Filled.CameraAlt, Icons.Outlined.CameraAlt),
    NavItem("guide", "Guide", Icons.AutoMirrored.Filled.MenuBook, Icons.AutoMirrored.Outlined.MenuBook),
    NavItem("chat", "Sage AI", Icons.Filled.Psychology, Icons.Outlined.Psychology),
    NavItem("curing", "Curing", Icons.Filled.LocalFireDepartment, Icons.Filled.LocalFireDepartment),
    NavItem("doctor", "Doctor", Icons.Filled.Hearing, Icons.Filled.Hearing),
    NavItem("kitchen", "Kitchen", Icons.Filled.Restaurant, Icons.Filled.Restaurant),
    NavItem("recommend", "Advisor", Icons.Filled.AutoAwesome, Icons.Filled.AutoAwesome),
    NavItem("history", "History", Icons.Filled.History, Icons.Outlined.History)
)

@Composable
fun TerraMechWebNavRail(
    currentScreen: String,
    onNavigate: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    NavigationRail(
        modifier = modifier
            .fillMaxHeight()
            .testTag("web_nav_rail"),
        containerColor = WarmCreamCard,
        contentColor = ClayEarthyBrown,
        header = {
            Box(
                modifier = Modifier
                    .padding(top = 16.dp, bottom = 12.dp)
                    .size(44.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(TerracottaPrimary)
                    .clickable { onNavigate("home") },
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.AutoAwesome,
                    contentDescription = "TerraMech Web",
                    tint = Color.White,
                    modifier = Modifier.size(24.dp)
                )
            }
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .verticalScroll(rememberScrollState())
                .padding(vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            webRailNavItems.forEach { item ->
                val isSelected = currentScreen == item.route
                NavigationRailItem(
                    selected = isSelected,
                    onClick = { onNavigate(item.route) },
                    icon = {
                        Icon(
                            imageVector = if (isSelected) item.selectedIcon else item.unselectedIcon,
                            contentDescription = item.title,
                            modifier = Modifier.size(22.dp)
                        )
                    },
                    label = {
                        Text(
                            text = item.title,
                            style = MaterialTheme.typography.labelSmall.copy(
                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                                fontSize = 10.sp
                            )
                        )
                    },
                    colors = NavigationRailItemDefaults.colors(
                        selectedIconColor = Color.White,
                        selectedTextColor = TerracottaPrimary,
                        indicatorColor = TerracottaPrimary,
                        unselectedIconColor = ClayWarmBrown,
                        unselectedTextColor = ClayWarmBrown
                    ),
                    modifier = Modifier.testTag("web_rail_${item.route}")
                )
            }
        }
    }
}


@Composable
fun TerraMechDrawerContent(
    currentScreen: String,
    onNavigate: (String) -> Unit,
    onCloseDrawer: () -> Unit,
    modifier: Modifier = Modifier
) {
    ModalDrawerSheet(
        modifier = modifier.fillMaxWidth(0.82f),
        drawerContainerColor = WarmCreamBg
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp)
        ) {
            // Drawer Header
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .clip(RoundedCornerShape(14.dp))
                    .background(TerracottaPrimary),
                contentAlignment = androidx.compose.ui.Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.AutoAwesome,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.size(28.dp)
                )
            }
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = "TerraMech",
                style = MaterialTheme.typography.headlineSmall.copy(
                    fontWeight = FontWeight.Bold,
                    color = TerracottaPrimary
                )
            )
            Text(
                text = "Traditional Clay + Modern AI",
                style = MaterialTheme.typography.bodySmall.copy(color = ClayWarmBrown)
            )

            Spacer(modifier = Modifier.height(24.dp))
            HorizontalDivider(color = WarmCreamSurface, thickness = 1.5.dp)
            Spacer(modifier = Modifier.height(16.dp))

            (mainNavItems + drawerExtraNavItems).forEach { item ->
                val isSelected = currentScreen == item.route
                NavigationDrawerItem(
                    label = {
                        Text(
                            text = item.title,
                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium
                        )
                    },
                    selected = isSelected,
                    onClick = {
                        onNavigate(item.route)
                        onCloseDrawer()
                    },
                    icon = {
                        Icon(
                            imageVector = if (isSelected) item.selectedIcon else item.unselectedIcon,
                            contentDescription = item.title
                        )
                    },
                    colors = NavigationDrawerItemDefaults.colors(
                        selectedContainerColor = TerracottaContainer,
                        selectedIconColor = TerracottaPrimary,
                        selectedTextColor = TerracottaPrimary,
                        unselectedContainerColor = Color.Transparent,
                        unselectedIconColor = ClayWarmBrown,
                        unselectedTextColor = ClayEarthyBrown
                    ),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier
                        .padding(vertical = 4.dp)
                        .testTag("drawer_item_${item.route}")
                )
            }
        }
    }
}
