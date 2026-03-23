package com.haldny.dragonball.design.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.haldny.dragonball.design.theme.Dimens

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CharacterGridItem(
    name: String,
    imageUrl: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .height(Dimens.characterCardHeight)
            .padding(Dimens.gridContentPadding),
        onClick = onClick,
    ) {
        Column {
            AsyncImage(
                modifier = Modifier
                    .height(Dimens.characterImageHeight)
                    .fillMaxWidth(),
                model = imageUrl,
                contentDescription = name,
                contentScale = ContentScale.Fit,
            )
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center,
            ) {
                Text(
                    text = name,
                    textAlign = TextAlign.Center,
                    fontSize = 22.sp,
                    maxLines = 1,
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }
    }
}
