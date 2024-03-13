package com.haldny.dragonball.characters.view

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.haldny.dragonball.characters.domain.CharactersRepository
import com.haldny.dragonball.characters.domain.DragonBallCharacter
import com.haldny.dragonball.core.business.BusinessResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CharactersViewModel @Inject constructor(
    private val repository: CharactersRepository
) : ViewModel() {
    private val _state = MutableStateFlow<UiState>(UiState.Loading)
    val state = _state.asStateFlow()

    init {
        loadCharacters()
    }

    fun loadCharacters() = viewModelScope.launch {
        setState(UiState.Loading)
        runCatching { repository.getCharacters() }.fold(
            onSuccess = {
                handleResult(it)
            },
            onFailure = {
                setState(UiState.Error)
            }
        )

    }

    private fun handleResult(result: BusinessResult<List<DragonBallCharacter>>) {
        when(result) {
            is BusinessResult.Failure -> setState(UiState.Error)
            is BusinessResult.Success -> {
                if (result.data.isNotEmpty()) {
                    setState(UiState.Loaded(result.data.toImmutableList()))
                } else {
                    setState(UiState.Empty)
                }
            }
        }

    }

    private fun setState(updatedObject: UiState) {
        _state.value = updatedObject
    }
}

sealed interface UiState {
    data object Loading : UiState
    data object Error : UiState
    data object Empty : UiState
    data class Loaded(
        val characters: ImmutableList<DragonBallCharacter> = persistentListOf(),
    ) : UiState
}
