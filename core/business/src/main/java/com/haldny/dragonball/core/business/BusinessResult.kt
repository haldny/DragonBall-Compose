package com.haldny.dragonball.core.business

import com.haldny.dragonball.core.business.exceptions.BusinessException

sealed class BusinessResult<out T> {
    data class Success<T>(val data: T) : BusinessResult<T>()
    data class Failure(val exception: BusinessException) : BusinessResult<Nothing>()
}
