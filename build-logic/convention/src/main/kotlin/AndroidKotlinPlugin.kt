import com.android.build.api.dsl.LibraryExtension
import extensions.configureAndroidKotlin
import extensions.configureBuildTypes
import extensions.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies

class AndroidKotlinPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target){
            with(pluginManager) {
                apply("org.jetbrains.kotlin.android")
            }
            extensions.configure<LibraryExtension> {
                defaultConfig.targetSdk = libs.findVersion("target-sdk").get().toString().toInt()
                configureAndroidKotlin(this)
                configureBuildTypes(this)
            }
            // defaultConfig sets AndroidJUnitRunner; the runner must be on the androidTest classpath.
            dependencies {
                add("androidTestImplementation", libs.findLibrary("androidx-junit").get())
                add("androidTestImplementation", libs.findLibrary("espresso-core").get())
            }
        }
    }
}
