package com.haldny.dragonball

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.haldny.dragonball.character.detail.data.di.CharacterDetailModule
import com.haldny.dragonball.character.detail.domain.CharacterDetailRepository
import com.haldny.dragonball.characters.data.di.CharactersModule
import com.haldny.dragonball.characters.domain.CharactersRepository
import com.haldny.dragonball.characters.view.CharactersListTestTags
import com.haldny.dragonball.design.R
import com.haldny.dragonball.testing.fake.FakeCharacterDetailRepository
import com.haldny.dragonball.testing.fake.FakeCharactersRepository
import com.haldny.dragonball.testing.fixtures.DomainTestFixtures
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.BindValue
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.UninstallModules
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@HiltAndroidTest
@UninstallModules(CharactersModule::class, CharacterDetailModule::class)
@RunWith(AndroidJUnit4::class)
class MainAppListErrorInstrumentedTest {

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeRule = createAndroidComposeRule<MainActivity>()

    @BindValue
    @JvmField
    val charactersRepository: CharactersRepository =
        FakeCharactersRepository(FakeCharactersRepository.Scenario.Error)

    @BindValue
    @JvmField
    val characterDetailRepository: CharacterDetailRepository =
        FakeCharacterDetailRepository(
            FakeCharacterDetailRepository.Scenario.Success,
            DomainTestFixtures.gokuCharacterDetail,
        )

    @Test
    fun characters_screen_shows_error_and_try_again() {
        composeRule.waitUntil(timeoutMillis = 10_000) {
            composeRule.onAllNodes(hasTestTag(CharactersListTestTags.ERROR))
                .fetchSemanticsNodes()
                .isNotEmpty()
        }
        composeRule.onNodeWithTag(CharactersListTestTags.ERROR).assertIsDisplayed()
        composeRule.onNodeWithText(testString(R.string.error_message_text)).assertIsDisplayed()
        composeRule.onNodeWithText(testString(R.string.button_try_again_text)).assertIsDisplayed()
    }
}
