package com.haldny.dragonball.character.detail.data.mapper

import com.haldny.dragonball.character.detail.data.model.CharacterDetailResponse
import com.haldny.dragonball.character.detail.data.model.OriginPlanet
import com.haldny.dragonball.character.detail.data.model.Transformation
import com.haldny.dragonball.character.detail.domain.OriginPlanet as DomainOriginPlanet
import com.haldny.dragonball.character.detail.domain.Transformation as DomainTransformation
import com.haldny.dragonball.character.detail.domain.DragonBallCharacterDetail
import com.haldny.dragonball.character.detail.domain.Gender
import com.haldny.dragonball.character.detail.domain.Race

fun CharacterDetailResponse.toBusinessModel() = DragonBallCharacterDetail(
    id = id ?: 0,
    name = name.orEmpty(),
    ki = ki.orEmpty(),
    maxKi = maxKi.orEmpty(),
    description = description.orEmpty(),
    image = image.orEmpty(),
    race = Race.getByValue(race),
    gender = Gender.getByValue(gender),
    originPlanet = originPlanet?.toBusinessModel(),
    transformations = transformations.map { it.toBusinessModel() }
)

fun OriginPlanet.toBusinessModel() = DomainOriginPlanet(
    id = id ?: 0,
    name = name.orEmpty(),
    description = description.orEmpty(),
    image = image.orEmpty(),
    isDestroyed = isDestroyed ?: false,
)

fun Transformation.toBusinessModel() = DomainTransformation(
    id = id ?: 0,
    name = name.orEmpty(),
    ki = ki.orEmpty(),
    image = image.orEmpty(),
)
