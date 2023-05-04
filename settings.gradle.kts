include(":app")
include(":core:ui")
include(":core:model")
include(":core:domain")
include(":feature:login")

pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
    }
}