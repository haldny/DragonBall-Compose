plugins {
    alias(libs.plugins.internal.plugin.library)
    alias(libs.plugins.internal.plugin.kotlin)
}

android {
    namespace = "com.haldny.dragonball.testing"
}

dependencies {
    implementation(project(":core:business"))
    implementation(project(":characters:domain"))
    implementation(project(":character-detail:domain"))
    implementation(project(":characters:data"))
    implementation(project(":character-detail:data"))
    implementation(libs.junit)
    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.kotlinx.coroutines.test)
}
