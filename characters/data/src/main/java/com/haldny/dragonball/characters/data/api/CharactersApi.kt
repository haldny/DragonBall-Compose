package com.haldny.dragonball.characters.data.api

import com.haldny.dragonball.characters.data.model.CharactersResponse
import com.haldny.dragonball.characters.domain.CharactersPagingConfig
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface CharactersApi {

    @GET("characters")
    suspend fun getCharacters(
        @Query("page") page: Int = CharactersPagingConfig.FIRST_PAGE,
        @Query("limit") limit: Int = CharactersPagingConfig.PAGE_LIMIT,
    ): Response<CharactersResponse>

}
