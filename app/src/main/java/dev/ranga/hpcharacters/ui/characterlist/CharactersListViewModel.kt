package dev.ranga.hpcharacters.ui.characterlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.ranga.hpcharacters.domain.model.HpCharacter
import dev.ranga.hpcharacters.exposedApi.GetCachedCharactersUseCase
import dev.ranga.hpcharacters.exposedApi.ReloadCharactersUseCase
import dev.ranga.hpcharacters.ui.model.UICharacter
import dev.ranga.hpcharacters.ui.model.UIModelMapper
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CharactersListViewModel @Inject constructor(
    getCachedCharactersUseCase: GetCachedCharactersUseCase,
    private val reloadCharactersUseCase: ReloadCharactersUseCase,
    private val uiModelMapper: UIModelMapper,
) : ViewModel() {

    val characters: StateFlow<List<UICharacter>> = getCachedCharactersUseCase
        .get()
        .map<List<HpCharacter>, List<UICharacter>> { characters ->
            characters.map { uiModelMapper.mapToUiCharacter(it) }
        }
        .catch {
            // Handle error
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    init {
        viewModelScope.launch {
            _isLoading.value = true
            reloadCharactersUseCase.reload()
            _isLoading.value = false
        }
    }
}