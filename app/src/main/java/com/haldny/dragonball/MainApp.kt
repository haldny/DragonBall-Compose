package com.haldny.dragonball

import android.annotation.SuppressLint
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.haldny.dragonball.character.detail.view.CharacterDetailScreen
import com.haldny.dragonball.characters.view.CharactersScreen
import com.haldny.dragonball.core.navigation.CHARACTERS_SCREEN_ROUTE
import com.haldny.dragonball.core.navigation.CHARACTER_DETAIL_SCREEN_ROUTE

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MainApp() {

    val navController = rememberNavController()
    var routeState by remember { mutableStateOf(CHARACTERS_SCREEN_ROUTE) }

    Scaffold(
        content = {
            Navigation(
                navController = navController
            )
        }
    )
}

@Composable
fun Navigation(
    navController: NavHostController
) {
    NavHost(
        navController = navController,
        startDestination = CHARACTERS_SCREEN_ROUTE,
    ) {
        composable(
            route = CHARACTERS_SCREEN_ROUTE,
        ) { _ ->
            CharactersScreen(
                navController = navController,
            )
        }

        composable(
            route = CHARACTER_DETAIL_SCREEN_ROUTE
        ) { _ ->
            val id = navController.previousBackStackEntry?.savedStateHandle?.get<Int>("id")
            CharacterDetailScreen(
                id = id ?: 0,
                navController = navController
            )
        }

    }
}
