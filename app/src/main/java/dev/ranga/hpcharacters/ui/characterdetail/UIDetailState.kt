package dev.ranga.hpcharacters.ui.characterdetail

import dev.ranga.hpcharacters.ui.model.UICharacterDetail

sealed interface UIDetailState {
    object Loading : UIDetailState
    data class Success(val character: UICharacterDetail) : UIDetailState
    object Error : UIDetailState
}