package com.haldny.dragonball.character.detail.view

import com.haldny.dragonball.character.detail.domain.CharacterDetailRepository
import com.haldny.dragonball.character.detail.domain.DragonBallCharacterDetail
import com.haldny.dragonball.core.business.BusinessResult
import com.haldny.dragonball.core.business.exceptions.BusinessException
import kotlin.coroutines.suspendCoroutine

class FakeCharacterDetailRepository(
    private val scenario: Scenario,
    private val character: DragonBallCharacterDetail? = null,
) : CharacterDetailRepository {

    enum class Scenario {
        LoadingForever,
        Success,
        Error,
    }

    override suspend fun getCharacterDetail(id: Int): BusinessResult<DragonBallCharacterDetail> {
        if (scenario == Scenario.LoadingForever) {
            suspendCoroutine<Unit> { }
        }
        return when (scenario) {
            Scenario.LoadingForever -> error("unreachable")
            Scenario.Success -> BusinessResult.Success(
                checkNotNull(character) { "Success scenario requires character" },
            )
            Scenario.Error -> BusinessResult.Failure(BusinessException("Test error"))
        }
    }
}
