package dev.ranga.hpcharacters.data.local

import androidx.room.Entity

@Entity(tableName = "hp_characters", primaryKeys = ["id"])
data class HpCharacterEntity(
    val id: String,
    val name: String,
    val actor: String,
    val species: String,
    val house: String,
    val alive: Boolean,
    val dateOfBirth: String,
    val image: String,
)
