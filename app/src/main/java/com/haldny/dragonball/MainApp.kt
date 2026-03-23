package com.haldny.dragonball

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.ui.NavDisplay
import com.haldny.dragonball.character.detail.view.CharacterDetailScreen
import com.haldny.dragonball.characters.view.CharactersScreen
import com.haldny.dragonball.core.navigation.AppDestination
import com.haldny.dragonball.design.R
import com.haldny.dragonball.design.components.DragonBallScaffold

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MainApp() {
    val backStack = remember {
        mutableStateListOf<AppDestination>(AppDestination.CharactersList)
    }
    val context = LocalContext.current

    NavDisplay(
        backStack = backStack,
        onBack = { if (backStack.size > 1) backStack.removeLastOrNull() },
        entryProvider = { key ->
            when (key) {
                is AppDestination.CharactersList -> NavEntry(key) {
                    DragonBallScaffold(title = context.getString(R.string.characters_list_title)) { paddingValues ->
                        CharactersScreen(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(paddingValues),
                            onNavigateToDetail = { id ->
                                backStack.add(AppDestination.CharacterDetail(id))
                            },
                        )
                    }
                }
                is AppDestination.CharacterDetail -> NavEntry(key) {
                    CharacterDetailScreen(
                        id = key.id,
                        onBack = { backStack.removeLastOrNull() },
                        modifier = Modifier.fillMaxSize(),
                    )
                }
            }
        },
    )
}
