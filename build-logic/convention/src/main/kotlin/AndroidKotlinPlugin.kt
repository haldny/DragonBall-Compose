import com.android.build.api.dsl.LibraryExtension
import extensions.configureAndroidKotlin
import extensions.configureBuildTypes
import extensions.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure

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
        }
    }
}
