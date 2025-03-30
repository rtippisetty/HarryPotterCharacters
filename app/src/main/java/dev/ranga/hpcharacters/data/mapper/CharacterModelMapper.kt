package dev.ranga.hpcharacters.data.mapper

import dev.ranga.hpcharacters.data.local.HpCharacterEntity
import dev.ranga.hpcharacters.data.model.CharacterDto
import dev.ranga.hpcharacters.domain.model.HpCharacter
import javax.inject.Inject

class CharacterModelMapper @Inject constructor() {

    fun mapDtoToEntity(characterDto: CharacterDto): HpCharacterEntity {
        return HpCharacterEntity(
            id = characterDto.id,
            name = characterDto.name,
            actor = characterDto.actor ?: "",
            species = characterDto.species,
            house = characterDto.house ?: "",
            alive = characterDto.alive,
            dateOfBirth = characterDto.dateOfBirth ?: "",
            image = characterDto.image ?: ""
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