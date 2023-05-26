plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.kapt")
    id("org.jetbrains.kotlin.android")
    id("com.google.dagger.hilt.android")
}

android {
    namespace = "com.zhixue.lite.core.domain"
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
    implementation(project(":core:data"))
    implementation(project(":core:model"))
    implementation("androidx.paging:paging-compose:1.0.0-alpha20")
    implementation("com.google.dagger:hilt-android:2.46.1")
    kapt("com.google.dagger:hilt-android-compiler:2.46.1")
}