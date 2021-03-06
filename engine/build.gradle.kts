plugins {
    kotlin("multiplatform")
    id("io.gitlab.arturbosch.detekt") version "1.19.0"
    id("org.jetbrains.kotlinx.kover") version "0.5.0"
}

group = "com.landry.digital.circuit.simulator.engine"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

kotlin {
    jvm()
    linuxX64()

    sourceSets {
        val commonMain by getting {

        }

        val commonTest by getting {
            dependencies {
                implementation(kotlin("test"))
            }
        }
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
