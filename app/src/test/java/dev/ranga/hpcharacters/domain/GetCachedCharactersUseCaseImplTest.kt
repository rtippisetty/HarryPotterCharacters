package dev.ranga.hpcharacters.domain

import dev.ranga.hpcharacters.domain.model.HpCharacter
import dev.ranga.hpcharacters.testFixtures.Fixtures.hpCharacters
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

@OptIn(ExperimentalCoroutinesApi::class)
class GetCachedCharactersUseCaseImplTest {

    private lateinit var hpCharactersRepository: HpCharactersRepository
    private lateinit var getCachedCharactersUseCase: GetCachedCharactersUseCaseImpl

    @BeforeEach
    fun setup() {
        hpCharactersRepository = mockk()
        getCachedCharactersUseCase = GetCachedCharactersUseCaseImpl(hpCharactersRepository)
    }

    @Test
    fun `Successful retrieval of cached characters`() = runTest {
        // Arrange
        val cachedCharacters = hpCharacters
        coEvery { hpCharactersRepository.getCachedCharacters() } returns flowOf(cachedCharacters)

        // Act
        val result = getCachedCharactersUseCase.get().toList()

        // Assert
        assertEquals(listOf(cachedCharacters), result)
    }

    @Test
    fun `Empty list retrieval from cache`() = runTest {
        // Arrange
        coEvery { hpCharactersRepository.getCachedCharacters() } returns flowOf(emptyList())

        // Act
        val result = getCachedCharactersUseCase.get().toList()

        // Assert
        assertEquals(listOf(emptyList<HpCharacter>()), result)
    }

    @Test
    fun `Repository exception handling`() = runTest {
        // Arrange
        val exception = RuntimeException("Repository error")
        coEvery { hpCharactersRepository.getCachedCharacters() } throws exception

        // Act & Assert
        assertThrows<RuntimeException> {
            getCachedCharactersUseCase.get().toList()
        }
    }

    @Test
    fun `Multiple character retrieval`() = runTest {
        // Arrange
        val cachedCharacters = hpCharacters
        coEvery { hpCharactersRepository.getCachedCharacters() } returns flowOf(cachedCharacters)

        // Act
        val result = getCachedCharactersUseCase.get().toList()

        // Assert
        assertEquals(listOf(cachedCharacters), result)
    }

    @Test
    fun `Verify order of elements returned`() = runTest {
        // Arrange
        val cachedCharacters = hpCharacters
        coEvery { hpCharactersRepository.getCachedCharacters() } returns flowOf(cachedCharacters)

        // Act
        val result = getCachedCharactersUseCase.get().toList().first()

        // Assert
        assertEquals(cachedCharacters, result)
        assertEquals("1", result[0].id)
        assertEquals("2", result[1].id)
    }
}