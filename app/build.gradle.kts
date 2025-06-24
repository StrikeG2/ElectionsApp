plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.example.app_elections"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.app_elections"
        minSdk = 21
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    buildFeatures {
        dataBinding = true
        viewBinding = true
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

dependencies {
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    implementation(libs.room.common.jvm)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
    implementation (libs.appcompat.v141)
    implementation (libs.material.v150)
    implementation (libs.constraintlayout.v213)
    implementation (libs.fragment)
    implementation(libs.sqlite)
    implementation(libs.room.runtime)
    annotationProcessor (libs.room.compiler)
    implementation (libs.jbcrypt)
}