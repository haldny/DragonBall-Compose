package com.haldny.dragonball.character.detail.data

import com.haldny.dragonball.character.detail.data.api.CharacterDetailApi
import com.haldny.dragonball.character.detail.data.mapper.toBusinessModel
import com.haldny.dragonball.character.detail.domain.CharacterDetailRepository
import com.haldny.dragonball.character.detail.domain.DragonBallCharacterDetail
import com.haldny.dragonball.core.business.BusinessResult
import com.haldny.dragonball.network.extensions.handleResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class CharacterDetailRepositoryImpl @Inject constructor(
    private val characterDetailApi: CharacterDetailApi
) : CharacterDetailRepository {

    override suspend fun getCharacterDetail(id: Int): BusinessResult<DragonBallCharacterDetail> =
        withContext(Dispatchers.IO) {
            characterDetailApi.getCharacterDetail(id).handleResponse { response ->
                response.toBusinessModel()
            }
        }
}
