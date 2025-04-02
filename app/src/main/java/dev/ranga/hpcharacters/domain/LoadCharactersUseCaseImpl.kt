package dev.ranga.hpcharacters.domain

import dev.ranga.hpcharacters.exposedApi.LoadCharactersUseCase
import javax.inject.Inject

class LoadCharactersUseCaseImpl @Inject constructor(
    private val hpCharactersRepository: HpCharactersRepository
) : LoadCharactersUseCase {

    override suspend fun load() {
        hpCharactersRepository.loadCharacters()
    }
}