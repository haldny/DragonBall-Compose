package com.haldny.dragonball.design.theme

import androidx.compose.ui.graphics.Color

/**
 * Dragon Ball–inspired palette: deep space UI, ki energy accents, capsule-tech panels.
 */
object DragonBallColors {
    val spaceBlack = Color(0xFF02060F)
    val spaceNavy = Color(0xFF0A1424)
    val spaceDeep = Color(0xFF060D18)
    val panel = Color(0xFF121C30)
    val panelElevated = Color(0xFF1A2740)

    val kiGold = Color(0xFFFFC400)
    val kiFlame = Color(0xFFFF6D00)
    val auraCyan = Color(0xFF00E5FF)
    val plasmaViolet = Color(0xFFD500F9)

    val textPrimary = Color(0xFFE8F1FF)
    val textMuted = Color(0xFF8FA8C8)
    val gridLine = Color(0xFF1E3A5F)
}

// Legacy names kept for any external references; map to new palette.
val Purple80 = DragonBallColors.plasmaViolet.copy(alpha = 0.85f)
val PurpleGrey80 = DragonBallColors.textMuted
val Pink80 = DragonBallColors.kiFlame.copy(alpha = 0.7f)
val Purple40 = DragonBallColors.panelElevated
val PurpleGrey40 = DragonBallColors.textMuted
val Pink40 = DragonBallColors.kiGold.copy(alpha = 0.85f)
