package com.haldny.dragonball.characters.view

sealed interface CharactersUserAction {
    data object Refresh : CharactersUserAction
    data object Retry : CharactersUserAction
    data object LoadNextPage : CharactersUserAction
    data class OpenCharacter(val id: Int) : CharactersUserAction
}
