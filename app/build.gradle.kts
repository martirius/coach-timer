plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("kapt")
    id("dagger.hilt.android.plugin")
    id("kotlinx-serialization")
}

android {
    compileSdk = Sdk.COMPILE_SDK
    namespace = "pini.mattia.coachtimer"

    defaultConfig {
        applicationId = "pini.mattia.coachtimer"
        minSdk = Sdk.MIN_SDK
        targetSdk = Sdk.TARGET_SDK

        versionCode = 1
        versionName = "0.0.1"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = true
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
        kotlinCompilerExtensionVersion = FrameworkLibs.Compose.compilerVersion
    }

    // Allow references to generated code
    kapt {
        correctErrorTypes = true
    }

    hilt {
        enableAggregatingTask = true
    }
}

dependencies {

    // Base
    implementation(SupportLibs.coreKtx)
    implementation(SupportLibs.lifecycleKtx)
    implementation(SupportLibs.appCompat)

    // DI
    implementation(FrameworkLibs.Dagger.daggerHilt)
    kapt(FrameworkLibs.Dagger.daggerHiltAndroidCompiler)

    // UI
    implementation(FrameworkLibs.Compose.composeUi)
    implementation(FrameworkLibs.Compose.composeMaterial)
    implementation(FrameworkLibs.Compose.composePreview)
    implementation(FrameworkLibs.Compose.composeActivity)

    // Tests
    testImplementation(TestLibs.junit)
    androidTestImplementation(TestLibs.junitExt)
    androidTestImplementation(TestLibs.espresso)
    androidTestImplementation(TestLibs.junitCompose)
    debugImplementation(FrameworkLibs.Compose.composeUiTooling)

    implementation(ExternalLibs.WorkManager.hiltWork)
    kapt(FrameworkLibs.Dagger.androidxHiltCompiler)

    // Ktor
    implementation(ExternalLibs.Ktor.core)
    implementation(ExternalLibs.Ktor.engine)
    implementation(ExternalLibs.Ktor.serialization)
    implementation(ExternalLibs.Ktor.contentNegotiation)

    implementation(ExternalLibs.kotlinxSerialization)
}
