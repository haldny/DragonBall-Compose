package com.haldny.dragonball.characters.data

import com.haldny.dragonball.characters.data.api.CharactersApi
import com.haldny.dragonball.characters.data.mapper.toBusinessModel
import com.haldny.dragonball.characters.data.model.CharactersResponse
import com.haldny.dragonball.characters.domain.CharactersPage
import com.haldny.dragonball.core.business.BusinessResult
import com.haldny.dragonball.testing.fixtures.CharactersApiTestFixtures
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Test
import retrofit2.Response

@OptIn(ExperimentalCoroutinesApi::class)
class CharactersRepositoryTest {

    private val api: CharactersApi = mockk()
    private val ioDispatcher = UnconfinedTestDispatcher()
    private val charactersRepository = CharactersRepositoryImpl(api, ioDispatcher)

    @Test
    fun `given a success response with a valid response body, when get characters page is called, then a business result success value is returned`() = runTest {
        coEvery {
            api.getCharacters(page = 1, limit = 20)
        } returns Response.success(
            CharactersApiTestFixtures.charactersResponseTwoItems(),
        )

        val result = charactersRepository.getCharactersPage(page = 1, limit = 20)

        with(result as BusinessResult.Success) {
            assertEquals(2, data.items.size)
            assertEquals(false, data.hasNextPage)
            assertEquals(
                CharactersApiTestFixtures.characterDto1.toBusinessModel(),
                data.items[0],
            )
            assertEquals(
                CharactersApiTestFixtures.characterDto2.toBusinessModel(),
                data.items[1],
            )
        }
    }

    @Test
    fun `given a success response with an empty list, when get characters page is called, then success has empty items and no next page`() = runTest {
        coEvery {
            api.getCharacters(page = 1, limit = 20)
        } returns Response.success(
            CharactersApiTestFixtures.charactersResponseEmpty(),
        )

        val result = charactersRepository.getCharactersPage(page = 1, limit = 20)

        with(result as BusinessResult.Success<CharactersPage>) {
            assertEquals(0, data.items.size)
            assertEquals(false, data.hasNextPage)
        }
    }

    @Test
    fun `given a failure response, when get characters page is called, then a business result with a failure value must be returned`() = runTest {
        val response = mockk<Response<CharactersResponse>> {
            every { isSuccessful } returns false
            every { errorBody() } returns null
        }
        coEvery { api.getCharacters(page = 1, limit = 20) } returns response

        val result = charactersRepository.getCharactersPage(page = 1, limit = 20)

        assertTrue(result is BusinessResult.Failure)
    }
}
