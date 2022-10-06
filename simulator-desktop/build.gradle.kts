import org.jetbrains.compose.desktop.application.dsl.*
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

val decomposeVersion: String by project

plugins {
    kotlin("jvm")
    id("org.jetbrains.compose") version "1.2.0-beta03"
    id("io.gitlab.arturbosch.detekt") version "1.19.0"
    id("org.jetbrains.kotlinx.kover")
}

group = "com.landry.digital.logic.simulator.desktop"
version = "1.0"

repositories {
    mavenCentral()
    google()
    maven { url = uri("https://maven.pkg.jetbrains.space/public/p/compose/dev") }
}

dependencies {
    implementation(compose.desktop.currentOs)
    implementation(project(":simulator-ui"))
    implementation(project(":engine"))
    implementation("com.arkivanov.decompose:decompose:$decomposeVersion")
    implementation("com.arkivanov.decompose:extensions-compose-jetbrains:$decomposeVersion")
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "11"
    kotlinOptions.freeCompilerArgs += "-Xopt-in=kotlin.RequiresOptIn"
}

compose.desktop {
    application {
        mainClass = "com.landry.digital.logic.simulator.desktop.MainKt"
        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "Digital Logic Simulator"
            packageVersion = "1.0.0"
        }
    }
}
