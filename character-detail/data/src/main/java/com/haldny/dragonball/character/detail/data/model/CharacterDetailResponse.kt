package com.haldny.dragonball.character.detail.data.model

import com.google.gson.annotations.SerializedName

data class CharacterDetailResponse (
    @SerializedName("id") val id: Int? = null,
    @SerializedName("name") val name: String? = null,
    @SerializedName("ki") val ki: String? = null,
    @SerializedName("maxKi") val maxKi: String? = null,
    @SerializedName("race") val race: String? = null,
    @SerializedName("gender") val gender: String? = null,
    @SerializedName("description") val description: String? = null,
    @SerializedName("image") val image: String? = null,
    @SerializedName("affiliation") val affiliation: String? = null,
    @SerializedName("deletedAt") val deletedAt: String? = null,
    @SerializedName("originPlanet") val originPlanet: OriginPlanet? = OriginPlanet(),
    @SerializedName("transformations") val transformations: List<Transformation> = listOf()
)

data class OriginPlanet (
    @SerializedName("id") val id: Int? = null,
    @SerializedName("name") val name: String? = null,
    @SerializedName("isDestroyed") val isDestroyed: Boolean? = null,
    @SerializedName("description") val description: String?  = null,
    @SerializedName("image") val image: String?  = null,
    @SerializedName("deletedAt") val deletedAt: String?  = null
)

data class Transformation (
    @SerializedName("id") val id: Int? = null,
    @SerializedName("name") val name: String? = null,
    @SerializedName("image") val image: String? = null,
    @SerializedName("ki") val ki: String? = null,
    @SerializedName("deletedAt") val deletedAt : String? = null
)
