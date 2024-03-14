package com.haldny.dragonball.character.detail.view

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import app.cash.turbine.test
import com.haldny.dragonball.character.detail.domain.CharacterDetailRepository
import com.haldny.dragonball.character.detail.domain.DragonBallCharacterDetail
import com.haldny.dragonball.character.detail.domain.Gender
import com.haldny.dragonball.character.detail.domain.OriginPlanet
import com.haldny.dragonball.character.detail.domain.Race
import com.haldny.dragonball.character.detail.support.TestCoroutineRule
import com.haldny.dragonball.core.business.BusinessResult
import com.haldny.dragonball.core.business.exceptions.BusinessException
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule

class CharacterDetailViewModelTest {

    @get:Rule
    val rule: TestRule = InstantTaskExecutorRule()

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    private val repository = mockk<CharacterDetailRepository>(relaxed = true)

    @Test
    fun `Test success flow when receive a dragon ball character`() = runTest {
        // GIVEN
        coEvery { repository.getCharacterDetail(any()) } answers {
            BusinessResult.Success(data = character1)
        }
        val viewModel = getCharacterDetailViewModel()

        // WHEN
        viewModel.getCharacterDetail(1)

        // THEN
        viewModel.state.test {
            assertEquals(UiState.Loaded(character1), awaitItem())
        }
    }

    @Test
    fun `Test error flow when receive a failure from repository`() = runTest {
        // GIVEN
        coEvery { repository.getCharacterDetail(any()) } answers {
            BusinessResult.Failure(BusinessException("Test Exception"))
        }
        val viewModel = getCharacterDetailViewModel()

        // WHEN
        viewModel.getCharacterDetail(1)

        // THEN
        viewModel.state.test {
            assertEquals(UiState.Error, awaitItem())
        }
    }

    private fun getCharacterDetailViewModel(id: Int = 1) = CharacterDetailViewModel(
        id,
        repository = repository
    )

    private val character1 = DragonBallCharacterDetail(
        id = 1,
        name = "Name 1",
        ki = "Ki 1",
        maxKi = "Max Ki 1",
        image = "Image 1",
        description = "Description 1",
        gender = Gender.FEMALE,
        race = Race.SAIYAN,
        originPlanet = OriginPlanet(
            id = 1,
            name = "Planet Name",
            isDestroyed = false,
            description = "Description",
            image = "Image"
        ),
        transformations = listOf(),
    )
}
