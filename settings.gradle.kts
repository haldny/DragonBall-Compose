pluginManagement {
    includeBuild("build-logic")
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

rootProject.name = "DragonBall-Compose"
include(":app")
include(":core:network")
include(":characters:view")
include(":characters:domain")
include(":characters:data")
include(":character-detail:view")
include(":character-detail:domain")
include(":character-detail:data")
include(":core:business")
include(":core:design")
include(":core:navigation")
