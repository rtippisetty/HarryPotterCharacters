package dev.ranga.hpcharacters.data

import dev.ranga.hpcharacters.data.local.HpCharacterDao
import dev.ranga.hpcharacters.data.mapper.CharacterModelMapper
import dev.ranga.hpcharacters.data.remote.HpCharacterService
import dev.ranga.hpcharacters.domain.HpCharactersRepository
import dev.ranga.hpcharacters.domain.model.HpCharacter
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class HpCharactersRepositoryImpl @Inject constructor(
    private val hpCharacterService: HpCharacterService,
    private val hpCharacterDao: HpCharacterDao,
    private val characterMapper: CharacterModelMapper,
) : HpCharactersRepository {

    override fun getCachedCharacters(): Flow<List<HpCharacter>> {
        return hpCharacterDao.getAllCharacters().map { characters ->
            characters.map { characterEntity ->
                characterMapper.mapEntityToDomain(characterEntity)
            }
        }
    }

    override suspend fun getCharacterById(id: String): HpCharacter {
        return hpCharacterDao.getCharacterById(id)?.let { characterEntity ->
            characterMapper.mapEntityToDomain(characterEntity)
        } ?: throw Exception("Character not found")
    }

    override suspend fun loadCharacters() {
        hpCharacterService.getAllCharacters().map { characterDto ->
            characterMapper.mapDtoToEntity(characterDto)
        }.let { characters ->
            hpCharacterDao.insertAllCharacters(characters)
        }
    }
}