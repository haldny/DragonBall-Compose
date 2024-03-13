plugins {
    alias(libs.plugins.internal.plugin.library.compose)
    alias(libs.plugins.internal.plugin.kotlin)
}
android {
    namespace = "com.haldny.dragonball.design"
}

dependencies {
    implementation(libs.activity.compose)
    implementation(libs.ui)
    implementation(libs.ui.graphics)
    implementation(libs.ui.tooling.preview)
    implementation(libs.material3)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.espresso.core)
}