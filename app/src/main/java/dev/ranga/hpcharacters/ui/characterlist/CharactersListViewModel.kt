package dev.ranga.hpcharacters.ui.characterlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.ranga.hpcharacters.Analytics.Logger
import dev.ranga.hpcharacters.api.GetCachedCharactersUseCase
import dev.ranga.hpcharacters.api.LoadCharactersUseCase
import dev.ranga.hpcharacters.ui.common.containsQuery
import dev.ranga.hpcharacters.ui.model.UICharacter
import dev.ranga.hpcharacters.ui.model.UIModelMapper
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.coroutines.cancellation.CancellationException

@HiltViewModel
class CharactersListViewModel @Inject constructor(
    getCachedCharactersUseCase: GetCachedCharactersUseCase,
    private val loadCharactersUseCase: LoadCharactersUseCase,
    private val uiModelMapper: UIModelMapper,
    private val logger: Logger
) : ViewModel() {

    private val _searchQuery = MutableStateFlow("")

    private val _cachedCharacters: StateFlow<List<UICharacter>> =
        getCachedCharactersUseCase.getAll()
            .mapNotNull { it.map(uiModelMapper::mapToUiCharacter) }
            .catch { e ->
                if (e is CancellationException) throw e
                logger.error("CharactersListViewModel", "Error loading cached characters", e)
            }.stateIn(
                scope = viewModelScope,
                started = SharingStarted.Eagerly,
                initialValue = emptyList()
            )


    @OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
    val characters: StateFlow<List<UICharacter>> = _searchQuery
        .debounce(500)
        .map{ it.trim() }
        .flatMapLatest { query ->
            _cachedCharacters.map { characters ->
                characters.filter {
                    query.isEmpty() || it.name.containsQuery(query) || it.actor.containsQuery(query)
                }
            }
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(500),
            initialValue = emptyList()
        )

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    init {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                loadCharactersUseCase.load()
            } catch (e: Exception) {
                if (e is CancellationException) throw e
                logger.error("CharactersListViewModel", "Error loading characters", e)
            }
            _isLoading.value = false
        }
    }

    fun searchCharacters(query: String) {
        _searchQuery.value = query
    }
}