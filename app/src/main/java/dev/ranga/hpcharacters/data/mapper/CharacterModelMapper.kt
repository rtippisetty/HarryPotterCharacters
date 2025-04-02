package dev.ranga.hpcharacters.data.mapper

import dev.ranga.hpcharacters.data.local.HpCharacterEntity
import dev.ranga.hpcharacters.data.model.HpCharacterDto
import dev.ranga.hpcharacters.domain.model.HpCharacter
import javax.inject.Inject

class CharacterModelMapper @Inject constructor() {

    fun mapDtoToEntity(hpCharacterDto: HpCharacterDto): HpCharacterEntity {
        return HpCharacterEntity(
            id = hpCharacterDto.id,
            name = hpCharacterDto.name,
            actor = hpCharacterDto.actor ?: "",
            species = hpCharacterDto.species,
            house = hpCharacterDto.house ?: "",
            alive = hpCharacterDto.alive,
            dateOfBirth = hpCharacterDto.dateOfBirth ?: "",
            image = hpCharacterDto.image ?: ""
        )
    }

    fun mapEntityToDomain(characterEntity: HpCharacterEntity): HpCharacter {
        return HpCharacter(
            id = characterEntity.id,
            name = characterEntity.name,
            actor = characterEntity.actor,
            species = characterEntity.species,
            house = characterEntity.house,
            alive = characterEntity.alive,
            dateOfBirth = characterEntity.dateOfBirth,
            image = characterEntity.image
        )
    }
}