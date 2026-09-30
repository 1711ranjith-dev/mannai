package com.example.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme = darkColorScheme(
    primary = DarkTerracotta,
    onPrimary = DarkClayBg,
    primaryContainer = TerracottaPrimaryVariant,
    onPrimaryContainer = Color(0xFFFFDBCF),
    secondary = OrganicSageLight,
    onSecondary = Color(0xFF003922),
    secondaryContainer = OrganicSageGreen,
    onSecondaryContainer = OrganicSageContainer,
    tertiary = AmberAccent,
    background = DarkClayBg,
    onBackground = DarkCreamText,
    surface = DarkClaySurface,
    onSurface = DarkCreamText,
    surfaceVariant = DarkClayCard,
    onSurfaceVariant = Color(0xFFD7C2B8),
    outline = Color(0xFF8C7164)
)

private val LightColorScheme = lightColorScheme(
    primary = TerracottaPrimary,
    onPrimary = Color.White,
    primaryContainer = TerracottaContainer,
    onPrimaryContainer = OnTerracottaContainer,
    secondary = OrganicSageGreen,
    onSecondary = Color.White,
    secondaryContainer = OrganicSageContainer,
    onSecondaryContainer = OnOrganicSageContainer,
    tertiary = AmberAccent,
    background = WarmCreamBg,
    onBackground = ClayEarthyBrown,
    surface = WarmCreamCard,
    onSurface = ClayEarthyBrown,
    surfaceVariant = WarmCreamSurface,
    onSurfaceVariant = ClayWarmBrown,
    outline = ClayBorder
)

@Composable
fun TerraMechTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false, // Keep signature TerraMech earthen brand palette
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
