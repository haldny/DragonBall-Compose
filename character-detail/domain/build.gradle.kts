plugins {
    alias(libs.plugins.internal.plugin.library)
    alias(libs.plugins.internal.plugin.kotlin)
}

android {
    namespace = "com.haldny.dragonball.character.detail.domain"
}

dependencies {
    implementation(project(":core:business"))
}
