package dev.ranga.hpcharacters.exposedApi

import dev.ranga.hpcharacters.domain.model.HpCharacter
import kotlinx.coroutines.flow.Flow

fun interface GetCachedCharactersUseCase {
    fun get(): Flow<List<HpCharacter>>
}