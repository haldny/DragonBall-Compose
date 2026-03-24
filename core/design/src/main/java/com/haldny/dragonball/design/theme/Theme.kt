package com.haldny.dragonball.design.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val DragonBallDarkScheme = darkColorScheme(
    primary = DragonBallColors.kiGold,
    onPrimary = DragonBallColors.spaceBlack,
    primaryContainer = DragonBallColors.panelElevated,
    onPrimaryContainer = DragonBallColors.kiGold,
    secondary = DragonBallColors.auraCyan,
    onSecondary = DragonBallColors.spaceBlack,
    secondaryContainer = DragonBallColors.panel,
    onSecondaryContainer = DragonBallColors.auraCyan,
    tertiary = DragonBallColors.plasmaViolet,
    onTertiary = Color.White,
    tertiaryContainer = DragonBallColors.panel,
    onTertiaryContainer = DragonBallColors.plasmaViolet,
    background = DragonBallColors.spaceBlack,
    onBackground = DragonBallColors.textPrimary,
    surface = DragonBallColors.spaceNavy,
    onSurface = DragonBallColors.textPrimary,
    surfaceVariant = DragonBallColors.panelElevated,
    // Slightly brighter than legacy “muted” so metadata on surfaceVariant panels stays readable (WCAG-ish).
    onSurfaceVariant = Color(0xFFB8C9E0),
    outline = DragonBallColors.auraCyan.copy(alpha = 0.35f),
    outlineVariant = DragonBallColors.gridLine,
    error = Color(0xFFFF5252),
    onError = Color.White,
)

private val DragonBallLightScheme = lightColorScheme(
    primary = Color(0xFFE65100),
    onPrimary = Color.White,
    primaryContainer = Color(0xFFFFE0B2),
    onPrimaryContainer = Color(0xFF3E1500),
    secondary = Color(0xFF00838F),
    onSecondary = Color.White,
    secondaryContainer = Color(0xFFB2EBF2),
    onSecondaryContainer = Color(0xFF004D54),
    tertiary = Color(0xFF6A1B9A),
    onTertiary = Color.White,
    tertiaryContainer = Color(0xFFE1BEE7),
    onTertiaryContainer = Color(0xFF2E1048),
    background = Color(0xFFF5F7FB),
    onBackground = Color(0xFF0D1624),
    surface = Color.White,
    onSurface = Color(0xFF0D1624),
    surfaceVariant = Color(0xFFE4E9F2),
    onSurfaceVariant = Color(0xFF4A5A72),
    outline = Color(0xFF90A4C4),
    outlineVariant = Color(0xFFD0D8E6),
    error = Color(0xFFB3261E),
    onError = Color.White,
)

@Composable
fun DragonBallComposeTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    /** Off by default so the Dragon Ball palette is consistent; enable for Material dynamic colors on API 31+. */
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit,
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        darkTheme -> DragonBallDarkScheme
        else -> DragonBallLightScheme
    }
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.background.copy(alpha = 0.94f).toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content,
    )
}
