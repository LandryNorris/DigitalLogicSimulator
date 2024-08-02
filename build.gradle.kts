plugins {
    id("org.jetbrains.dokka") version "1.9.20"
    id("org.jetbrains.kotlinx.kover") version "0.6.0"
    kotlin("plugin.compose") version "2.0.0" apply false
    id("org.jetbrains.compose") version "1.6.10" apply false
    id("io.gitlab.arturbosch.detekt") version "1.23.6" apply false
}

buildscript {
    val kotlinVersion: String by project
    dependencies {
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlinVersion")
        classpath("com.android.tools.build:gradle:8.5.0")
    }

    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}

allprojects {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
        maven { url = uri("https://maven.pkg.jetbrains.space/public/p/compose/dev") }
        maven { url = uri("https://maven.pkg.jetbrains.space/public/p/ktor/eap") }
    }
}

tasks {
    dokkaGfmMultiModule {
        outputDirectory.set(rootDir.resolve("docs/generated"))
    }
}

koverMerged {
    enable()

    filters {
        classes {
            includes += listOf("com.landry.*")
            excludes += listOf("*.BuildConfig")
        }
        projects {
            excludes += listOf()
        }
    }

    htmlReport {
        onCheck.set(true)
        reportDir.set(File(buildDir, "test/report/html"))
    }

    xmlReport {
        onCheck.set(true)
        reportFile.set(File(buildDir, "test/report/xml/report.xml"))
    }

    verify {
        onCheck.set(true)

        rule {
            isEnabled = true
            name = "coverage"
            target = kotlinx.kover.api.VerificationTarget.ALL

            bound { // add rule bound
                minValue = 10
                counter = kotlinx.kover.api.CounterType.LINE // change coverage metric to evaluate (LINE, INSTRUCTION, BRANCH)
                valueType = kotlinx.kover.api.VerificationValueType.COVERED_PERCENTAGE // change counter value (COVERED_COUNT, MISSED_COUNT, COVERED_PERCENTAGE, MISSED_PERCENTAGE)
            }
        }
    }
}
