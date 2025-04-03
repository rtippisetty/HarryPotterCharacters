package dev.ranga.hpcharacters.ui.characterdetail

import androidx.lifecycle.SavedStateHandle
import dev.ranga.hpcharacters.api.GetCharacterUseCase
import dev.ranga.hpcharacters.testFixtures.Fixtures.hpCharacter1
import dev.ranga.hpcharacters.testFixtures.Fixtures.hpCharacterDetailUI1
import dev.ranga.hpcharacters.ui.model.UIModelMapper
import dev.ranga.hpcharacters.ui.navigation.CharacterDetail
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

@OptIn(ExperimentalCoroutinesApi::class)
class CharacterDetailViewModelTest {

    private val getCharacterUseCase: GetCharacterUseCase = mockk(relaxed = true)
    private val uiModelMapper: UIModelMapper = mockk(relaxed = true)
    private lateinit var savedStateHandle: SavedStateHandle
    private lateinit var viewModel: CharacterDetailViewModel
    private val testDispatcher = StandardTestDispatcher()

    @BeforeEach
    fun setup() {
        Dispatchers.setMain(testDispatcher)
    }

    @AfterEach
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `init - loads character successfully`() = runTest {
        // Given
        val characterId = "1"
        savedStateHandle = SavedStateHandle(mapOf(CharacterDetail::id.name to characterId))
        coEvery { getCharacterUseCase.getById(characterId) } returns hpCharacter1
        every { uiModelMapper.mapToUiCharacterDetail(hpCharacter1) } returns hpCharacterDetailUI1

        // When
        viewModel = CharacterDetailViewModel(savedStateHandle, getCharacterUseCase, uiModelMapper)
        advanceUntilIdle()

        // Then
        val state = viewModel.characterDetailState.value
        assertTrue(state is UIDetailState.Success)
        assertEquals(hpCharacterDetailUI1, (state as UIDetailState.Success).character)
    }

    @Test
    fun `init - handles error when loading character`() = runTest {
        // Given
        val characterId = "1"
        savedStateHandle = SavedStateHandle(mapOf(CharacterDetail::id.name to characterId))
        val exception = RuntimeException("Failed to load character")
        coEvery { getCharacterUseCase.getById(characterId) } throws exception

        // When
        viewModel = CharacterDetailViewModel(savedStateHandle, getCharacterUseCase, uiModelMapper)
        advanceUntilIdle()

        // Then
        val state = viewModel.characterDetailState.value
        assertTrue(state is UIDetailState.Error)
    }

    @Test
    fun `init - handles missing character id`() = runTest {
        // Given
        savedStateHandle = SavedStateHandle()

        // When
        viewModel = CharacterDetailViewModel(savedStateHandle, getCharacterUseCase, uiModelMapper)
        advanceUntilIdle()
        // Then
        val state = viewModel.characterDetailState.value
        assertTrue(state is UIDetailState.Error)
    }

    @Test
    fun `init - starts in loading state`() = runTest {
        // Given
        val characterId = "1"
        savedStateHandle = SavedStateHandle(mapOf(CharacterDetail::id.name to characterId))
        coEvery { getCharacterUseCase.getById(characterId) } returns hpCharacter1
        every { uiModelMapper.mapToUiCharacterDetail(hpCharacter1) } returns hpCharacterDetailUI1

        // When
        viewModel = CharacterDetailViewModel(savedStateHandle, getCharacterUseCase, uiModelMapper)

        // Then
        val state = viewModel.characterDetailState.value
        assertTrue(state is UIDetailState.Loading)
    }
}