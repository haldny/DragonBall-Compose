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
    @SerializedName("gender") var gender : String? = null,
    @SerializedName("description") var description : String? = null,
    @SerializedName("image") var image : String? = null,
    @SerializedName("affiliation") var affiliation : String? = null,
    @SerializedName("deletedAt") var deletedAt : String? = null
)
