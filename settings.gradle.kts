include(":app")
include(":core:ui")
include(":core:data")
include(":core:datastore")
include(":core:model")
include(":core:common")
include(":core:domain")
include(":core:network")
include(":feature:home")
include(":feature:login")
include(":feature:report")
include(":feature:profile")

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