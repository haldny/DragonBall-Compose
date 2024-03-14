package com.haldny.dragonball.character.detail.data.api

import com.haldny.dragonball.character.detail.data.model.CharacterDetailResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface CharacterDetailApi {

    @GET("characters/{id}")
    suspend fun getCharacterDetail(
        @Path("id") id: Int
    ): Response<CharacterDetailResponse>
}