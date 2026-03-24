package com.haldny.dragonball.characters.view

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import app.cash.turbine.ReceiveTurbine
import app.cash.turbine.test
import com.haldny.dragonball.characters.domain.CharactersPage
import com.haldny.dragonball.characters.domain.CharactersPagingConfig.PAGE_LIMIT
import com.haldny.dragonball.characters.domain.CharactersRepository
import com.haldny.dragonball.testing.support.TestCoroutineRule
import com.haldny.dragonball.core.business.BusinessResult
import com.haldny.dragonball.core.business.exceptions.BusinessException
import com.haldny.dragonball.testing.fixtures.DomainTestFixtures
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
    fun `when first page succeeds with characters then state is Loaded`() = runTest {
        coEvery {
            repository.getCharactersPage(page = 1, limit = PAGE_LIMIT)
        } returns BusinessResult.Success(
            CharactersPage(
                items = listOf(
                    DomainTestFixtures.dragonBallCharacter1,
                    DomainTestFixtures.dragonBallCharacter2,
                ),
                hasNextPage = false,
            )
        )

        val viewModel = CharactersViewModel(repository = repository)
        val expected = CharactersUiState.Loaded(
            CharactersListContent(
                characters = listOf(
                    DomainTestFixtures.dragonBallCharacter1,
                    DomainTestFixtures.dragonBallCharacter2,
                ).toImmutableList(),
                hasNextPage = false,
                isAppending = false,
                nextPageToLoad = 2,
            )
        )

        viewModel.state.test {
            assertEquals(expected, awaitFinalListState())
        }
    }

    @Test
    fun `when first page is empty then state is Empty`() = runTest {
        coEvery {
            repository.getCharactersPage(page = 1, limit = PAGE_LIMIT)
        } returns BusinessResult.Success(
            CharactersPage(items = emptyList(), hasNextPage = false)
        )

        val viewModel = CharactersViewModel(repository = repository)

        viewModel.state.test {
            assertEquals(CharactersUiState.Empty, awaitFinalListState())
        }
    }

    @Test
    fun `when first page fails then state is Error`() = runTest {
        coEvery {
            repository.getCharactersPage(page = 1, limit = PAGE_LIMIT)
        } returns BusinessResult.Failure(BusinessException("Test Exception"))

        val viewModel = CharactersViewModel(repository = repository)

        viewModel.state.test {
            assertEquals(CharactersUiState.Error, awaitFinalListState())
        }
    }

    @Test
    fun `when Retry is sent after error then loading and result are emitted`() = runTest {
        coEvery {
            repository.getCharactersPage(page = 1, limit = PAGE_LIMIT)
        } returnsMany listOf(
            BusinessResult.Failure(BusinessException("Test Exception")),
            BusinessResult.Success(
                CharactersPage(
                    items = listOf(DomainTestFixtures.dragonBallCharacter1),
                    hasNextPage = false,
                )
            )
        )

        val viewModel = CharactersViewModel(repository = repository)
        val expected = CharactersUiState.Loaded(
            CharactersListContent(
                characters = listOf(DomainTestFixtures.dragonBallCharacter1).toImmutableList(),
                hasNextPage = false,
                isAppending = false,
                nextPageToLoad = 2,
            )
        )

        viewModel.state.test {
            while (true) {
                when (awaitItem()) {
                    is CharactersUiState.Error -> break
                    else -> Unit
                }
            }
            viewModel.onAction(CharactersUserAction.Retry)
            assertEquals(expected, awaitFinalListState())
        }
    }

    /**
     * Skips an optional leading [CharactersUiState.InitialLoading] because [StateFlow] may not
     * re-emit the same value when [CharactersViewModel] sets loading again.
     */
    private suspend fun ReceiveTurbine<CharactersUiState>.awaitFinalListState(): CharactersUiState {
        val first = awaitItem()
        if (first is CharactersUiState.InitialLoading) {
            return awaitItem()
        }
        return first
    }
}
