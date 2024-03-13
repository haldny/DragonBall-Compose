import com.android.build.api.dsl.ApplicationExtension
import extensions.configureAndroidCompose
import extensions.configureBuildTypes
import extensions.libs
import org.gradle.api.JavaVersion
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

class AndroidApplicationComposePlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            pluginManager.apply{
                apply("com.android.application")
                apply("org.jetbrains.kotlin.android")
            }
            extensions.configure<ApplicationExtension> {
                compileSdk = libs.findVersion("compile-sdk").get().toString().toInt()
                defaultConfig.apply {
                    targetSdk = libs.findVersion("target-sdk").get().toString().toInt()
                    minSdk = libs.findVersion("min-sdk").get().toString().toInt()
                    versionCode = libs.findVersion("version-code").get().toString().toInt()
                    versionName = libs.findVersion("version-name").get().toString()
                    applicationId = libs.findVersion("application-id").get().toString()
                    multiDexEnabled = true
                }

                compileOptions {
                    sourceCompatibility = JavaVersion.VERSION_17
                    targetCompatibility = JavaVersion.VERSION_17
                }
                configureAndroidCompose(this)
                configureBuildTypes(this)
            }
            tasks.withType<KotlinCompile>().configureEach {
                kotlinOptions {
                    jvmTarget =  JavaVersion.VERSION_17.toString()
                }
            }
        }
    }
}
