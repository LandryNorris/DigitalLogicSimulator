allprojects {
    repositories {
        mavenCentral()
    }
}

buildscript {
    val kotlinVersion: String by project
    dependencies {
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlinVersion")
        classpath("com.android.tools.build:gradle:7.0.0")
    }

    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}