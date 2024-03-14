package com.haldny.dragonball.character.detail.view

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.haldny.dragonball.character.detail.domain.CharacterDetailRepository
import com.haldny.dragonball.character.detail.domain.DragonBallCharacterDetail
import com.haldny.dragonball.core.business.BusinessResult
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

@HiltViewModel(assistedFactory = CharacterDetailViewModel.Factory::class)
class CharacterDetailViewModel @AssistedInject constructor(
    @Assisted id: Int,
    private val repository: CharacterDetailRepository,
) : ViewModel() {

    @AssistedFactory
    interface Factory {
        fun create(id: Int): CharacterDetailViewModel
    }

    private val _state = MutableStateFlow<UiState>(UiState.Loading)
    val state = _state.asStateFlow()

    init {
        getCharacterDetail(id)
    }

    fun getCharacterDetail(id: Int) = viewModelScope.launch {
        setState(UiState.Loading)
        runCatching { repository.getCharacterDetail(id) }.fold(
            onSuccess = {
                handleResult(it)
            },
            onFailure = {
                setState(UiState.Error)
            }
        )
    }

    private fun handleResult(result: BusinessResult<DragonBallCharacterDetail>) {
        when(result) {
            is BusinessResult.Failure -> setState(UiState.Error)
            is BusinessResult.Success -> setState(UiState.Loaded(result.data))
        }
    }

    private fun setState(updatedObject: UiState) {
        _state.value = updatedObject
    }
}

sealed interface UiState {
    data object Loading : UiState
    data object Error : UiState
    data class Loaded(
        val character: DragonBallCharacterDetail,
    ) : UiState
}
