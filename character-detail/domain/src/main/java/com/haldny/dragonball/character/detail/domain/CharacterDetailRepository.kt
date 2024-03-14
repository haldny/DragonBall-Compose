package com.haldny.dragonball.character.detail.domain

import com.haldny.dragonball.core.business.BusinessResult

interface CharacterDetailRepository {
    suspend fun getCharacterDetail(id: Int): BusinessResult<DragonBallCharacterDetail>
}
