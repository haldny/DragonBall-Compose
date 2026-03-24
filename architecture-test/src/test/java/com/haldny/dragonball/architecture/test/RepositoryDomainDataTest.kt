package com.haldny.dragonball.architecture.test

import com.lemonappdev.konsist.api.Konsist
import com.lemonappdev.konsist.api.ext.list.withNameEndingWith
import com.lemonappdev.konsist.api.verify.assertTrue
import io.kotest.core.spec.style.FunSpec

/**
 * Clean Architecture repository placement:
 * - Port: `*Repository` interfaces live in the domain layer.
 * - Adapter: `*RepositoryImpl` classes live in the data layer.
 */
class RepositoryDomainDataTest :
    FunSpec({
        val scope = Konsist.scopeFromExternalDirectories(productionKotlinRootPaths())

        test("Repository interfaces are declared in the domain layer") {
            scope
                .interfaces(includeNested = false)
                .withNameEndingWith("Repository")
                .assertTrue(testName = "reside under ..domain..") {
                    it.resideInPackage("..domain..")
                }
        }

        test("Repository implementations are classes in the data layer") {
            scope
                .classes()
                .withNameEndingWith("RepositoryImpl")
                .assertTrue(testName = "reside under ..data..") {
                    it.resideInPackage("..data..")
                }
        }
    })
