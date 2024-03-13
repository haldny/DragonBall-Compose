plugins {
    alias(libs.plugins.internal.plugin.library)
    alias(libs.plugins.internal.plugin.kotlin)
    alias(libs.plugins.internal.plugin.hilt)
}

android {
    namespace = "com.haldny.dragonball.characters.domain"
}

dependencies {
    implementation(project(":core:business"))
    testImplementation(libs.junit)
}
