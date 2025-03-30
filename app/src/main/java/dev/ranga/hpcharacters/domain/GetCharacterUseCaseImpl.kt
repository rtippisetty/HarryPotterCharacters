package dev.ranga.hpcharacters.domain

import dev.ranga.hpcharacters.domain.model.HpCharacter
import dev.ranga.hpcharacters.exposedApi.GetCharacterUseCase
import javax.inject.Inject

class GetCharacterUseCaseImpl @Inject constructor(
    private val charactersRepository: CharactersRepository
) : GetCharacterUseCase {
    override suspend fun getById(id: String): HpCharacter {
        return charactersRepository.getCharacterById(id)
    }
}