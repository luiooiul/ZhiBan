plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.kapt")
    id("org.jetbrains.kotlin.android")
    id("com.google.dagger.hilt.android")
}

android {
    namespace = "com.zhixue.lite"
    compileSdk = 33

    defaultConfig {
        applicationId = "com.zhixue.lite"
        minSdk = 21
        targetSdk = 33
        versionCode = 5
        versionName = "1.0-beta01"

        resourceConfigurations.add("en")
    }

    packaging {
        resources.excludes += setOf(
            "kotlin/**",
            "google/**",
            "okhttp3/**",
            "META-INF/*.version",
            "DebugProbesKt.bin",
            "kotlin-tooling-metadata.json"
        )
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

    buildFeatures {
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "1.4.6"
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
}

dependencies {
    implementation(project(":core:ui"))
    implementation(project(":core:data"))
    implementation(project(":core:model"))
    implementation(project(":feature:home"))
    implementation(project(":feature:login"))
    implementation(project(":feature:report"))
    implementation(project(":feature:profile"))
    implementation(project(":feature:modify"))
    implementation("androidx.core:core-splashscreen:1.0.1")
    implementation("androidx.activity:activity-compose:1.7.1")
    implementation("androidx.navigation:navigation-compose:2.5.3")
    implementation("com.google.accompanist:accompanist-systemuicontroller:0.30.1")
    implementation("com.google.dagger:hilt-android:2.45")
    kapt("com.google.dagger:hilt-android-compiler:2.45")
}