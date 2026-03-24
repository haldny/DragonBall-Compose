package com.haldny.dragonball.character.detail.data

import com.haldny.dragonball.character.detail.data.api.CharacterDetailApi
import com.haldny.dragonball.character.detail.data.model.CharacterDetailResponse
import com.haldny.dragonball.core.business.BusinessResult
import com.haldny.dragonball.testing.fixtures.CharacterDetailApiTestFixtures
import com.haldny.dragonball.testing.fixtures.DomainTestFixtures
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
class CharacterDetailRepositoryImplTest {

    private val api: CharacterDetailApi = mockk()
    private val ioDispatcher = UnconfinedTestDispatcher()
    private val characterDetailRepository = CharacterDetailRepositoryImpl(api, ioDispatcher)

    @Test
    fun `given a success response with an character, when get character detail is called, then a business result success value is returned`() = runTest {
        coEvery {
            api.getCharacterDetail(any())
        } returns Response.success(
            CharacterDetailApiTestFixtures.characterDetailSuccess,
        )

        val result = characterDetailRepository.getCharacterDetail(1)

        with(result as BusinessResult.Success) {
            assertEquals(DomainTestFixtures.characterDetailMinimalMapped, data)
        }
    }

    @Test
    fun `given a failure response, when get character detail is called, then a business result with a failure value must be returned`() = runTest {
        val response = mockk<Response<CharacterDetailResponse>> {
            every { isSuccessful } returns false
            every { errorBody() } returns null
        }
        coEvery { api.getCharacterDetail(any()) } returns response

        val result = characterDetailRepository.getCharacterDetail(1)

        assertTrue(result is BusinessResult.Failure)
    }
}
