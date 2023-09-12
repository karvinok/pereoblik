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

rootProject.name = "pereoblik"

include(":app")

include(":core:navigation")
include(":core:ui")
include(":core:domain")
include(":core:data")

include(":feature:home")
include(":feature:documents")
include(":feature:document-edit")
