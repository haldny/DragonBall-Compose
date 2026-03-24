plugins {
    alias(libs.plugins.internal.plugin.application)
    alias(libs.plugins.internal.plugin.hilt)
}

android {
    namespace = "com.haldny.dragonball"
    defaultConfig {
        testInstrumentationRunner = "com.haldny.dragonball.DragonBallHiltTestRunner"
    }
}

dependencies {
    implementation(projects.core.design)
    implementation(projects.core.navigation)
    implementation(projects.characterDetail.view)
    implementation(projects.characters.view)
    implementation(libs.core.ktx)
    implementation(libs.lifecycle.runtime.ktx)
    implementation(libs.activity.compose)
    implementation(libs.ui)
    implementation(libs.ui.graphics)
    implementation(libs.ui.tooling.preview)
    implementation(libs.material3)
    implementation(libs.androidx.navigation3.runtime)
    implementation(libs.androidx.navigation3.ui)
    testImplementation(libs.junit)
    androidTestImplementation(projects.testing)
    androidTestImplementation(projects.characters.domain)
    androidTestImplementation(projects.characters.data)
    androidTestImplementation(projects.characterDetail.domain)
    androidTestImplementation(projects.characterDetail.data)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.espresso.core)
    androidTestImplementation(libs.hilt.testing)
    androidTestImplementation(platform(libs.compose.bom))
    androidTestImplementation(libs.ui.test.junit4)
    kspAndroidTest(libs.hilt.compiler)
    debugImplementation(libs.ui.tooling)
    debugImplementation(libs.ui.test.manifest)
}