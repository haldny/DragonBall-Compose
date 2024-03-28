package com.haldny.dragonball.character.detail.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.haldny.dragonball.design.screens.ErrorScreen
import com.haldny.dragonball.design.screens.LoadingScreen

@Composable
fun CharacterDetailScreen(
    modifier: Modifier = Modifier,
    id: Int,
    navController: NavHostController
) {
    val viewModel = hiltViewModel<CharacterDetailViewModel, CharacterDetailViewModel.Factory>(
        creationCallback = { factory -> factory.create(id = id) }
    )

    val state by viewModel.state.collectAsStateWithLifecycle()
    when (state) {
        UiState.Loading -> LoadingScreen(modifier = modifier)
        UiState.Error -> ErrorScreen(modifier = modifier) { viewModel.getCharacterDetail(id) }
        is UiState.Loaded -> CharacterScreen(modifier = modifier, state as UiState.Loaded)
    }
}

@Composable
fun CharacterScreen(
    modifier: Modifier = Modifier,
    state: UiState.Loaded,
) {
    Column(modifier = Modifier
        .fillMaxSize()
        .padding(32.dp)
        .verticalScroll(rememberScrollState())
    ) {
        AsyncImage(
            modifier = Modifier
                .fillMaxWidth()
                .height(180.dp),
            model = state.character.image,
            contentDescription = null,
            contentScale = ContentScale.Fit,
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = state.character.name,
            style = MaterialTheme.typography.headlineLarge
        )
        Text(text = state.character.ki)
        Text(text = state.character.maxKi)
        Text(text = state.character.gender?.value.orEmpty())
        Text(text = state.character.race?.value.orEmpty())

        Spacer(modifier = Modifier.height(32.dp))

        AsyncImage(
            modifier = Modifier
                .fillMaxWidth()
                .height(180.dp),
            model = state.character.originPlanet?.image,
            contentDescription = null,
            contentScale = ContentScale.Fit,
        )

        Text(
            text = state.character.originPlanet?.name.orEmpty(),
            style = MaterialTheme.typography.headlineLarge
        )
        Text(text = state.character.originPlanet?.description.orEmpty())
    }
}
