plugins {
    alias(libs.plugins.internal.plugin.library.compose)
    alias(libs.plugins.internal.plugin.kotlin)
    alias(libs.plugins.internal.plugin.hilt)
}

android {
    namespace = "com.haldny.dragonball.character.detail.view"
    defaultConfig {
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
}

dependencies {
    implementation(platform(libs.compose.bom))
    implementation(libs.material.icons.extended)
    implementation(projects.core.business)
    implementation(projects.core.design)
    implementation(projects.characterDetail.data)
    implementation(projects.characterDetail.domain)
    implementation(libs.activity.compose)
    implementation(libs.ui)
    implementation(libs.ui.graphics)
    implementation(libs.ui.tooling.preview)
    implementation(libs.material3)
    implementation(libs.core.ktx)
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.kotlinx.collections.immutable)
    implementation(libs.hilt.compose)
    implementation(libs.lifecycle.runtime.compose)
    implementation(libs.coil.compose)
    testImplementation(libs.junit)
    testImplementation(libs.mockk)
    testImplementation(libs.turbine)
    testImplementation(libs.androidx.junit)
    testImplementation(libs.androidx.core.testing)
    testImplementation(libs.kotlinx.coroutines.test)
    testImplementation(projects.testing)
    androidTestImplementation(projects.testing)
    androidTestImplementation(platform(libs.compose.bom))
    androidTestImplementation(libs.ui.test.junit4)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.espresso.core)
    debugImplementation(libs.ui.test.manifest)
}
