plugins {
    kotlin("multiplatform")
    id("org.jetbrains.compose") version "1.1.1"
    id("io.gitlab.arturbosch.detekt") version "1.19.0"
    id("org.jetbrains.kotlinx.kover") version "0.5.0"
}

group = "com.landry.digital.circuit.simulator.ui"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

kotlin {
    jvm()

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(project(":engine"))
            }
        }

        val commonTest by getting {
            dependencies {
                implementation(kotlin("test"))
            }
        }

        val composeMain by creating {
            dependsOn(commonMain)

            dependencies {
                implementation(project(":engine"))
                implementation(compose.ui)
                implementation(compose.material)
                //implementation(compose.material3)
                implementation(compose.materialIconsExtended)
                implementation(compose.uiTooling)
                implementation(compose.foundation)
                implementation(compose.preview)
                implementation(compose.runtime)
            }
        }

        val jvmMain by getting { dependsOn(composeMain) }
    }
}

tasks {
    koverMergedHtmlReport {
        isEnabled = true                        // false to disable report generation
        htmlReportDir.set(layout.buildDirectory.dir("report/html-result"))

        includes = listOf("com.landry.*")            // inclusion rules for classes
    }

    koverMergedXmlReport {
        isEnabled = true                        // false to disable report generation
        xmlReportFile.set(layout.buildDirectory.file("report/result.xml"))

        includes = listOf("com.landry.*")            // inclusion rules for classes
    }
}

kover {
    isDisabled = false
    coverageEngine.set(kotlinx.kover.api.CoverageEngine.INTELLIJ)
    generateReportOnCheck = true
}

detekt {
    source = files(
        "src/commonMain/kotlin"
    )
}
