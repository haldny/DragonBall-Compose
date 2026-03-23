package com.haldny.dragonball.design.components

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
    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                title = { Text(text = title, style = MaterialTheme.typography.titleLarge) },
                navigationIcon = navigationIcon,
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface,
                    titleContentColor = MaterialTheme.colorScheme.onSurface,
                )
            )
        },
        content = content
    )
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
