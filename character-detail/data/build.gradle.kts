plugins {
    alias(libs.plugins.internal.plugin.library)
    alias(libs.plugins.internal.plugin.kotlin)
    alias(libs.plugins.internal.plugin.hilt)
}

android {
    namespace = "com.haldny.dragonball.character.detail.data"
}

dependencies {
    implementation(projects.core.dispatchers)
    implementation(projects.core.business)
    implementation(projects.core.network)
    implementation(projects.characterDetail.domain)
    implementation(libs.gson)
    testImplementation(projects.testing)
    testImplementation(libs.junit)
    testImplementation(libs.mockk)
    testImplementation(libs.androidx.junit)
    testImplementation(libs.kotlinx.coroutines.test)
}