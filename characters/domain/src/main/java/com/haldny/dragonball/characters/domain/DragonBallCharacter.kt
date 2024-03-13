package com.haldny.dragonball.characters.domain

data class DragonBallCharacter(
    val id: Int,
    val name: String,
    val ki: String,
    val maxKi: String,
    val description: String,
    val image: String,
    val race: Race?,
    val gender: Gender?,
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
