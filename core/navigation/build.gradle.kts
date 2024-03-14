plugins {
    alias(libs.plugins.internal.plugin.library)
    alias(libs.plugins.internal.plugin.kotlin)
}

android {
    namespace = "com.haldny.dragonball.core.navigation"
}

dependencies {
    implementation(libs.androidx.navigation.runtime.ktx)
}
