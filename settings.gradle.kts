pluginManagement {
    repositories {
        mavenLocal()
        gradlePluginPortal()
        mavenCentral()
        maven("https://juggernaut0.github.io/m2/repository")
    }

    plugins {
        val twarnerPlugins = "0.3.1"
        id("dev.twarner.common") version twarnerPlugins
        id("dev.twarner.docker") version twarnerPlugins
        id("dev.twarner.kotlin") version twarnerPlugins

        val kotlinVersion = "1.7.22"
        kotlin("js") version kotlinVersion
        kotlin("jvm") version kotlinVersion
        kotlin("kapt") version kotlinVersion
        kotlin("multiplatform") version kotlinVersion
        kotlin("plugin.serialization") version kotlinVersion
    }
}

dependencyResolutionManagement {
    repositories {
        mavenLocal()
        maven("https://juggernaut0.github.io/m2/repository")
    }

    versionCatalogs {
        create("libs") {
            from("dev.twarner:catalog:0.3.1")
        }
    }
}

rootProject.name = "watchlist"
include("common")
include("dbmigrate")
include("electron", "electron:renderer")
include("service")
include("ui")
include("web")
