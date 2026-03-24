plugins {
    alias(libs.plugins.internal.plugin.library)
    alias(libs.plugins.internal.plugin.kotlin)
}

android {
    namespace = "com.haldny.dragonball.testing"
}

dependencies {
    implementation(projects.core.business)
    implementation(projects.characters.domain)
    implementation(projects.characterDetail.domain)
    implementation(projects.characters.data)
    implementation(projects.characterDetail.data)
    implementation(libs.junit)
    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.kotlinx.coroutines.test)
}
