package com.haldny.dragonball.architecture.test

import com.lemonappdev.konsist.api.Konsist
import com.lemonappdev.konsist.api.architecture.KoArchitectureCreator
import com.lemonappdev.konsist.api.architecture.Layer
import io.kotest.core.spec.style.FunSpec

/**
 * Enforces layer boundaries aligned with Clean Architecture:
 * - Domain depends on Core (shared primitives / [com.haldny.dragonball.core.business.BusinessResult]).
 * - Data depends on Domain + Core (never on Presentation).
 * - Presentation (`*.view`) may depend on Domain, Core, and Data (Data is present on the classpath for
 *   Hilt-wired repositories in this project).
 * Note: Konsist 0.17.x `dependsOn` must use a single `strict` value for all rules (library quirk);
 * this project uses `strict = false` (allowed dependencies), not “must depend on every layer”.
 * - Domain must not depend on Data or Presentation.
 */
class CleanArchitectureLayersTest :
    FunSpec({
        test("clean architecture layer dependencies hold for production sources") {
            val scope = Konsist.scopeFromExternalDirectories(productionKotlinRootPaths())

            with(KoArchitectureCreator) {
                scope.assertArchitecture {
                    val core = Layer("Core", "com.haldny.dragonball.core..")
                    val domain = Layer("Domain", "..domain..")
                    val data = Layer("Data", "..data..")
                    val presentation = Layer("Presentation", "..view..")

                    domain.dependsOn(core, strict = false)
                    data.dependsOn(domain, core, strict = false)
                    presentation.dependsOn(domain, core, data, strict = false)

                    domain.doesNotDependOn(data, presentation)
                    data.doesNotDependOn(presentation)
                }
            }
        }
    })
