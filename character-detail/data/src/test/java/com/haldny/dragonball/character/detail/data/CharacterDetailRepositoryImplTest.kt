package com.haldny.dragonball.character.detail.data

import com.haldny.dragonball.character.detail.data.api.CharacterDetailApi
import com.haldny.dragonball.character.detail.data.mapper.toBusinessModel
import com.haldny.dragonball.character.detail.data.model.CharacterDetailResponse
import com.haldny.dragonball.core.business.BusinessResult
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.test.runTest
import org.junit.Test
import retrofit2.Response

class CharacterDetailRepositoryImplTest {

    private val api: CharacterDetailApi = mockk()
    private val characterDetailRepository = CharacterDetailRepositoryImpl(api)

    @Test
    fun `given a success response with an character, when get character detail is called, then a business result success value is returned`() = runTest {
        coEvery {
            api.getCharacterDetail(any())
        } returns Response.success(
            characterDetail
        )

        val result = characterDetailRepository.getCharacterDetail(1)

        with(result as BusinessResult.Success) {
            assertEquals(characterDetail.toBusinessModel(), data)
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

    private val characterDetail = CharacterDetailResponse(
        id = 1,
        name = "Name 1",
        ki = "Ki 1",
        maxKi = "Max Ki 1",
        image = "Image 1",
        description = "Description 1",
        gender = "Female",
        race = "Saiyan"
    )
}
