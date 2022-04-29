
rootProject.name = "DigitalLogicSimulator"

pluginManagement {
    repositories {
        gradlePluginPortal()
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    }
}

include(":engine")
include(":parser")
include(":simulator-ui")
include(":simulator-desktop")