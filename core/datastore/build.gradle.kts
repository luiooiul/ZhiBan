import com.google.protobuf.gradle.id

plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.kapt")
    id("org.jetbrains.kotlin.android")
    id("com.google.protobuf")
    id("com.google.dagger.hilt.android")
}

android {
    namespace = "com.zhixue.lite.core.datastore"
    compileSdk = 33

    defaultConfig {
        minSdk = 21
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
}

protobuf {
    protoc {
        artifact = "com.google.protobuf:protoc:3.22.3"
    }

    generateProtoTasks {
        all().forEach { task ->
            task.builtins {
                id("java") {
                    option("lite")
                }
                id("kotlin") {
                    option("lite")
                }
            }
        }
    }
}

dependencies {
    implementation(project(":core:model"))
    implementation("androidx.datastore:datastore:1.0.0")
    implementation("com.google.protobuf:protobuf-kotlin-lite:3.22.3")
    implementation("com.google.dagger:hilt-android:2.46.1")
    kapt("com.google.dagger:hilt-android-compiler:2.46.1")
}