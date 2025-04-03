package dev.ranga.hpcharacters.api

import dev.ranga.hpcharacters.domain.model.HpCharacter
import kotlinx.coroutines.flow.Flow

fun interface GetCachedCharactersUseCase {
    fun getAll(): Flow<List<HpCharacter>>
}