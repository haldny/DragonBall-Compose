package com.haldny.dragonball.characters.view

sealed interface CharactersUiEffect {
    data class NavigateToDetail(val id: Int) : CharactersUiEffect
}
