package com.haldny.dragonball.core.navigation

import androidx.navigation.NavController

const val CHARACTER_DETAIL_SCREEN_ROUTE = "character-detail-screen"
const val CHARACTERS_SCREEN_ROUTE = "characters-screen"

fun NavController.openCharacterDetails(id: Int) {
    currentBackStackEntry?.savedStateHandle?.apply {
        set("id", id)
    }

    navigate(route = CHARACTER_DETAIL_SCREEN_ROUTE)
}
