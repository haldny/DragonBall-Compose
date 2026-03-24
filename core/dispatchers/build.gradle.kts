plugins {
    alias(libs.plugins.internal.plugin.library)
    alias(libs.plugins.internal.plugin.kotlin)
    alias(libs.plugins.internal.plugin.hilt)
}

android {
    namespace = "com.haldny.dragonball.dispatchers"
}

dependencies {
    implementation(libs.core.ktx)
    implementation(libs.hilt)
}
