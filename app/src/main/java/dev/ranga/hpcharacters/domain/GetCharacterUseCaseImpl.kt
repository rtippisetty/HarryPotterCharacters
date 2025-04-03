package dev.ranga.hpcharacters.domain

import dev.ranga.hpcharacters.domain.model.HpCharacter
import dev.ranga.hpcharacters.api.GetCharacterUseCase
import javax.inject.Inject

class GetCharacterUseCaseImpl @Inject constructor(
    private val hpCharactersRepository: HpCharactersRepository
) : GetCharacterUseCase {
    override suspend fun getById(id: String): HpCharacter {
        return hpCharactersRepository.getCharacterById(id)
    }
}