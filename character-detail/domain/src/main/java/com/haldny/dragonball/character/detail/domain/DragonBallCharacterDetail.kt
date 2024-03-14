package com.haldny.dragonball.character.detail.domain

data class DragonBallCharacterDetail(
    val id: Int,
    val name: String,
    val ki: String,
    val maxKi: String,
    val description: String,
    val image: String,
    val race: Race?,
    val gender: Gender?,
    val originPlanet: OriginPlanet?,
    val transformations: List<Transformation> = listOf()
)

enum class Race(val value: String) {
    SAIYAN("Saiyan"),
    NAMEKIAN("Namekian"),
    HUMAN("Human"),
    MAJIN("Majin"),
    FRIEZA_RACE("Frieza Race"),
    JIREN_RACE("Jiren Race"),
    ANDROID("Android"),
    GOD("God"),
    ANGEL("Angel"),
    EVIL("Evil"),
    UNKNOWN("Unknown"),
    NUCLEICO_BENIGNO("Nucleico benigno"),
    NUCLEICO("Nucleico");

    companion object {
        fun getByValue(value: String?): Race? = entries.firstOrNull { it.value == value }
    }
}

enum class Gender(val value: String) {
    MALE("Male"),
    FEMALE("Female"),
    OTHER("Other"),
    UNKNOWN("Unknown");

    companion object {
        fun getByValue(value: String?): Gender? = entries.firstOrNull { it.value == value }
    }
}

data class OriginPlanet (
    val id: Int,
    val name: String,
    val isDestroyed: Boolean,
    val description: String,
    val image: String,
)

data class Transformation (
    val id: Int,
    val name: String,
    val image: String,
    val ki: String,
)
