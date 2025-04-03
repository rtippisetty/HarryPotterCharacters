package dev.ranga.hpcharacters.testFixtures

import dev.ranga.hpcharacters.data.local.HpCharacterEntity
import dev.ranga.hpcharacters.data.model.HpCharacterDto
import dev.ranga.hpcharacters.domain.model.HpCharacter
import dev.ranga.hpcharacters.ui.model.UICharacter
import dev.ranga.hpcharacters.ui.model.UICharacterDetail
import dev.ranga.hpcharacters.ui.theme.GryffindorHouseColor

object Fixtures {
    val hpCharacterUI1 = UICharacter(
        id = "1",
        name = "Harry Potter",
        actor = "Daniel Radcliffe",
        species = "Human",
        house = "Gryffindor",
        houseColor = GryffindorHouseColor
    )

    val hpCharacterDetailUI1 = UICharacterDetail(
        id = "1",
        name = "Harry Potter",
        actor = "Daniel Radcliffe",
        species = "Human",
        house = "Gryffindor",
        houseColor = GryffindorHouseColor,
        alive = true,
        dateOfBirth = "25-12-2023",
        image = "image_url",
    )

    val hpCharacter1 = HpCharacter(
        id = "1",
        name = "Harry Potter",
        actor = "Daniel Radcliffe",
        species = "Human",
        house = "Gryffindor",
        alive = true,
        dateOfBirth = "25-12-2023",
        image = "image_url",
    )

    val hpCharacter2 = HpCharacter(
        id = "2",
        name = "Hermione Granger",
        actor = "Emma Watson",
        species = "Human",
        house = "Gryffindor",
        alive = true,
        dateOfBirth = "25-12-2023",
        image = "image_url",
    )

    val hpCharacters = listOf(hpCharacter1, hpCharacter2)

    val hpCharacterEntity1 = HpCharacterEntity(
        id = "1",
        name = "Harry Potter",
        actor = "Daniel Radcliffe",
        species = "Human",
        house = "Gryffindor",
        alive = true,
        dateOfBirth = "25-12-2023",
        image = "image_url",
    )

    val hpCharacterEntity2 = HpCharacterEntity(
        id = "2",
        name = "Hermione Granger",
        actor = "Emma Watson",
        species = "Human",
        house = "Gryffindor",
        alive = true,
        dateOfBirth = "25-12-2023",
        image = "image_url",
    )

    val hpCharacterEntities = listOf(hpCharacterEntity1, hpCharacterEntity2)

    val hpCharacterDto1 = HpCharacterDto(
        id = "1",
        name = "Harry Potter",
        actor = "Daniel Radcliffe",
        species = "Human",
        house = "Gryffindor",
        alive = true,
        dateOfBirth = "25-12-2023",
        image = "image_url",
    )

    val hpCharacterDto2 = HpCharacterDto(
        id = "2",
        name = "Hermione Granger",
        actor = "Emma Watson",
        species = "Human",
        house = "Gryffindor",
        alive = true,
        dateOfBirth = "25-12-2023",
        image = "image_url",
    )

    val hpCharacterDtos = listOf(hpCharacterDto1, hpCharacterDto2)
}