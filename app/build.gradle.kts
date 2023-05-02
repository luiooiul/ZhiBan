plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "com.zhixue.lite"
    compileSdk = 33

    defaultConfig {
        applicationId = "com.zhixue.lite"
        minSdk = 21
        targetSdk = 33
        versionCode = 1
        versionName = "1.0-alpha1"
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
}