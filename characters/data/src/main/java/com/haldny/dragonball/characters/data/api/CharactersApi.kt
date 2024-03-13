package com.haldny.dragonball.characters.data.api

import com.haldny.dragonball.characters.data.model.CharactersResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface CharactersApi {

    @GET("characters")
    suspend fun getCharacters(
        @Query("page") page: Int = 1,
        @Query("limit") limit: Int = 58,
    ): Response<CharactersResponse>

}
