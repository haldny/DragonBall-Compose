package com.haldny.dragonball.characters.view

/**
 * Layout and interaction constants for the characters list screen.
 */
object CharactersListLayout {
    const val GRID_COLUMN_COUNT: Int = 2

    /** Trigger [CharactersUserAction.LoadNextPage] when this many items from the list end are visible. */
    const val LOAD_MORE_THRESHOLD_ITEMS_FROM_END: Int = 2

    /** [kotlinx.coroutines.flow.MutableSharedFlow] buffer for one-shot navigation effects. */
    const val UI_EFFECT_BUFFER_CAPACITY: Int = 1
}
