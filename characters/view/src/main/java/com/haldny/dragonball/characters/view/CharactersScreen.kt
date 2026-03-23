package com.haldny.dragonball.characters.view

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.haldny.dragonball.design.components.CharacterGridItem
import com.haldny.dragonball.design.screens.EmptyScreen
import com.haldny.dragonball.design.screens.ErrorScreen
import com.haldny.dragonball.design.screens.LoadingScreen
import com.haldny.dragonball.design.theme.Dimens
import kotlinx.coroutines.flow.distinctUntilChanged

@Composable
fun CharactersScreen(
    onNavigateToDetail: (Int) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: CharactersViewModel = hiltViewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.uiEffect.collect { effect ->
            when (effect) {
                is CharactersUiEffect.NavigateToDetail -> onNavigateToDetail(effect.id)
            }
        }
    }

    when (val ui = state) {
        CharactersUiState.InitialLoading -> LoadingScreen(
            modifier = modifier.testTag(CharactersListTestTags.LOADING)
        )
        CharactersUiState.Error -> ErrorScreen(
            modifier = modifier.testTag(CharactersListTestTags.ERROR)
        ) { viewModel.onAction(CharactersUserAction.Retry) }
        CharactersUiState.Empty -> EmptyScreen(
            modifier = modifier.testTag(CharactersListTestTags.EMPTY)
        ) { viewModel.onAction(CharactersUserAction.Refresh) }
        is CharactersUiState.Loaded -> ListCharactersScreen(
            modifier = modifier,
            state = ui,
            onItemClick = { viewModel.onAction(CharactersUserAction.OpenCharacter(it)) },
            onLoadMore = { viewModel.onAction(CharactersUserAction.LoadNextPage) },
        )
    }
}

@Composable
fun ListCharactersScreen(
    state: CharactersUiState.Loaded,
    onItemClick: (id: Int) -> Unit,
    onLoadMore: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val content = state.content
    val gridState = rememberLazyGridState()

    LaunchedEffect(gridState, content.characters.size, content.hasNextPage, content.isAppending) {
        snapshotFlow {
            val layoutInfo = gridState.layoutInfo
            val lastVisible = layoutInfo.visibleItemsInfo.lastOrNull()?.index
            val total = layoutInfo.totalItemsCount
            if (lastVisible == null || total == 0) {
                null
            } else {
                Triple(lastVisible, total, content.isAppending)
            }
        }
            .distinctUntilChanged()
            .collect { triple ->
                if (triple == null) return@collect
                val (lastVisibleIndex, totalItems, isAppending) = triple
                if (isAppending || !content.hasNextPage) return@collect
                if (lastVisibleIndex >= totalItems - 2) {
                    onLoadMore()
                }
            }
    }

    LazyVerticalGrid(
        state = gridState,
        modifier = modifier
            .fillMaxSize()
            .testTag(CharactersListTestTags.LIST),
        columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(Dimens.gridContentPadding),
    ) {
        items(
            items = content.characters,
            key = { it.id },
        ) { item ->
            CharacterGridItem(
                name = item.name,
                imageUrl = item.image,
                onClick = { onItemClick(item.id) },
            )
        }
        if (content.isAppending) {
            item(span = { GridItemSpan(maxLineSpan) }) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag(CharactersListTestTags.APPEND_LOADING)
                        .padding(16.dp),
                    contentAlignment = Alignment.Center,
                ) {
                    CircularProgressIndicator()
                }
            }
        }
    }
}
