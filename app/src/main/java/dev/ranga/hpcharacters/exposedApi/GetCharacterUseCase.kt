package dev.ranga.hpcharacters.exposedApi

import dev.ranga.hpcharacters.domain.model.HpCharacter

fun interface GetCharacterUseCase {
    suspend fun getById(id: String): HpCharacter
}