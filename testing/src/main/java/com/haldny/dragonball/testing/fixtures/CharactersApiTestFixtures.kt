package com.haldny.dragonball.testing.fixtures

import com.haldny.dragonball.characters.data.model.Character
import com.haldny.dragonball.characters.data.model.CharactersResponse

object CharactersApiTestFixtures {

    val characterDto1: Character = Character(
        id = 1,
        name = "Name 1",
        ki = "Ki 1",
        maxKi = "Max Ki 1",
        image = "Image 1",
        description = "Description 1",
        gender = "Female",
        race = "Saiyan",
    )

    val characterDto2: Character = Character(
        id = 2,
        name = "Name 2",
        ki = "Ki 2",
        maxKi = "Max Ki 2",
        image = "Image 2",
        description = "Description 2",
        gender = "Male",
        race = "Namekian",
    )

    fun charactersResponseTwoItems(): CharactersResponse = CharactersResponse(
        characters = listOf(characterDto1, characterDto2),
    )

    fun charactersResponseEmpty(): CharactersResponse = CharactersResponse(
        characters = emptyList(),
    )
}
