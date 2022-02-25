plugins {
    kotlin("multiplatform")
    id("org.jetbrains.kotlinx.kover") version "0.5.0"
}

group = "com.landry.digital.circuit.simulator"
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

        includes = listOf("com.example.*")            // inclusion rules for classes
        excludes = listOf("com.example.subpackage.*") // exclusion rules for classes
    }

    koverMergedXmlReport {
        isEnabled = true                        // false to disable report generation
        xmlReportFile.set(layout.buildDirectory.file("report/result.xml"))

        includes = listOf("com.example.*")            // inclusion rules for classes
        excludes = listOf("com.example.subpackage.*") // exclusion rules for classes
    }
}

kover {
    isDisabled = false
    coverageEngine.set(kotlinx.kover.api.CoverageEngine.INTELLIJ)
    generateReportOnCheck = true
}
