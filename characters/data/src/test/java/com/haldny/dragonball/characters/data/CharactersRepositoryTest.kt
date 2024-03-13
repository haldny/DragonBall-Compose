package com.haldny.dragonball.characters.data

import com.haldny.dragonball.characters.data.api.CharactersApi
import com.haldny.dragonball.characters.data.mapper.toBusinessModel
import com.haldny.dragonball.characters.data.model.Character
import com.haldny.dragonball.characters.data.model.CharactersResponse
import com.haldny.dragonball.core.business.BusinessResult
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.test.runTest
import org.junit.Test
import retrofit2.Response

class CharactersRepositoryTest {

    private val api: CharactersApi = mockk()
    private val charactersRepository = CharactersRepositoryImpl(api)

    @Test
    fun `given a success response with a valid response body, when get characters is called, then a business result success value is returned`() = runTest {
        coEvery {
            api.getCharacters()
        } returns Response.success(
            getCharactersResponse()
        )

        val result = charactersRepository.getCharacters()

        with(result as BusinessResult.Success) {
            assertEquals(2, data.size)
            assertEquals(character1.toBusinessModel(), data[0])
            assertEquals(character2.toBusinessModel(), data[1])
        }
    }

    @Test
    fun `given a success response with an empty list, when get characters is called, then a business result success value is returned with empty data`() = runTest {
        coEvery {
            api.getCharacters()
        } returns Response.success(
            getEmptyCharactersResponse()
        )

        val result = charactersRepository.getCharacters()

        with(result as BusinessResult.Success) {
            assertEquals(0, data.size)
        }
    }

    @Test
    fun `given a failure response, when get characters is called, then a business result with a failure value must be returned`() = runTest {
        val response = mockk<Response<CharactersResponse>> {
            every { isSuccessful } returns false
            every { errorBody() } returns null
        }
        coEvery { api.getCharacters() } returns response

        val result = charactersRepository.getCharacters()

        assertTrue(result is BusinessResult.Failure)
    }

    private fun getCharactersResponse() = CharactersResponse(
        characters = listOf(
            character1,
            character2
        )
    )

    private fun getEmptyCharactersResponse() = CharactersResponse(
        characters = listOf()
    )

    private val character1 = Character(
        id = 1,
        name = "Name 1",
        ki = "Ki 1",
        maxKi = "Max Ki 1",
        image = "Image 1",
        description = "Description 1",
        gender = "Female",
        race = "Saiyan"
    )

    private val character2 = Character(
        id = 2,
        name = "Name 2",
        ki = "Ki 2",
        maxKi = "Max Ki 2",
        image = "Image 2",
        description = "Description 2",
        gender = "Male",
        race = "Namekian"
    )

}
