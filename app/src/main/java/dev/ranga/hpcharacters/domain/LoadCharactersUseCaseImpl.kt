package dev.ranga.hpcharacters.domain

import dev.ranga.hpcharacters.exposedApi.LoadCharactersUseCase
import javax.inject.Inject

class LoadCharactersUseCaseImpl @Inject constructor(
    private val charactersRepository: CharactersRepository
) : LoadCharactersUseCase {

    override suspend fun load() {
        charactersRepository.loadCharacters()
    }
}