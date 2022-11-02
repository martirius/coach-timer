plugins {
    id(Plugins.Linter.spotless) version Plugins.Linter.spotlessVersion
    id(Plugins.CodeAnalysis.detekt) version Plugins.CodeAnalysis.detektVersion
}

buildscript {
    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        classpath(Plugins.Gradle.androidGradlePlugin)
        classpath(Plugins.Gradle.kotlinGradlePlugin)
        classpath(Plugins.Gradle.kotlinxSerialization)
        classpath(Plugins.Gradle.hiltAndroidGradlePlugin)
    }
}

subprojects {

    apply(plugin = Plugins.Linter.spotless)
    spotless {
        kotlin {

            // Check spotless repo before changing ktlint version
            // https://github.com/diffplug/spotless/tree/main/plugin-gradle#ktlint
            ktlint(Plugins.Linter.ktlintVersion)
                .setUseExperimental(true)

            // by default the target is every '.kt' and '.kts` file in the java sourcesets
            target("**/*.kt")
            targetExclude("$buildDir/**/*.kt")
            targetExclude("bin/**/*.kt")
            trimTrailingWhitespace()
            indentWithSpaces()
            endWithNewline()
        }
    }

    apply(plugin = Plugins.CodeAnalysis.detekt)
    detekt {
        buildUponDefaultConfig = true // preconfigure defaults
        allRules = false // activate all available (even unstable) rules.
        config = rootProject.files("detekt/detekt-config.yml")
    }
}
