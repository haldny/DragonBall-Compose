plugins {
    alias(libs.plugins.internal.plugin.library)
    alias(libs.plugins.internal.plugin.kotlin)
    alias(libs.plugins.internal.plugin.hilt)
}

android {
    namespace = "com.haldny.dragonball.character.detail.data"
}

dependencies {
    implementation(project(":core:business"))
    implementation(project(":core:network"))
    implementation(project(":character-detail:domain"))
    implementation(libs.gson)
    testImplementation(libs.junit)
    testImplementation(libs.mockk)
    testImplementation(libs.androidx.junit)
    testImplementation(libs.kotlinx.coroutines.test)
}