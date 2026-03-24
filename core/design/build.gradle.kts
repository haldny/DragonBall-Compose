plugins {
    alias(libs.plugins.internal.plugin.library.compose)
    alias(libs.plugins.internal.plugin.kotlin)
}
android {
    namespace = "com.haldny.dragonball.design"
}

dependencies {
    implementation(platform(libs.compose.bom))
    implementation(libs.material.icons.extended)
    implementation(libs.coil.compose)
    implementation(libs.activity.compose)
    implementation(libs.ui)
    implementation(libs.ui.graphics)
    implementation(libs.ui.tooling.preview)
    implementation(libs.material3)
}