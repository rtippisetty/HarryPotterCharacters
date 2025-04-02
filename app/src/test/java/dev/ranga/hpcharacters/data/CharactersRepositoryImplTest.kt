package dev.ranga.hpcharacters.data

import dev.ranga.hpcharacters.data.local.HpCharacterDao
import dev.ranga.hpcharacters.data.mapper.CharacterModelMapper
import dev.ranga.hpcharacters.data.model.HpCharacterDto
import dev.ranga.hpcharacters.data.remote.HpCharacterService
import dev.ranga.hpcharacters.domain.model.HpCharacter
import dev.ranga.hpcharacters.testFixtures.Fixtures
import dev.ranga.hpcharacters.testFixtures.Fixtures.hpCharacter1
import dev.ranga.hpcharacters.testFixtures.Fixtures.hpCharacterEntities
import dev.ranga.hpcharacters.testFixtures.Fixtures.hpCharacterEntity1
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

@OptIn(ExperimentalCoroutinesApi::class)
class CharactersRepositoryImplTest {

    private lateinit var hpCharacterDao: HpCharacterDao
    private lateinit var hpCharacterService: HpCharacterService
    private lateinit var characterMapper: CharacterModelMapper
    private lateinit var charactersRepository: HpCharactersRepositoryImpl

    @BeforeEach
    fun setup() {
        hpCharacterDao = mockk()
        hpCharacterService = mockk()
        characterMapper = mockk()
        charactersRepository = HpCharactersRepositoryImpl(
            hpCharacterDao = hpCharacterDao,
            hpCharacterService = hpCharacterService,
            characterMapper = characterMapper
        )
    }

    @Test
    fun `getCachedCharacters successful retrieval`() = runTest {
        // Arrange
        val characterEntities = hpCharacterEntity1
        val domainCharacters = hpCharacter1
        coEvery { hpCharacterDao.getAllCharacters() } returns flowOf(listOf(characterEntities))
        every { characterMapper.mapEntityToDomain(characterEntities) } returns domainCharacters

        // Act
        val result = charactersRepository.getCachedCharacters().first()

        // Assert
        assertEquals(listOf(domainCharacters), result)
    }

    @Test
    fun `getCachedCharacters empty cache`() = runTest {
        // Arrange
        every { hpCharacterDao.getAllCharacters() } returns flowOf(emptyList())

        // Act
        val result = charactersRepository.getCachedCharacters().toList()

        // Assert
        assertEquals(listOf(emptyList<HpCharacter>()), result)
    }

    @Test
    fun `getCachedCharacters dao error handling`() = runTest {
        // Arrange
        val exception = RuntimeException("Database error")
        coEvery { hpCharacterDao.getAllCharacters() } returns flow { throw exception }

        // Assert
        assertThrows<RuntimeException> {
            charactersRepository.getCachedCharacters().first()
        }
    }

    @Test
    fun `getCachedCharacters mapping error`() = runTest {
        // Arrange
        val characterEntities = hpCharacterEntities
        val exception = RuntimeException("Mapping error")
        every { hpCharacterDao.getAllCharacters() } returns flowOf(characterEntities)
        every { characterMapper.mapEntityToDomain(any()) } throws exception

        // Act & Assert
        assertThrows<RuntimeException> {
            charactersRepository.getCachedCharacters().first()
        }
    }

    @Test
    fun `getCharacterById successful retrieval`() = runTest {
        // Arrange
        val characterId = "1"
        val characterEntity = hpCharacterEntity1
        val domainCharacter = hpCharacter1
        coEvery { hpCharacterDao.getCharacterById(characterId) } returns characterEntity
        coEvery { characterMapper.mapEntityToDomain(characterEntity) } returns domainCharacter

        // Act
        val result = charactersRepository.getCharacterById(characterId)

        // Assert
        assertEquals(domainCharacter, result)
        coVerify { hpCharacterDao.getCharacterById(characterId) }
        coVerify { characterMapper.mapEntityToDomain(characterEntity) }
    }

    @Test
    fun `loadCharacters successful load and insert`() = runTest {
        // Arrange
        val hpCharacterDtos = Fixtures.hpCharacterDtos
        val characterEntities = hpCharacterEntities
        coEvery { hpCharacterService.getAllCharacters() } returns hpCharacterDtos
        every { characterMapper.mapDtoToEntity(any()) } answers {
            val dto = firstArg<HpCharacterDto>()
            characterEntities.first { it.id == dto.id }
        }
        coEvery { hpCharacterDao.insertAllCharacters(any()) } returns Unit
        // Act
        charactersRepository.loadCharacters()
        // Assert
        coVerify { hpCharacterService.getAllCharacters() }
        coVerify { hpCharacterDao.insertAllCharacters(characterEntities) }
    }
}