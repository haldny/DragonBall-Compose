package com.haldny.dragonball

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.haldny.dragonball.character.detail.data.di.CharacterDetailModule
import com.haldny.dragonball.character.detail.domain.CharacterDetailRepository
import com.haldny.dragonball.character.detail.view.CharacterDetailTestTags
import com.haldny.dragonball.characters.data.di.CharactersModule
import com.haldny.dragonball.characters.domain.CharactersRepository
import com.haldny.dragonball.characters.view.CharactersListTestTags
import com.haldny.dragonball.design.R
import com.haldny.dragonball.testing.fake.FakeCharacterDetailRepository
import com.haldny.dragonball.testing.fake.FakeCharactersRepository
import com.haldny.dragonball.testing.fixtures.DomainTestFixtures
import dagger.hilt.android.testing.BindValue
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@HiltAndroidTest
@UninstallModules(CharactersModule::class, CharacterDetailModule::class)
@RunWith(AndroidJUnit4::class)
class MainAppDetailCharacterAndPlanetInstrumentedTest {

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeRule = createAndroidComposeRule<MainActivity>()

    @BindValue
    @JvmField
    val charactersRepository: CharactersRepository = FakeCharactersRepository(
        scenario = FakeCharactersRepository.Scenario.Success,
        characters = listOf(DomainTestFixtures.gokuListCharacter),
    )

    @BindValue
    @JvmField
    val characterDetailRepository: CharacterDetailRepository =
        FakeCharacterDetailRepository(
            FakeCharacterDetailRepository.Scenario.Success,
            DomainTestFixtures.gokuCharacterDetail,
        )

    @Test
    fun navigate_to_detail_character_tab_then_planet_shows_origin_data() {
        composeRule.waitUntil(timeoutMillis = 10_000) {
            composeRule.onAllNodes(hasTestTag(CharactersListTestTags.LIST))
                .fetchSemanticsNodes()
                .isNotEmpty()
        }
        composeRule.onNodeWithText("Goku").performClick()

        composeRule.waitUntil(timeoutMillis = 10_000) {
            composeRule.onAllNodes(hasTestTag(CharacterDetailTestTags.CONTENT))
                .fetchSemanticsNodes()
                .isNotEmpty()
        }
        composeRule.onNodeWithTag(CharacterDetailTestTags.CONTENT).assertIsDisplayed()
        composeRule.onNodeWithText(testString(R.string.character_detail_title)).assertIsDisplayed()
        composeRule.onNodeWithText(testString(R.string.detail_label_ki)).assertIsDisplayed()
        composeRule.onNodeWithText("1,000").assertIsDisplayed()

        composeRule.onNodeWithText(testString(R.string.detail_tab_planet)).performClick()

        composeRule.waitUntil(timeoutMillis = 5_000) {
            composeRule.onAllNodes(hasText("Earth")).fetchSemanticsNodes().isNotEmpty()
        }
        composeRule.onNodeWithText("Earth").assertIsDisplayed()
        composeRule.onNodeWithText(testString(R.string.detail_label_destroyed)).assertIsDisplayed()
        composeRule.onNodeWithText(testString(R.string.detail_no)).assertIsDisplayed()
        composeRule.onNodeWithText("Home planet").assertIsDisplayed()
    }
}
