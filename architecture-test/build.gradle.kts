plugins {
    alias(libs.plugins.internal.plugin.library)
    alias(libs.plugins.internal.plugin.kotlin)
}

android {
    namespace = "com.haldny.dragonball.architecture.test"
    defaultConfig {
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
    testOptions {
        unitTests.all { test ->
            test.useJUnitPlatform()
        }
    }
}

dependencies {
    testImplementation(libs.kotest.runner.junit5)
    testImplementation(libs.junit.jupiter.api)
    testRuntimeOnly(libs.junit.jupiter.engine)
    testImplementation(libs.konsist)

    testImplementation(projects.core.business)
    testImplementation(projects.core.design)
    testImplementation(projects.core.dispatchers)
    testImplementation(projects.core.network)
    testImplementation(projects.core.navigation)
    testImplementation(projects.characters.domain)
    testImplementation(projects.characters.data)
    testImplementation(projects.characters.view)
    testImplementation(projects.characterDetail.domain)
    testImplementation(projects.characterDetail.data)
    testImplementation(projects.characterDetail.view)
}
