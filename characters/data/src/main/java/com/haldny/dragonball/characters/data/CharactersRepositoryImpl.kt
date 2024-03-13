package com.haldny.dragonball.characters.data

import com.haldny.dragonball.characters.data.api.CharactersApi
import com.haldny.dragonball.characters.data.mapper.toBusinessModel
import com.haldny.dragonball.characters.domain.CharactersRepository
import com.haldny.dragonball.characters.domain.DragonBallCharacter
import com.haldny.dragonball.core.business.BusinessResult
import com.haldny.dragonball.network.extensions.handleResponse
import javax.inject.Inject

class CharactersRepositoryImpl @Inject constructor(
    private val charactersApi: CharactersApi
) : CharactersRepository {
    override suspend fun getCharacters(): BusinessResult<List<DragonBallCharacter>> {
        return charactersApi.getCharacters().handleResponse { response ->
            response.characters.map { character ->
                character.toBusinessModel()
            }
        }
    }
}
