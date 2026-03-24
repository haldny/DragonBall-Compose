package com.haldny.dragonball

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.haldny.dragonball.character.detail.domain.CharacterDetailRepository
import com.haldny.dragonball.characters.domain.CharactersRepository
import com.haldny.dragonball.characters.view.CharactersListTestTags
import com.haldny.dragonball.character.detail.data.di.CharacterDetailModule
import com.haldny.dragonball.characters.data.di.CharactersModule
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
class MainAppListLoadingInstrumentedTest {

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeRule = createAndroidComposeRule<MainActivity>()

    @BindValue
    @JvmField
    val charactersRepository: CharactersRepository =
        FakeCharactersRepository(FakeCharactersRepository.Scenario.LoadingForever)

    @BindValue
    @JvmField
    val characterDetailRepository: CharacterDetailRepository =
        FakeCharacterDetailRepository(
            FakeCharacterDetailRepository.Scenario.Success,
            DomainTestFixtures.gokuCharacterDetail,
        )

    @Test
    fun characters_screen_shows_loading() {
        composeRule.onNodeWithTag(CharactersListTestTags.LOADING).assertIsDisplayed()
    }
}
