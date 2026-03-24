package com.haldny.dragonball.design.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.tooling.preview.Preview
import com.haldny.dragonball.design.theme.Dimens
import com.haldny.dragonball.design.theme.DragonBallComposeTheme

@Composable
fun LoadingScreen(modifier: Modifier = Modifier) {
    val scheme = MaterialTheme.colorScheme
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        scheme.surface.copy(alpha = 0.25f),
                        scheme.background,
                        scheme.primary.copy(alpha = 0.08f),
                    ),
                ),
            ),
        contentAlignment = Alignment.Center,
    ) {
        CircularProgressIndicator(
            color = scheme.primary,
            trackColor = scheme.surfaceVariant.copy(alpha = 0.55f),
            strokeWidth = Dimens.loadingIndicatorStrokeWidth,
        )
    }
}

@Preview(showBackground = true)
@Composable
internal fun LoadingPreview() {
    DragonBallComposeTheme {
        LoadingScreen()
    }
}
