package com.haldny.dragonball.characters.domain

data class CharactersPage(
    val items: List<DragonBallCharacter>,
    val hasNextPage: Boolean,
)
