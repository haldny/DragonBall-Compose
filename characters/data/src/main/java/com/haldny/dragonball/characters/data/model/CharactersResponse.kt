package com.haldny.dragonball.characters.data.model

import com.google.gson.annotations.SerializedName

data class CharactersResponse (
    @SerializedName("items" ) val characters : List<Character> = listOf(),
)

data class Character (
    @SerializedName("id") val id : Int? = null,
    @SerializedName("name") val name : String? = null,
    @SerializedName("ki") var ki : String? = null,
    @SerializedName("maxKi") val maxKi : String? = null,
    @SerializedName("race") val race : String? = null,
    @SerializedName("gender") val gender : String? = null,
    @SerializedName("description") val description : String? = null,
    @SerializedName("image") val image : String? = null,
    @SerializedName("affiliation") val affiliation : String? = null,
    @SerializedName("deletedAt") val deletedAt : String? = null
)
