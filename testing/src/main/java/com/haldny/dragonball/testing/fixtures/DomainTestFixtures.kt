package com.haldny.dragonball.testing.fixtures

import com.haldny.dragonball.character.detail.domain.DragonBallCharacterDetail
import com.haldny.dragonball.character.detail.domain.Gender as DetailGender
import com.haldny.dragonball.character.detail.domain.OriginPlanet
import com.haldny.dragonball.character.detail.domain.Race as DetailRace
import com.haldny.dragonball.characters.domain.DragonBallCharacter
import com.haldny.dragonball.characters.domain.Gender
import com.haldny.dragonball.characters.domain.Race

object DomainTestFixtures {

    val dragonBallCharacter1: DragonBallCharacter = DragonBallCharacter(
        id = 1,
        name = "Name 1",
        ki = "Ki 1",
        maxKi = "Max Ki 1",
        image = "Image 1",
        description = "Description 1",
        gender = Gender.FEMALE,
        race = Race.SAIYAN,
    )

    val dragonBallCharacter2: DragonBallCharacter = DragonBallCharacter(
        id = 2,
        name = "Name 2",
        ki = "Ki 2",
        maxKi = "Max Ki 2",
        image = "Image 2",
        description = "Description 2",
        gender = Gender.MALE,
        race = Race.NAMEKIAN,
    )

    /** Compose UI sample with remote image URL (instrumented tests declare INTERNET). */
    val gokuListCharacter: DragonBallCharacter = DragonBallCharacter(
        id = 1,
        name = "Goku",
        ki = "0",
        maxKi = "0",
        image = "https://example.com/goku.png",
        description = "",
        gender = Gender.MALE,
        race = Race.SAIYAN,
    )

    val gokuCharacterDetail: DragonBallCharacterDetail = DragonBallCharacterDetail(
        id = 1,
        name = "Goku",
        ki = "1,000",
        maxKi = "9,000",
        image = "https://example.com/goku.png",
        description = "Saiyan warrior",
        gender = DetailGender.MALE,
        race = DetailRace.SAIYAN,
        originPlanet = OriginPlanet(
            id = 2,
            name = "Earth",
            isDestroyed = false,
            description = "Home planet",
            image = "https://example.com/earth.png",
        ),
        transformations = emptyList(),
    )

    val characterDetailMinimalMapped: DragonBallCharacterDetail = DragonBallCharacterDetail(
        id = 1,
        name = "Name 1",
        ki = "Ki 1",
        maxKi = "Max Ki 1",
        image = "Image 1",
        description = "Description 1",
        gender = DetailGender.FEMALE,
        race = DetailRace.SAIYAN,
        originPlanet = OriginPlanet(
            id = 0,
            name = "",
            isDestroyed = false,
            description = "",
            image = "",
        ),
        transformations = emptyList(),
    )

    val characterDetailWithPlanet: DragonBallCharacterDetail = DragonBallCharacterDetail(
        id = 1,
        name = "Name 1",
        ki = "Ki 1",
        maxKi = "Max Ki 1",
        image = "Image 1",
        description = "Description 1",
        gender = DetailGender.FEMALE,
        race = DetailRace.SAIYAN,
        originPlanet = OriginPlanet(
            id = 1,
            name = "Planet Name",
            isDestroyed = false,
            description = "Description",
            image = "Image",
        ),
        transformations = emptyList(),
    )
}
