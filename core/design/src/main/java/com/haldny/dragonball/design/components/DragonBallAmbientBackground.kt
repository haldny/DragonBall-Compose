package com.haldny.dragonball.design.components

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import com.haldny.dragonball.design.theme.DragonBallColors

/**
 * Deep-space vertical gradient with a slow radial “ki” pulse for a sci-fi capsule look.
 */
@Composable
fun DragonBallAmbientBackground(modifier: Modifier = Modifier) {
    val pulse = rememberInfiniteTransition(label = "ambientKiPulse")
    val pulseAlpha by pulse.animateFloat(
        initialValue = 0.06f,
        targetValue = 0.14f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 4500, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse,
        ),
        label = "pulseAlpha",
    )

    BoxWithConstraints(modifier = modifier.fillMaxSize()) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            DragonBallColors.spaceNavy,
                            DragonBallColors.spaceDeep,
                            DragonBallColors.spaceBlack,
                        ),
                    ),
                ),
        )
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.radialGradient(
                        colors = listOf(
                            DragonBallColors.kiGold.copy(alpha = pulseAlpha),
                            DragonBallColors.auraCyan.copy(alpha = pulseAlpha * 0.45f),
                            Color.Transparent,
                        ),
                        center = Offset(constraints.maxWidth * 0.5f, constraints.maxHeight * 0.08f),
                        radius = constraints.maxWidth * 0.95f,
                    ),
                ),
        )
    }
}
