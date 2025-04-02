package dev.ranga.hpcharacters.data.model

import kotlinx.serialization.Serializable

@Serializable
data class HpCharacterDto(
    val id: String,
    val name: String,
    val actor: String?,
    val species: String,
    val house: String?,
    val alive: Boolean,
    val dateOfBirth: String?,
    val image: String?,
)