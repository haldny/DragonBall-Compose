package com.haldny.dragonball.testing.fixtures

import com.haldny.dragonball.character.detail.data.model.CharacterDetailResponse

object CharacterDetailApiTestFixtures {

    val characterDetailSuccess: CharacterDetailResponse = CharacterDetailResponse(
        id = 1,
        name = "Name 1",
        ki = "Ki 1",
        maxKi = "Max Ki 1",
        image = "Image 1",
        description = "Description 1",
        gender = "Female",
        race = "Saiyan",
    )
}
