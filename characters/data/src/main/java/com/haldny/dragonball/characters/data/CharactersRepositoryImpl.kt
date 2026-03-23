package com.haldny.dragonball.characters.data

import com.haldny.dragonball.characters.data.api.CharactersApi
import com.haldny.dragonball.characters.data.mapper.toBusinessModel
import com.haldny.dragonball.characters.domain.CharactersPage
import com.haldny.dragonball.characters.domain.CharactersRepository
import com.haldny.dragonball.core.business.BusinessResult
import com.haldny.dragonball.network.extensions.handleResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class CharactersRepositoryImpl @Inject constructor(
    private val charactersApi: CharactersApi
) : CharactersRepository {
    override suspend fun getCharactersPage(
        page: Int,
        limit: Int,
    ): BusinessResult<CharactersPage> = withContext(Dispatchers.IO) {
        charactersApi.getCharacters(page = page, limit = limit).handleResponse { response ->
            val items = response.characters.map { it.toBusinessModel() }
            val hasNextPage = items.size >= limit
            CharactersPage(items = items, hasNextPage = hasNextPage)
        }
    }
}
