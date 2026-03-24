package com.haldny.dragonball

import androidx.annotation.StringRes
import androidx.test.platform.app.InstrumentationRegistry

internal fun testString(@StringRes id: Int): String =
    InstrumentationRegistry.getInstrumentation().targetContext.getString(id)
