package com.haldny.dragonball.architecture.test

import com.lemonappdev.konsist.api.Konsist
import com.lemonappdev.konsist.api.declaration.KoInterfaceDeclaration
import com.lemonappdev.konsist.api.ext.list.withNameEndingWith
import com.lemonappdev.konsist.api.verify.assertTrue
import io.kotest.core.spec.style.FunSpec

/**
 * MVVM + unidirectional flow conventions:
 * - ViewModels extend AndroidX [ViewModel] and live in presentation (`..view..`) packages.
 * - Screen state / intent / effect types (`*UiState`, `*UserAction`, `*UiEffect`, or feature `UiState`)
 *   are `sealed` and live in presentation.
 */
class MvvmStateActionEffectTest :
    FunSpec({
        val scope = Konsist.scopeFromExternalDirectories(productionKotlinRootPaths())

        test("classes named ViewModel extend androidx.lifecycle.ViewModel") {
            scope
                .classes()
                .withNameEndingWith("ViewModel")
                .assertTrue(testName = "inherit from ViewModel (by supertype name)") { koClass ->
                    // Use parents(), not parentClasses(): library supertypes may not be treated as Ko “class”.
                    koClass.hasParentWithName("ViewModel", indirectParents = true)
                }
        }

        test("ViewModel classes reside in presentation (view) packages") {
            scope
                .classes()
                .withNameEndingWith("ViewModel")
                .assertTrue { it.resideInPackage("..view..") }
        }

        test("types suffixed with UiState are sealed interfaces in presentation") {
            scope
                .interfaces(includeNested = false)
                .withNameEndingWith("UiState")
                .assertTrue { it.hasSealedModifier && it.resideInPackage("..view..") }
        }

        test("types suffixed with UserAction are sealed interfaces in presentation") {
            scope
                .interfaces(includeNested = false)
                .withNameEndingWith("UserAction")
                .assertTrue { it.hasSealedModifier && it.resideInPackage("..view..") }
        }

        test("types suffixed with UiEffect are sealed interfaces in presentation") {
            scope
                .interfaces(includeNested = false)
                .withNameEndingWith("UiEffect")
                .assertTrue { it.hasSealedModifier && it.resideInPackage("..view..") }
        }

        test("feature-level UiState in presentation is sealed (e.g. detail screen)") {
            scope
                .interfaces(includeNested = false)
                .filter { it.name == "UiState" }
                .assertTrue { ko: KoInterfaceDeclaration ->
                    ko.hasSealedModifier && ko.resideInPackage("..view..")
                }
        }
    })
