package com.haldny.dragonball

import android.app.Application
import android.content.Context
import androidx.test.runner.AndroidJUnitRunner
import dagger.hilt.android.testing.HiltTestApplication

/**
 * Swaps in [HiltTestApplication] for instrumented tests (see Hilt instrumentation testing docs).
 * Hilt 2.x no longer ships a separate `HiltTestRunner` class in `hilt-android-testing`.
 */
class DragonBallHiltTestRunner : AndroidJUnitRunner() {
    override fun newApplication(cl: ClassLoader?, className: String?, context: Context?): Application {
        return super.newApplication(cl, HiltTestApplication::class.java.name, context)
    }
}
