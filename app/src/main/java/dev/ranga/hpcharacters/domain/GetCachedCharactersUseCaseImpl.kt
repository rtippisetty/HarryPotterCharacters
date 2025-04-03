package dev.ranga.hpcharacters.domain

import dev.ranga.hpcharacters.domain.model.HpCharacter
import dev.ranga.hpcharacters.api.GetCachedCharactersUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCachedCharactersUseCaseImpl @Inject constructor(
    private val hpCharactersRepository: HpCharactersRepository
) : GetCachedCharactersUseCase {

    override fun getAll(): Flow<List<HpCharacter>> {
        return hpCharactersRepository.getCachedCharacters()
    }
}