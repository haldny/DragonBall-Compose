package com.haldny.dragonball.core.navigation

import androidx.navigation3.runtime.NavKey

sealed interface AppDestination : NavKey {
    data object CharactersList : AppDestination
    data class CharacterDetail(val id: Int) : AppDestination
}
