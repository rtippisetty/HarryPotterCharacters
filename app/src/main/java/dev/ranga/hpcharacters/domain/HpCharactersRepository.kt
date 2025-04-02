package dev.ranga.hpcharacters.domain

import dev.ranga.hpcharacters.domain.model.HpCharacter
import kotlinx.coroutines.flow.Flow

interface HpCharactersRepository {
    fun getCachedCharacters(): Flow<List<HpCharacter>>
    suspend fun getCharacterById(id: String): HpCharacter
    suspend fun loadCharacters()
}