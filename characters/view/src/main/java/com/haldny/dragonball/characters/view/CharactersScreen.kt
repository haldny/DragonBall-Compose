package com.haldny.dragonball.characters.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.haldny.dragonball.characters.domain.DragonBallCharacter

const val CHARACTERS_SCREEN_ROUTE = "characters-screen"

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
                //TODO: Navigate to details
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
                    CharacterItemScreen(item = state.characters[it], onItemClickListener = onItemClickListener)
                }
            }
        )
    })
}

@Composable
fun LoadingScreen(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}

@Composable fun ErrorScreen(
    modifier: Modifier = Modifier,
    onButtonClick: () -> Unit,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val context = LocalContext.current

        Text(text = context.getString(R.string.error_message_text))

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = { onButtonClick.invoke() },
        ) {
            Text(text = context.getString(R.string.button_try_again_text))
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun CharacterItemScreen(item: DragonBallCharacter, onItemClickListener: (id: Int) -> Unit) {
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
                        })
                })
        })
}

@Preview(showBackground = true)
@Composable
internal fun LoadingPreview() = LoadingScreen()

@Preview(showBackground = true)
@Composable
internal fun ErrorPreview() = ErrorScreen() {}
