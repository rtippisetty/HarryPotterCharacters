package dev.ranga.hpcharacters.ui.characterlist

import androidx.lifecycle.viewModelScope
import dev.ranga.hpcharacters.Analytics.Logger
import dev.ranga.hpcharacters.api.GetCachedCharactersUseCase
import dev.ranga.hpcharacters.api.LoadCharactersUseCase
import dev.ranga.hpcharacters.testFixtures.Fixtures.hpCharacter1
import dev.ranga.hpcharacters.testFixtures.Fixtures.hpCharacterUI1
import dev.ranga.hpcharacters.ui.model.UICharacter
import dev.ranga.hpcharacters.ui.model.UIModelMapper
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceTimeBy
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runCurrent
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

@OptIn(ExperimentalCoroutinesApi::class)
class CharactersListViewModelTest {
    private val getCachedCharactersUseCase = mockk<GetCachedCharactersUseCase>(relaxed = true)
    private val loadCharactersUseCase = mockk<LoadCharactersUseCase>(relaxed = true)
    private val uiModelMapper = mockk<UIModelMapper>(relaxed = true)
    private val logger = mockk<Logger>(relaxed = true)
    private val testDispatcher = StandardTestDispatcher()
    private lateinit var viewModel: CharactersListViewModel

    private val testCharacter = hpCharacter1
    private val testUICharacter = hpCharacterUI1

    @BeforeEach
    fun setup() {
        Dispatchers.setMain(testDispatcher)
    }

    @AfterEach
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `characters are loaded and mapped on init`() = runTest {
        // Given
        coEvery { getCachedCharactersUseCase.getAll() } returns flowOf(listOf(testCharacter))
        every { uiModelMapper.mapToUiCharacter(any()) } returns testUICharacter
        // When
        viewModel = CharactersListViewModel(getCachedCharactersUseCase, loadCharactersUseCase, uiModelMapper, logger)
        // Launch a coroutine to collect from _cachedCharacters
        val job = launch {
            viewModel.characters.collect {
                //collecting from the flow
            }
        }
        // Wait for the collection to happen
        advanceUntilIdle()
        val result = viewModel.characters.value
        //Then
        assertEquals(listOf(testUICharacter), result)
        coVerify { loadCharactersUseCase.load() }
        job.cancel()
    }

    @Test
    fun `searchCharacters updates searchQuery`() = runTest {
        // Given
        coEvery { getCachedCharactersUseCase.getAll() } returns flowOf(listOf(testCharacter))
        every { uiModelMapper.mapToUiCharacter(any()) } returns testUICharacter

        viewModel = CharactersListViewModel(getCachedCharactersUseCase, loadCharactersUseCase, uiModelMapper, logger)
        // Launch a coroutine to collect from _cachedCharacters
        val job = launch {
            viewModel.characters.collect {
                //collecting from the flow
            }
        }
        // Wait for the collection to happen
        advanceUntilIdle()
        // When
        viewModel.searchCharacters("Harry")
        advanceTimeBy(500) // Debounce

        // Then
        val result = viewModel.characters.value
        assertEquals(listOf(testUICharacter), result)

        viewModel.searchCharacters("other")
        advanceTimeBy(500) // Debounce
        runCurrent()
        val result2 = viewModel.characters.value
        assertEquals(emptyList<UICharacter>(), result2)

        viewModel.searchCharacters("")
        advanceTimeBy(500) // Debounce
        runCurrent()
        val result3 = viewModel.characters.value
        assertEquals(listOf(testUICharacter), result3)
        job.cancel()
    }

    @Test
    fun `isLoading is updated correctly`() = runTest {
        // Given
        val loadingList : MutableList<Boolean> = mutableListOf()
        coEvery { getCachedCharactersUseCase.getAll() } returns flowOf(listOf(testCharacter))
        every { uiModelMapper.mapToUiCharacter(any()) } returns testUICharacter
        coEvery { loadCharactersUseCase.load() } answers {
            loadingList.add(viewModel.isLoading.value)
            Unit
        }

        viewModel = CharactersListViewModel(getCachedCharactersUseCase, loadCharactersUseCase, uiModelMapper, logger)
        viewModel.viewModelScope.launch {
            viewModel.isLoading.toList(loadingList)
        }

        // when
        advanceTimeBy(500)

        //Then
        assertTrue(loadingList[0])
        assertTrue(!loadingList[1])
        assertEquals(2, loadingList.size)

    }
    @Test
    fun `error loading characters logs the error`() = runTest {
        // Given
        val exception = RuntimeException("Test Exception")
        coEvery { getCachedCharactersUseCase.getAll() } returns flowOf(listOf(testCharacter))
        every { uiModelMapper.mapToUiCharacter(any()) } returns testUICharacter
        coEvery { loadCharactersUseCase.load() } answers {
            throw exception
        }
        viewModel = CharactersListViewModel(getCachedCharactersUseCase, loadCharactersUseCase, uiModelMapper, logger)

        // When
        advanceTimeBy(500)

        // Then
        verify {
            logger.error("CharactersListViewModel", "Error loading characters", exception)
        }
    }
}