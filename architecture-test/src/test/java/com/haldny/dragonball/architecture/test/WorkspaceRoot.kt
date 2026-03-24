package com.haldny.dragonball.architecture.test

import java.io.File

/**
 * Resolves the Gradle workspace root when unit tests run with `user.dir` set to this module
 * (`architecture-test`) or to the repo root.
 */
internal fun gradleWorkspaceRoot(): File {
    var dir = File(System.getProperty("user.dir")!!).canonicalFile
    if (dir.name == "architecture-test") {
        dir = dir.parentFile!!
    }
    return dir
}

internal fun productionKotlinRootPaths(): List<String> {
    val root = gradleWorkspaceRoot()
    // Android convention in this repo: Kotlin under src/main/java (not kotlin/).
    return listOf(
        "core/business/src/main/java",
        "core/design/src/main/java",
        "core/dispatchers/src/main/java",
        "core/network/src/main/java",
        "core/navigation/src/main/java",
        "characters/domain/src/main/java",
        "characters/data/src/main/java",
        "characters/view/src/main/java",
        "character-detail/domain/src/main/java",
        "character-detail/data/src/main/java",
        "character-detail/view/src/main/java",
    ).map { root.resolve(it).absolutePath }
}
