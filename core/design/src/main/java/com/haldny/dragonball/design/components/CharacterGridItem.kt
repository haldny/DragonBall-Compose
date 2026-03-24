package com.haldny.dragonball.design.components

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PersonOff
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.material3.ColorScheme
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import coil.compose.SubcomposeAsyncImage
import coil.compose.SubcomposeAsyncImageContent
import coil.request.ImageRequest
import com.haldny.dragonball.design.theme.CharacterCardAlpha
import com.haldny.dragonball.design.theme.CharacterCardMotion
import com.haldny.dragonball.design.theme.CharacterCardShimmer
import com.haldny.dragonball.design.theme.CharacterCardTypography
import com.haldny.dragonball.design.theme.Dimens

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CharacterGridItem(
    name: String,
    imageUrl: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val cardShape = RoundedCornerShape(Dimens.characterCardCornerRadius)
    val scheme = MaterialTheme.colorScheme
    Card(
        modifier = modifier
            .fillMaxWidth()
            .height(Dimens.characterCardHeight)
            .padding(Dimens.gridContentPadding),
        onClick = onClick,
        shape = cardShape,
        border = BorderStroke(
            width = Dimens.characterCardBorderWidth,
            brush = Brush.linearGradient(
                colors = listOf(
                    scheme.primary.copy(alpha = 0.92f),
                    scheme.secondary.copy(alpha = 0.55f),
                    scheme.tertiary.copy(alpha = 0.38f),
                ),
            ),
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = Dimens.characterCardElevation),
        colors = CardDefaults.cardColors(
            containerColor = scheme.surfaceVariant.copy(alpha = CharacterCardAlpha.cardContainer),
        ),
    ) {
        CharacterGridItemCardContent(
            name = name,
            imageUrl = imageUrl,
            cardShape = cardShape,
            scheme = scheme,
        )
    }
}

@Composable
private fun CharacterGridItemCardContent(
    name: String,
    imageUrl: String,
    cardShape: Shape,
    scheme: ColorScheme,
) {
    val context = LocalContext.current
    Column(
        modifier = Modifier
            .fillMaxSize()
            .clip(cardShape),
    ) {
        // Fit keeps the full image visible; Crop was clipping heads/feet on portrait art.
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .background(
                    scheme.surfaceVariant.copy(alpha = CharacterCardAlpha.imageAreaBackground),
                ),
        ) {
            SubcomposeAsyncImage(
                model = ImageRequest.Builder(context)
                    .data(imageUrl)
                    .crossfade(CharacterCardMotion.IMAGE_CROSSFADE_DURATION_MS)
                    .build(),
                contentDescription = name,
                contentScale = ContentScale.Fit,
                modifier = Modifier.fillMaxSize(),
                loading = {
                    CharacterCardImageLoading()
                },
                success = {
                    SubcomposeAsyncImageContent()
                },
                error = {
                    CharacterCardImageError()
                },
            )
        }
        Text(
            text = name,
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    Brush.horizontalGradient(
                        colors = listOf(
                            scheme.primary.copy(alpha = 0.52f),
                            scheme.secondary.copy(alpha = 0.28f),
                        ),
                    ),
                )
                .padding(
                    horizontal = Dimens.characterNamePaddingHorizontal,
                    vertical = Dimens.characterNamePaddingVertical,
                ),
            style = MaterialTheme.typography.titleMedium.copy(
                fontWeight = FontWeight.SemiBold,
                lineHeight = MaterialTheme.typography.titleMedium.lineHeight *
                    CharacterCardTypography.TITLE_LINE_HEIGHT_MULTIPLIER,
            ),
            color = Color.White,
            textAlign = TextAlign.Center,
            maxLines = CharacterCardTypography.TITLE_MAX_LINES,
            overflow = TextOverflow.Ellipsis,
        )
    }
}

@Composable
private fun CharacterCardImageLoading(modifier: Modifier = Modifier) {
    val transition = rememberInfiniteTransition(label = "cardImageShimmer")
    val phase by transition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(CharacterCardMotion.SHIMMER_DURATION_MS, easing = LinearEasing),
            repeatMode = RepeatMode.Restart,
        ),
        label = "shimmerPhase",
    )
    val base = MaterialTheme.colorScheme.surfaceVariant
    val highlight = MaterialTheme.colorScheme.primary.copy(alpha = CharacterCardAlpha.shimmerHighlight)
    val brush = Brush.linearGradient(
        colors = listOf(
            base.copy(alpha = CharacterCardAlpha.shimmerBand),
            highlight,
            base.copy(alpha = CharacterCardAlpha.shimmerBand),
        ),
        start = Offset(phase * CharacterCardShimmer.phaseWidthPx, 0f),
        end = Offset(
            phase * CharacterCardShimmer.phaseWidthPx + CharacterCardShimmer.bandLengthPx,
            CharacterCardShimmer.diagonalEndYPx,
        ),
    )
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(brush),
        contentAlignment = Alignment.Center,
    ) {
        CircularProgressIndicator(
            modifier = Modifier.size(Dimens.characterCardLoadingIndicatorSize),
            strokeWidth = Dimens.characterCardLoadingIndicatorStrokeWidth,
            color = MaterialTheme.colorScheme.primary,
            trackColor = MaterialTheme.colorScheme.surface.copy(alpha = CharacterCardAlpha.progressTrack),
        )
    }
}

@Composable
private fun CharacterCardImageError(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(
                MaterialTheme.colorScheme.surfaceVariant.copy(alpha = CharacterCardAlpha.errorBackground),
            ),
        contentAlignment = Alignment.Center,
    ) {
        Icon(
            imageVector = Icons.Filled.PersonOff,
            contentDescription = null,
            modifier = Modifier.size(Dimens.characterCardErrorIconSize),
            tint = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = CharacterCardAlpha.errorIconTint),
        )
    }
}
