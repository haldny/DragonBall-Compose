package extensions

import com.android.build.gradle.LibraryExtension
import extensions.configureAndroidKotlin
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.getByType

class AndroidLibraryComposePlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            pluginManager.apply("com.android.library")

            val extension = extensions.getByType<LibraryExtension>()
            configureAndroidKotlin(extension)
        }
    }
}
