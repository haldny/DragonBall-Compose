plugins {
    alias(libs.plugins.internal.plugin.application)
    alias(libs.plugins.internal.plugin.hilt)
}

android {
    namespace = "com.haldny.dragonball"
}

dependencies {
    implementation(project(":core:design"))
    implementation(project(":core:navigation"))
    implementation(project(":character-detail:view"))
    implementation(project(":characters:view"))
    implementation(libs.core.ktx)
    implementation(libs.lifecycle.runtime.ktx)
    implementation(libs.activity.compose)
    implementation(libs.ui)
    implementation(libs.ui.graphics)
    implementation(libs.ui.tooling.preview)
    implementation(libs.material3)
    implementation(libs.androidx.navigation.compose)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.espresso.core)
    androidTestImplementation(platform(libs.compose.bom))
    androidTestImplementation(libs.ui.test.junit4)
    debugImplementation(libs.ui.tooling)
    debugImplementation(libs.ui.test.manifest)
}