plugins {
    kotlin("multiplatform")
    kotlin("plugin.compose")
    id("org.jetbrains.compose")
    id("io.gitlab.arturbosch.detekt")
    id("org.jetbrains.kotlinx.kover")
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

detekt {
    source = files(
        "src/commonMain/kotlin"
    )
}
