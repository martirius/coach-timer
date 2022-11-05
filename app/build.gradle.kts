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

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_11.toString()
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
    implementation(SupportLibs.lifecycleViewModel)
    implementation(SupportLibs.lifecycleViewModelCompose)
    implementation(SupportLibs.appCompat)

    // DI
    implementation(FrameworkLibs.Dagger.daggerHilt)
    kapt(FrameworkLibs.Dagger.daggerHiltAndroidCompiler)
    implementation(FrameworkLibs.Dagger.hiltCompose)

    // UI
    implementation(FrameworkLibs.Compose.composeUi)
    implementation(FrameworkLibs.Compose.composeMaterial)
    implementation(FrameworkLibs.Compose.composePreview)
    implementation(FrameworkLibs.Compose.composeActivity)
    implementation(FrameworkLibs.Compose.composeUiTooling)
    implementation(FrameworkLibs.Compose.composeNavigation)

    // Tests
    testImplementation(TestLibs.junit)
    testImplementation(TestLibs.coroutinesTest)
    testImplementation(TestLibs.mockito)
    androidTestImplementation(TestLibs.junitExt)
    androidTestImplementation(TestLibs.espresso)
    androidTestImplementation(TestLibs.junitCompose)

//    implementation(ExternalLibs.WorkManager.hiltWork)
//    kapt(FrameworkLibs.Dagger.androidxHiltCompiler)

    // Ktor
    implementation(ExternalLibs.Ktor.core)
    implementation(ExternalLibs.Ktor.engine)
    implementation(ExternalLibs.Ktor.serialization)
    implementation(ExternalLibs.Ktor.contentNegotiation)

    implementation(ExternalLibs.kotlinxSerialization)
    implementation(ExternalLibs.Landscapist.glide)
}
