include(":app")
include(":core:ui")
include(":core:common")
include(":core:data")
include(":core:database")
include(":core:datastore")
include(":core:model")
include(":core:domain")
include(":core:network")
include(":feature:home")
include(":feature:login")
include(":feature:report")
include(":feature:profile")
include(":feature:modify")

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