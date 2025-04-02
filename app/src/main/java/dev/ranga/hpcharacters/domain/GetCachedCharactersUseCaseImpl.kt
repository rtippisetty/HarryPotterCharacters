package dev.ranga.hpcharacters.domain

import dev.ranga.hpcharacters.domain.model.HpCharacter
import dev.ranga.hpcharacters.exposedApi.GetCachedCharactersUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCachedCharactersUseCaseImpl @Inject constructor(
    private val hpCharactersRepository: HpCharactersRepository
) : GetCachedCharactersUseCase {

    override fun get(): Flow<List<HpCharacter>> {
        return hpCharactersRepository.getCachedCharacters()
    }
}