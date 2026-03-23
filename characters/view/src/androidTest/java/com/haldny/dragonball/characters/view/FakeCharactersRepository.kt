package com.haldny.dragonball.characters.view

import com.haldny.dragonball.characters.domain.CharactersPage
import com.haldny.dragonball.characters.domain.CharactersRepository
import com.haldny.dragonball.characters.domain.DragonBallCharacter
import com.haldny.dragonball.core.business.BusinessResult
import com.haldny.dragonball.core.business.exceptions.BusinessException
import kotlin.coroutines.suspendCoroutine

class FakeCharactersRepository(
    private val scenario: Scenario,
    private val characters: List<DragonBallCharacter> = emptyList(),
) : CharactersRepository {

    enum class Scenario {
        LoadingForever,
        Success,
        Empty,
        Error,
    }

    override suspend fun getCharactersPage(
        page: Int,
        limit: Int,
    ): BusinessResult<CharactersPage> {
        if (scenario == Scenario.LoadingForever) {
            suspendCoroutine<Unit> { }
        }
        return when (scenario) {
            Scenario.LoadingForever -> error("unreachable")
            Scenario.Success -> BusinessResult.Success(
                CharactersPage(items = characters, hasNextPage = false)
            )
            Scenario.Empty -> BusinessResult.Success(
                CharactersPage(items = emptyList(), hasNextPage = false)
            )
            Scenario.Error -> BusinessResult.Failure(BusinessException("Test error"))
        }
    }
}
