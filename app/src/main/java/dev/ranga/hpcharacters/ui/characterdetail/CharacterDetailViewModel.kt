package dev.ranga.hpcharacters.ui.characterdetail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.ranga.hpcharacters.api.GetCharacterUseCase
import dev.ranga.hpcharacters.ui.model.UIModelMapper
import dev.ranga.hpcharacters.ui.navigation.CharacterDetail
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.coroutines.cancellation.CancellationException

@HiltViewModel
class CharacterDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getCharacterUseCase: GetCharacterUseCase,
    private val uiModelMapper: UIModelMapper
) : ViewModel() {

    private val characterId: String = savedStateHandle[CharacterDetail::id.name]
        ?: ""

    private val _characterDetailState = MutableStateFlow<UIDetailState>(UIDetailState.Loading)
    val characterDetailState: StateFlow<UIDetailState> = _characterDetailState

    init {
        if(characterId.isEmpty()) {
            _characterDetailState.value = UIDetailState.Error
        } else {

            viewModelScope.launch {
                runCatching {
                    val character = getCharacterUseCase.getById(characterId)
                    uiModelMapper.mapToUiCharacterDetail(character)
                }.onSuccess { uiCharacter ->
                    _characterDetailState.value = UIDetailState.Success(
                        character = uiCharacter
                    )
                }.onFailure {
                    if (it is CancellationException) throw it
                    _characterDetailState.value = UIDetailState.Error
                }
            }
        }
    }
}