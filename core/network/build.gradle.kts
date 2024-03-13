plugins {
    alias(libs.plugins.internal.plugin.library)
    alias(libs.plugins.internal.plugin.kotlin)
    alias(libs.plugins.internal.plugin.hilt)
}

android {
    namespace = "com.haldny.dragonball.network"
}

dependencies {
    implementation(project(":core:business"))
    implementation(libs.retrofit)
    implementation(libs.gson)
    implementation(libs.okhttp.logging)
}