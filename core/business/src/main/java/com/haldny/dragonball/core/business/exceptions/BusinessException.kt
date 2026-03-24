package com.haldny.dragonball.core.business.exceptions

open class BusinessException(
    override val message: String? = null,
    override val cause: Throwable? = null,
) : Exception(message, cause)
