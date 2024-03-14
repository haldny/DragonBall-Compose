package com.haldny.dragonball.characters.view

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import app.cash.turbine.test
import com.haldny.dragonball.characters.domain.CharactersRepository
import com.haldny.dragonball.characters.domain.DragonBallCharacter
import com.haldny.dragonball.characters.domain.Gender
import com.haldny.dragonball.characters.domain.Race
import com.haldny.dragonball.characters.support.TestCoroutineRule
import com.haldny.dragonball.core.business.BusinessResult
import com.haldny.dragonball.core.business.exceptions.BusinessException
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule

class CharactersViewModelTest {

    @get:Rule
    val rule: TestRule = InstantTaskExecutorRule()

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    private val repository = mockk<CharactersRepository>(relaxed = true)

    @Test
    fun `Test success flow when receive a dragon ball character`() = runTest {
        // GIVEN
        coEvery { repository.getCharacters() } answers {
            BusinessResult.Success(data = listOf(character1, character2))
        }
        val viewModel = getCharactersViewModel()

        // WHEN
        viewModel.loadCharacters()

        // THEN
        viewModel.state.test {
            assertEquals(UiState.Loaded(listOf(character1, character2).toImmutableList()), awaitItem())
        }
    }

    @Test
    fun `Test empty flow when receive an empty list of dragon ball characters`() = runTest {
        // GIVEN
        coEvery { repository.getCharacters() } answers {
            BusinessResult.Success(data = listOf())
        }
        val viewModel = getCharactersViewModel()

        // WHEN
        viewModel.loadCharacters()

        // THEN
        viewModel.state.test {
            assertEquals(UiState.Empty, awaitItem())
        }
    }

    @Test
    fun `Test error flow when receive a failure from repository`() = runTest {
        // GIVEN
        coEvery { repository.getCharacters() } answers {
            BusinessResult.Failure(BusinessException("Test Exception"))
        }
        val viewModel = getCharactersViewModel()

        // WHEN
        viewModel.loadCharacters()

        // THEN
        viewModel.state.test {
            assertEquals(UiState.Error, awaitItem())
        }
    }

    private fun getCharactersViewModel() = CharactersViewModel(
        repository = repository
    )

    private val character1 = DragonBallCharacter(
        id = 1,
        name = "Name 1",
        ki = "Ki 1",
        maxKi = "Max Ki 1",
        image = "Image 1",
        description = "Description 1",
        gender = Gender.FEMALE,
        race = Race.SAIYAN
    )

    private val character2 = DragonBallCharacter(
        id = 2,
        name = "Name 2",
        ki = "Ki 2",
        maxKi = "Max Ki 2",
        image = "Image 2",
        description = "Description 2",
        gender = Gender.MALE,
        race = Race.NAMEKIAN
    )
}
