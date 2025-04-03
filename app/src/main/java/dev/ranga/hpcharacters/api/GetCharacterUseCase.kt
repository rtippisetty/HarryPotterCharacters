package dev.ranga.hpcharacters.api

import dev.ranga.hpcharacters.domain.model.HpCharacter

fun interface GetCharacterUseCase {
    suspend fun getById(id: String): HpCharacter
}