package dev.ranga.hpcharacters.ui.model

import androidx.compose.ui.graphics.Color

data class UICharacter(
    val id: String,
    val name: String,
    val actor: String,
    val species: String,
    val house: String,
    val houseColor: Color,
)