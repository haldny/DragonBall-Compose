package com.haldny.dragonball.characters.domain

import com.haldny.dragonball.core.business.BusinessResult

interface CharactersRepository {
    suspend fun getCharactersPage(
        page: Int,
        limit: Int,
    ): BusinessResult<CharactersPage>
}
