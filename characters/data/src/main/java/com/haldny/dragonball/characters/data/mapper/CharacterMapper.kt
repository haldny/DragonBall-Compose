package com.haldny.dragonball.characters.data.mapper

import com.haldny.dragonball.characters.data.model.Character
import com.haldny.dragonball.characters.domain.DragonBallCharacter
import com.haldny.dragonball.characters.domain.Gender
import com.haldny.dragonball.characters.domain.Race

internal fun Character.toBusinessModel() = DragonBallCharacter(
    id = id ?: 0,
    name = name.orEmpty(),
    ki = ki.orEmpty(),
    maxKi = maxKi.orEmpty(),
    description = description.orEmpty(),
    image = image.orEmpty(),
    race = Race.getByValue(race),
    gender = Gender.getByValue(gender),
)
