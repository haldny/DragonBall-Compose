package com.haldny.dragonball.characters.view

import com.haldny.dragonball.characters.domain.DragonBallCharacter
import kotlinx.collections.immutable.ImmutableList

data class CharactersListContent(
    val characters: ImmutableList<DragonBallCharacter>,
    val hasNextPage: Boolean,
    val isAppending: Boolean,
    val nextPageToLoad: Int,
)

sealed interface CharactersUiState {
    data object InitialLoading : CharactersUiState
    data object Error : CharactersUiState
    data object Empty : CharactersUiState
    data class Loaded(val content: CharactersListContent) : CharactersUiState
}
