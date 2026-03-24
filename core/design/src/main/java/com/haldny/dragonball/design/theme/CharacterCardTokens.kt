package com.haldny.dragonball.design.theme

/**
 * Non-[Dimens] tokens for character grid cards: motion, alpha, and text limits.
 */
object CharacterCardAlpha {
    /** Glass-style card fill over [androidx.compose.material3.ColorScheme.surfaceVariant]. */
    const val cardContainer: Float = 0.42f
    const val imageAreaBackground: Float = 0.4f
    const val shimmerBand: Float = 0.55f
    const val shimmerHighlight: Float = 0.15f
    const val progressTrack: Float = 0.4f
    const val errorBackground: Float = 0.85f
    const val errorIconTint: Float = 0.65f
}

object CharacterCardMotion {
    const val IMAGE_CROSSFADE_DURATION_MS: Int = 280
    const val SHIMMER_DURATION_MS: Int = 1100
}

object CharacterCardTypography {
    const val TITLE_LINE_HEIGHT_MULTIPLIER: Float = 1.05f
    const val TITLE_MAX_LINES: Int = 2
}

/**
 * Gradient geometry for character card shimmer (Brush coordinate space, not dp).
 */
object CharacterCardShimmer {
    const val phaseWidthPx: Float = 400f
    const val bandLengthPx: Float = 220f
    const val diagonalEndYPx: Float = 280f
}
