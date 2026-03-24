package com.haldny.dragonball.design.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.haldny.dragonball.design.theme.DragonBallComposeTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DragonBallScaffold(
    title: String,
    modifier: Modifier = Modifier,
    navigationIcon: @Composable () -> Unit = {},
    content: @Composable (PaddingValues) -> Unit,
) {
    val colorScheme = MaterialTheme.colorScheme
    Box(modifier = modifier.fillMaxSize()) {
        DragonBallAmbientBackground(modifier = Modifier.fillMaxSize())
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            containerColor = Color.Transparent,
            topBar = {
                TopAppBar(
                    modifier = Modifier.background(
                        Brush.horizontalGradient(
                            colors = listOf(
                                colorScheme.primary.copy(alpha = 0.55f),
                                colorScheme.secondary.copy(alpha = 0.35f),
                                colorScheme.tertiary.copy(alpha = 0.2f),
                            ),
                        ),
                    ),
                    title = {
                        Text(
                            text = title,
                            style = MaterialTheme.typography.titleLarge.copy(
                                fontWeight = FontWeight.Bold,
                                letterSpacing = MaterialTheme.typography.titleLarge.letterSpacing * 1.2f,
                            ),
                        )
                    },
                    navigationIcon = navigationIcon,
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = Color.Transparent,
                        scrolledContainerColor = colorScheme.surface.copy(alpha = 0.92f),
                        navigationIconContentColor = Color.White,
                        titleContentColor = Color.White,
                        actionIconContentColor = Color.White,
                    ),
                )
            },
            content = content,
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun DragonBallScaffoldPreview() {
    DragonBallComposeTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            DragonBallScaffold(title = "Preview") { padding ->
                Text("Content", modifier = Modifier.padding(padding))
            }
        }
    }
}
