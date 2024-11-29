plugins {
    kotlin("multiplatform")
    id("org.jetbrains.kotlinx.kover")
    id("io.gitlab.arturbosch.detekt")
}

group = "com.landry.digital.circuit.simulator.parser"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

kotlin {
    jvm()
    linuxX64()

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(project(":engine"))
                implementation("com.squareup.okio:okio:3.9.0")
            }
        }

        val commonTest by getting {
            dependencies {
                implementation(kotlin("test"))
                implementation("com.squareup.okio:okio-fakefilesystem:3.9.0")
            }
        }
    }
}

detekt {
    source.setFrom("src/commonMain/kotlin")
}
