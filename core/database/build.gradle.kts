plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.kapt")
    id("org.jetbrains.kotlin.android")
    id("com.google.devtools.ksp")
    id("com.google.dagger.hilt.android")
}

android {
    namespace = "com.zhixue.lite.core.database"
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
    implementation("androidx.room:room-ktx:2.5.1")
    implementation("com.google.dagger:hilt-android:2.45")
    ksp("androidx.room:room-compiler:2.5.1")
    kapt("com.google.dagger:hilt-android-compiler:2.45")
}