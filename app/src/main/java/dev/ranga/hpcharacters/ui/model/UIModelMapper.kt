package dev.ranga.hpcharacters.ui.model

import androidx.compose.ui.graphics.Color
import dev.ranga.hpcharacters.domain.model.HpCharacter
import dev.ranga.hpcharacters.ui.common.formatDateToddMMMyy
import dev.ranga.hpcharacters.ui.theme.GryffindorHouseColor
import dev.ranga.hpcharacters.ui.theme.HufflepuffColor
import dev.ranga.hpcharacters.ui.theme.RavenclawHouseColor
import dev.ranga.hpcharacters.ui.theme.SlytherinHouseColor
import javax.inject.Inject

class UIModelMapper @Inject constructor() {

    fun mapToUiCharacter(hpCharacter: HpCharacter): UICharacter {
        return UICharacter(
            id = hpCharacter.id,
            name = hpCharacter.name,
            actor = hpCharacter.actor,
            species = hpCharacter.species,
            house = hpCharacter.house,
            houseColor = getHouseColor(hpCharacter.house)
        )
    }

    fun mapToUiCharacterDetail(hpCharacter: HpCharacter): UICharacterDetail {
        return UICharacterDetail(
            id = hpCharacter.id,
            name = hpCharacter.name,
            actor = hpCharacter.actor,
            species = hpCharacter.species,
            house = hpCharacter.house,
            houseColor = getHouseColor(hpCharacter.house),
            alive = hpCharacter.alive,
            dateOfBirth = hpCharacter.dateOfBirth.formatDateToddMMMyy(),
            image = hpCharacter.image
        )
    }

    private fun getHouseColor(house: String): Color {
        return when (house.lowercase()) {
            "gryffindor" -> return GryffindorHouseColor
            "slytherin" -> return SlytherinHouseColor
            "ravenclaw" -> return RavenclawHouseColor
            "hufflepuff" -> return HufflepuffColor
            else -> Color.Gray
        }
    }
}