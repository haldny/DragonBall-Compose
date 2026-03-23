package com.haldny.dragonball.character.detail.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.haldny.dragonball.design.R
import com.haldny.dragonball.design.components.DragonBallScaffold
import com.haldny.dragonball.design.screens.ErrorScreen
import com.haldny.dragonball.design.screens.LoadingScreen
import com.haldny.dragonball.design.theme.Dimens

@Composable
fun CharacterDetailScreen(
    id: Int,
    onBack: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val viewModel = hiltViewModel<CharacterDetailViewModel, CharacterDetailViewModel.Factory>(
        creationCallback = { factory -> factory.create(id = id) }
    )

    val state by viewModel.state.collectAsStateWithLifecycle()
    val context = LocalContext.current

    DragonBallScaffold(
        title = context.getString(R.string.character_detail_title),
        navigationIcon = {
            IconButton(onClick = onBack) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = context.getString(R.string.content_description_back),
                )
            }
        },
    ) { paddingValues ->
        when (val ui = state) {
            UiState.Loading -> LoadingScreen(modifier = modifier.padding(paddingValues))
            UiState.Error -> ErrorScreen(modifier = modifier.padding(paddingValues)) {
                viewModel.getCharacterDetail(id)
            }
            is UiState.Loaded -> CharacterScreen(
                modifier = modifier.padding(paddingValues),
                state = ui,
            )
        }
    }
}

@Composable
fun CharacterScreen(
    modifier: Modifier = Modifier,
    state: UiState.Loaded,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(Dimens.screenPadding)
            .verticalScroll(rememberScrollState())
    ) {
        AsyncImage(
            modifier = Modifier
                .fillMaxWidth()
                .height(Dimens.characterImageHeight),
            model = state.character.image,
            contentDescription = state.character.name,
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
                .height(Dimens.characterImageHeight),
            model = state.character.originPlanet?.image,
            contentDescription = state.character.originPlanet?.name,
            contentScale = ContentScale.Fit,
        )

        Text(
            text = state.character.originPlanet?.name.orEmpty(),
            style = MaterialTheme.typography.headlineLarge
        )
        Text(text = state.character.originPlanet?.description.orEmpty())
    }
}
