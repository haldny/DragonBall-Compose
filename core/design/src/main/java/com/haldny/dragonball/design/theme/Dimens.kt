package com.haldny.dragonball.design.theme

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

object Dimens {
    val screenPadding: Dp = 16.dp
    val gridContentPadding: Dp = 12.dp
    val characterCardHeight: Dp = 304.dp
    val characterImageHeight: Dp = 180.dp
    val characterCardCornerRadius: Dp = 16.dp
    val characterCardBorderWidth: Dp = 1.5.dp
    val characterNamePaddingHorizontal: Dp = 14.dp
    val characterNamePaddingVertical: Dp = 12.dp
    val sectionSpacingLarge: Dp = 32.dp
    val characterCardElevation: Dp = 6.dp
    val characterCardLoadingIndicatorSize: Dp = 32.dp
    val characterCardLoadingIndicatorStrokeWidth: Dp = 2.5.dp
    val characterCardErrorIconSize: Dp = 40.dp
    val listAppendLoadingPadding: Dp = screenPadding

    /** Detail screen: fixed width for label column so values align after ": " (short labels end-aligned). */
    val characterDetailLabelColumnWidth: Dp = 140.dp

    /** Detail screen carousel: space under tab row before pager cards. */
    val characterDetailCarouselTabSpacing: Dp = 8.dp
    val characterDetailCarouselCardElevation: Dp = 6.dp
    val stateScreenCardPadding: Dp = 24.dp
    val loadingIndicatorStrokeWidth: Dp = 3.dp
}

/**
 * Typography tokens (sp). Used by [Typography] in [Type.kt].
 */
object TextDimens {
    val bodyLargeFontSize = 16.sp
    val bodyLargeLineHeight = 24.sp
    val bodyLargeLetterSpacing = 0.5.sp
}
