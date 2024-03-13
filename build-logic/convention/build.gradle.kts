import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    `kotlin-dsl`
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}
tasks.withType<KotlinCompile>().configureEach {
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_17.toString()
    }
}

dependencies {
    implementation(libs.build.gradle.plugin)
    implementation(libs.kotlin.gradle.plugin)
}

gradlePlugin {
    plugins {
        register("androidApplication") {
            id = "internal.plugin.application"
            implementationClass = "AndroidApplicationComposePlugin"
        }

        register("androidHilt") {
            id = "internal.plugin.hilt"
            implementationClass = "AndroidHiltPlugin"
        }

        register("androidKotlin") {
            id = "internal.plugin.kotlin"
            implementationClass = "AndroidKotlinPlugin"
        }

        register("androidLibraryCompose") {
            id = "internal.plugin.library.compose"
            implementationClass = "AndroidLibraryComposePlugin"
        }

        register("androidLibrary") {
            id = "internal.plugin.library"
            implementationClass = "AndroidLibraryPlugin"
        }
    }
}
