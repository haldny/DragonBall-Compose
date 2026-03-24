package com.haldny.dragonball.character.detail.view

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.haldny.dragonball.character.detail.domain.DragonBallCharacterDetail
import com.haldny.dragonball.character.detail.domain.Gender
import com.haldny.dragonball.character.detail.domain.OriginPlanet
import com.haldny.dragonball.character.detail.domain.Race
import com.haldny.dragonball.design.theme.DragonBallComposeTheme
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class CharacterDetailScreenComposeTest {

    @get:Rule
    val composeRule = createComposeRule()

    private val sampleCharacter = DragonBallCharacterDetail(
        id = 1,
        name = "Goku",
        ki = "1,000",
        maxKi = "9,000",
        image = "https://example.com/goku.png",
        description = "Saiyan warrior",
        gender = Gender.MALE,
        race = Race.SAIYAN,
        originPlanet = OriginPlanet(
            id = 2,
            name = "Earth",
            isDestroyed = false,
            description = "Home planet",
            image = "https://example.com/earth.png",
        ),
        transformations = emptyList(),
    )

    @Test
    fun when_repository_never_completes_then_loading_tag_is_shown() {
        val repo = FakeCharacterDetailRepository(FakeCharacterDetailRepository.Scenario.LoadingForever)
        val viewModel = CharacterDetailViewModel(id = 1, repository = repo)

        composeRule.setContent {
            DragonBallComposeTheme {
                CharacterDetailScreen(
                    id = 1,
                    onBack = {},
                    viewModel = viewModel,
                )
            }
        }

        composeRule.onNodeWithTag(CharacterDetailTestTags.LOADING).assertIsDisplayed()
    }

    @Test
    fun when_repository_returns_character_then_content_tag_and_name_are_shown() {
        val repo = FakeCharacterDetailRepository(
            scenario = FakeCharacterDetailRepository.Scenario.Success,
            character = sampleCharacter,
        )
        val viewModel = CharacterDetailViewModel(id = 1, repository = repo)

        composeRule.setContent {
            DragonBallComposeTheme {
                CharacterDetailScreen(
                    id = 1,
                    onBack = {},
                    viewModel = viewModel,
                )
            }
        }

        composeRule.waitUntil(timeoutMillis = 5_000) {
            composeRule.onAllNodes(hasTestTag(CharacterDetailTestTags.CONTENT))
                .fetchSemanticsNodes()
                .isNotEmpty()
        }
        composeRule.onNodeWithTag(CharacterDetailTestTags.CONTENT).assertIsDisplayed()
        composeRule.onNodeWithText(sampleCharacter.name).assertIsDisplayed()
    }

    @Test
    fun when_repository_fails_then_error_tag_is_shown() {
        val repo = FakeCharacterDetailRepository(FakeCharacterDetailRepository.Scenario.Error)
        val viewModel = CharacterDetailViewModel(id = 1, repository = repo)

        composeRule.setContent {
            DragonBallComposeTheme {
                CharacterDetailScreen(
                    id = 1,
                    onBack = {},
                    viewModel = viewModel,
                )
            }
        }

        composeRule.waitUntil(timeoutMillis = 5_000) {
            composeRule.onAllNodes(hasTestTag(CharacterDetailTestTags.ERROR))
                .fetchSemanticsNodes()
                .isNotEmpty()
        }
        composeRule.onNodeWithTag(CharacterDetailTestTags.ERROR).assertIsDisplayed()
    }
}
