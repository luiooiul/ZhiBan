plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("org.jetbrains.kotlin.plugin.serialization")
    id("com.google.devtools.ksp")
}

android {
    namespace = "com.zhixue.lite.core.model"
    compileSdk = 33

    defaultConfig {
        minSdk = 21
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
}

dependencies {
    implementation(project(":core:common"))
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.5.1")
    implementation("androidx.room:room-ktx:2.5.1")
    ksp("androidx.room:room-compiler:2.5.1")
}