package com.haldny.dragonball.characters.view

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.haldny.dragonball.characters.domain.CharactersPage
import com.haldny.dragonball.characters.domain.CharactersPagingConfig.PAGE_LIMIT
import com.haldny.dragonball.characters.domain.CharactersRepository
import com.haldny.dragonball.core.business.BusinessResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CharactersViewModel @Inject constructor(
    private val repository: CharactersRepository
) : ViewModel() {

    private val _state = MutableStateFlow<CharactersUiState>(CharactersUiState.InitialLoading)
    val state = _state.asStateFlow()

    private val _uiEffect = MutableSharedFlow<CharactersUiEffect>(extraBufferCapacity = 1)
    val uiEffect = _uiEffect.asSharedFlow()

    init {
        onAction(CharactersUserAction.Refresh)
    }

    fun onAction(action: CharactersUserAction) {
        when (action) {
            CharactersUserAction.Refresh,
            CharactersUserAction.Retry,
            -> loadFirstPage()
            CharactersUserAction.LoadNextPage -> loadNextPage()
            is CharactersUserAction.OpenCharacter -> emitNavigate(action.id)
        }
    }

    private fun emitNavigate(id: Int) {
        viewModelScope.launch {
            _uiEffect.emit(CharactersUiEffect.NavigateToDetail(id))
        }
    }

    private fun loadFirstPage() = viewModelScope.launch {
        _state.value = CharactersUiState.InitialLoading
        runCatching { repository.getCharactersPage(page = 1, limit = PAGE_LIMIT) }.fold(
            onSuccess = { handleFirstPageResult(it) },
            onFailure = { _state.value = CharactersUiState.Error }
        )
    }

    private fun handleFirstPageResult(result: BusinessResult<CharactersPage>) {
        when (result) {
            is BusinessResult.Failure -> _state.value = CharactersUiState.Error
            is BusinessResult.Success -> {
                val page = result.data
                if (page.items.isEmpty()) {
                    _state.value = CharactersUiState.Empty
                } else {
                    _state.value = CharactersUiState.Loaded(
                        CharactersListContent(
                            characters = page.items.toImmutableList(),
                            hasNextPage = page.hasNextPage,
                            isAppending = false,
                            nextPageToLoad = 2,
                        )
                    )
                }
            }
        }
    }

    private fun loadNextPage() = viewModelScope.launch {
        val loaded = _state.value as? CharactersUiState.Loaded ?: return@launch
        val content = loaded.content
        if (!content.hasNextPage || content.isAppending) return@launch

        _state.value = CharactersUiState.Loaded(
            content.copy(isAppending = true)
        )

        runCatching {
            repository.getCharactersPage(page = content.nextPageToLoad, limit = PAGE_LIMIT)
        }.fold(
            onSuccess = { result ->
                when (result) {
                    is BusinessResult.Failure -> {
                        _state.value = CharactersUiState.Loaded(
                            content.copy(isAppending = false)
                        )
                    }
                    is BusinessResult.Success -> {
                        val page = result.data
                        val merged = (content.characters + page.items).toImmutableList()
                        _state.value = CharactersUiState.Loaded(
                            content.copy(
                                characters = merged,
                                hasNextPage = page.hasNextPage,
                                isAppending = false,
                                nextPageToLoad = content.nextPageToLoad + 1,
                            )
                        )
                    }
                }
            },
            onFailure = {
                _state.value = CharactersUiState.Loaded(
                    content.copy(isAppending = false)
                )
            }
        )
    }
}
