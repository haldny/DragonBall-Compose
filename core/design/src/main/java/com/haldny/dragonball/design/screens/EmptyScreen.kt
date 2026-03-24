package com.haldny.dragonball.design.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.haldny.dragonball.design.R
import com.haldny.dragonball.design.theme.Dimens
import com.haldny.dragonball.design.theme.DragonBallComposeTheme

@Composable
fun EmptyScreen(
    modifier: Modifier = Modifier,
    onButtonClick: () -> Unit,
) {
    val scheme = MaterialTheme.colorScheme
    val context = LocalContext.current

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(Dimens.screenPadding),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        OutlinedCard(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.outlinedCardColors(
                containerColor = scheme.surfaceVariant.copy(alpha = 0.48f),
            ),
            border = BorderStroke(
                width = Dimens.characterCardBorderWidth,
                brush = Brush.linearGradient(
                    colors = listOf(
                        scheme.primary.copy(alpha = 0.75f),
                        scheme.secondary.copy(alpha = 0.4f),
                    ),
                ),
            ),
        ) {
            Column(
                modifier = Modifier.padding(Dimens.stateScreenCardPadding),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text(
                    text = context.getString(R.string.empty_characters_message),
                    style = MaterialTheme.typography.bodyLarge,
                    color = scheme.onSurface,
                    textAlign = TextAlign.Center,
                )
                Spacer(modifier = Modifier.height(Dimens.screenPadding))
                FilledTonalButton(
                    onClick = onButtonClick,
                    colors = ButtonDefaults.filledTonalButtonColors(
                        containerColor = scheme.primaryContainer.copy(alpha = 0.9f),
                        contentColor = scheme.onPrimaryContainer,
                    ),
                ) {
                    Text(text = context.getString(R.string.button_refresh_text))
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
internal fun EmptyPreview() {
    DragonBallComposeTheme {
        EmptyScreen(onButtonClick = {})
    }
}
