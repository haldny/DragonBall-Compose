package com.haldny.dragonball.characters.view

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.haldny.dragonball.characters.domain.DragonBallCharacter
import com.haldny.dragonball.core.navigation.openCharacterDetails
import com.haldny.dragonball.design.screens.ErrorScreen
import com.haldny.dragonball.design.screens.LoadingScreen

@Composable
fun CharactersScreen(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    viewModel: CharactersViewModel = hiltViewModel()
) {
    val state = viewModel.state.collectAsStateWithLifecycle()
    when (state.value) {
        UiState.Loading -> LoadingScreen(modifier = modifier)
        UiState.Error, UiState.Empty -> ErrorScreen(modifier = modifier) { viewModel.loadCharacters() }
        is UiState.Loaded -> {
            ListCharactersScreen(modifier = modifier, state.value as UiState.Loaded) {
                navController.openCharacterDetails(it)
            }
        }
    }
}

@Composable
fun ListCharactersScreen(
    modifier: Modifier = Modifier,
    state: UiState.Loaded,
    onItemClickListener: (id: Int) -> Unit
) {
    Box(modifier = Modifier.fillMaxSize(), content = {
        LazyVerticalGrid(
            modifier = Modifier
                .fillMaxSize(),
            columns = GridCells.Fixed(2),
            contentPadding = PaddingValues(8.dp),
            content = {
                items(count = state.characters.size) {
                    CharacterItemScreen(
                        modifier = modifier,
                        item = state.characters[it],
                        onItemClickListener = onItemClickListener
                    )
                }
            }
        )
    })
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun CharacterItemScreen(
    modifier: Modifier = Modifier,
    item: DragonBallCharacter,
    onItemClickListener: (id: Int) -> Unit
) {
    Card(modifier = Modifier
        .fillMaxWidth()
        .height(250.dp)
        .padding(8.dp),
        onClick = {
            onItemClickListener.invoke(item.id)
        }, content = {
            Column(
                content = {
                    AsyncImage(
                        modifier = Modifier
                            .height(180.dp)
                            .fillMaxWidth(),
                        model = item.image,
                        contentDescription = null,
                        contentScale = ContentScale.Fit,
                    )

                    Box(modifier = Modifier
                        .fillMaxSize(),
                        contentAlignment = Alignment.Center,
                        content = {
                            Text(
                                text = item.name,
                                textAlign = TextAlign.Center,
                                fontSize = 22.sp,
                                maxLines = 1,
                                color = MaterialTheme.colorScheme.primary
                            )
                        }
                    )
                })
        })
}
