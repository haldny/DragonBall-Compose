package com.haldny.dragonball.characters.view

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.haldny.dragonball.characters.domain.DragonBallCharacter
import com.haldny.dragonball.characters.domain.Gender
import com.haldny.dragonball.characters.domain.Race
import com.haldny.dragonball.design.theme.DragonBallComposeTheme
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class CharactersScreenComposeTest {

    @get:Rule
    val composeRule = createComposeRule()

    private val sampleCharacter = DragonBallCharacter(
        id = 1,
        name = "Goku",
        ki = "0",
        maxKi = "0",
        image = "https://example.com/goku.png",
        description = "",
        gender = Gender.MALE,
        race = Race.SAIYAN,
    )

    @Test
    fun when_repository_never_completes_then_loading_tag_is_shown() {
        val repo = FakeCharactersRepository(FakeCharactersRepository.Scenario.LoadingForever)
        val viewModel = CharactersViewModel(repository = repo)

        composeRule.setContent {
            DragonBallComposeTheme {
                CharactersScreen(
                    onNavigateToDetail = {},
                    viewModel = viewModel,
                )
            }
        }

        composeRule.onNodeWithTag(CharactersListTestTags.LOADING).assertIsDisplayed()
    }

    @Test
    fun when_repository_returns_characters_then_list_tag_is_shown() {
        val repo = FakeCharactersRepository(
            scenario = FakeCharactersRepository.Scenario.Success,
            characters = listOf(sampleCharacter),
        )
        val viewModel = CharactersViewModel(repository = repo)

        composeRule.setContent {
            DragonBallComposeTheme {
                CharactersScreen(
                    onNavigateToDetail = {},
                    viewModel = viewModel,
                )
            }
        }

        composeRule.waitUntil(timeoutMillis = 5_000) {
            composeRule.onAllNodes(hasTestTag(CharactersListTestTags.LIST))
                .fetchSemanticsNodes()
                .isNotEmpty()
        }
        composeRule.onNodeWithTag(CharactersListTestTags.LIST).assertIsDisplayed()
    }

    @Test
    fun when_repository_returns_empty_then_empty_tag_is_shown() {
        val repo = FakeCharactersRepository(FakeCharactersRepository.Scenario.Empty)
        val viewModel = CharactersViewModel(repository = repo)

        composeRule.setContent {
            DragonBallComposeTheme {
                CharactersScreen(
                    onNavigateToDetail = {},
                    viewModel = viewModel,
                )
            }
        }

        composeRule.waitUntil(timeoutMillis = 5_000) {
            composeRule.onAllNodes(hasTestTag(CharactersListTestTags.EMPTY))
                .fetchSemanticsNodes()
                .isNotEmpty()
        }
        composeRule.onNodeWithTag(CharactersListTestTags.EMPTY).assertIsDisplayed()
    }

    @Test
    fun when_repository_fails_then_error_tag_is_shown() {
        val repo = FakeCharactersRepository(FakeCharactersRepository.Scenario.Error)
        val viewModel = CharactersViewModel(repository = repo)

        composeRule.setContent {
            DragonBallComposeTheme {
                CharactersScreen(
                    onNavigateToDetail = {},
                    viewModel = viewModel,
                )
            }
        }

        composeRule.waitUntil(timeoutMillis = 5_000) {
            composeRule.onAllNodes(hasTestTag(CharactersListTestTags.ERROR))
                .fetchSemanticsNodes()
                .isNotEmpty()
        }
        composeRule.onNodeWithTag(CharactersListTestTags.ERROR).assertIsDisplayed()
    }
}
