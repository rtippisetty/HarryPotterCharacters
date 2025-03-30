package dev.ranga.hpcharacters.domain.model

data class HpCharacter(
    val id: String,
    val name: String,
    val actor: String,
    val species: String,
    val house: String,
    val alive: Boolean,
    val dateOfBirth: String,
    val image: String,
)
