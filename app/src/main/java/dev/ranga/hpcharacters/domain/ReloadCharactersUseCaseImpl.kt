package dev.ranga.hpcharacters.domain

import dev.ranga.hpcharacters.exposedApi.ReloadCharactersUseCase
import javax.inject.Inject

class ReloadCharactersUseCaseImpl @Inject constructor(
    private val charactersRepository: CharactersRepository
) : ReloadCharactersUseCase {

    override suspend fun reload() {
        charactersRepository.loadCharacters()
    }
}