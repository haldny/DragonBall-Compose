import io.gitlab.arturbosch.detekt.Detekt
import io.gitlab.arturbosch.detekt.extensions.DetektExtension
import org.gradle.api.JavaVersion

plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.hilt) apply false
    alias(libs.plugins.ksp) apply false
    alias(libs.plugins.org.jetbrains.kotlin.jvm) apply false
    alias(libs.plugins.detekt) apply false
}

task("clean") {
    delete(layout.buildDirectory)
}

val detektPluginId = libs.plugins.detekt.get().pluginId

subprojects {
    apply(plugin = detektPluginId)

    extensions.configure<DetektExtension>("detekt") {
        buildUponDefaultConfig = true
        parallel = true
        config.setFrom(files("$rootDir/config/detekt/detekt.yml"))
    }

    tasks.withType<Detekt>().configureEach {
        jvmTarget = JavaVersion.VERSION_17.toString()
        reports {
            html.required.set(true)
            xml.required.set(true)
        }
    }

    afterEvaluate {
        val detekt = tasks.findByName("detekt")
        val check = tasks.findByName("check")
        if (detekt != null && check != null) {
            check.dependsOn(detekt)
        }
    }
}
