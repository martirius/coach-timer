object Versions {
    const val kotlin = "1.7.20"
    const val gradle = "7.3.0"
    const val hilt = "2.44"
}

object Plugins {
    object Linter {
        const val spotlessVersion = "6.11.0"
        const val ktlintVersion = "0.45.2"
        const val spotless = "com.diffplug.spotless"
    }

    object CodeAnalysis {
        const val detektVersion = "1.21.0"
        const val detekt = "io.gitlab.arturbosch.detekt"
    }

    object Gradle {
        const val androidGradlePlugin = "com.android.tools.build:gradle:${Versions.gradle}"
        const val kotlinGradlePlugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.kotlin}"
        const val kotlinxSerialization = "org.jetbrains.kotlin:kotlin-serialization:${Versions.kotlin}"
        const val hiltAndroidGradlePlugin = "com.google.dagger:hilt-android-gradle-plugin:${Versions.hilt}"
    }
}

object SupportLibs {
    const val coreKtx = "androidx.core:core-ktx:1.9.0"
    const val appCompat = "androidx.appcompat:appcompat:1.5.1"
    const val lifecycleViewModel = "androidx.lifecycle:lifecycle-viewmodel-ktx:2.5.1"
    const val lifecycleViewModelCompose = "androidx.lifecycle:lifecycle-viewmodel-compose:2.5.1"
}

object FrameworkLibs {

    object Compose {
        const val compilerVersion = "1.3.2"
        internal const val version = "1.2.1" // not private since used in app/build.gradle.kts composeOptions
        const val composeUi = "androidx.compose.ui:ui:$version"
        const val composeMaterial = "androidx.compose.material3:material3:1.0.0"
        const val composePreview = "androidx.compose.ui:ui-tooling-preview:$version"
        const val composeActivity = "androidx.activity:activity-compose:1.6.0"
        const val composeUiTooling = "androidx.compose.ui:ui-tooling:$version"
        const val composeNavigation = "androidx.navigation:navigation-compose:2.5.1"
        const val composeAnimation = "androidx.compose.animation:animation:1.3.0"
    }

    object Dagger {
        const val hilt = "com.google.dagger:hilt-android:${Versions.hilt}"
        const val daggerHilt = "com.google.dagger:hilt-android:${Versions.hilt}"
        const val daggerHiltAndroidCompiler = "com.google.dagger:hilt-android-compiler:${Versions.hilt}"
        const val daggerHiltCompiler = "com.google.dagger:hilt-compiler:${Versions.hilt}"
        const val androidxHiltCompiler = "androidx.hilt:hilt-compiler:1.0.0"
        const val daggerHiltTesting = "com.google.dagger:hilt-android-testing:${Versions.hilt}"
        const val hiltCompose = "androidx.hilt:hilt-navigation-compose:1.0.0"
    }
}

object TestLibs {
    const val junit = "junit:junit:4.13.2"
    const val junitExt = "androidx.test.ext:junit:1.1.3"
    const val espresso = "androidx.test.espresso:espresso-core:3.4.0"
    const val junitCompose = "androidx.compose.ui:ui-test-junit4:${FrameworkLibs.Compose.version}"
    const val coroutinesTest = "org.jetbrains.kotlinx:kotlinx-coroutines-test:1.6.4"
    const val mockito = "org.mockito.kotlin:mockito-kotlin:4.0.0"
}

object ExternalLibs {

    const val kotlinxSerialization = "org.jetbrains.kotlinx:kotlinx-serialization-json:1.4.1"

    object Ktor {
        private const val ktorVersion = "2.1.3"
        const val core = "io.ktor:ktor-client-core:$ktorVersion"
        const val engine = "io.ktor:ktor-client-cio:$ktorVersion"
        const val serialization = "io.ktor:ktor-serialization-kotlinx-json:$ktorVersion"
        const val contentNegotiation = "io.ktor:ktor-client-content-negotiation:$ktorVersion"
    }

    object WorkManager {
        private const val version = "2.7.1"
        const val runtime = "androidx.work:work-runtime-ktx:$version"
        const val testing = "androidx.work:work-testing:$version"
        const val hiltWork = "androidx.hilt:hilt-work:1.0.0"
    }

    object Landscapist {
        const val glide = "com.github.skydoves:landscapist-glide:2.0.3"
    }
}
