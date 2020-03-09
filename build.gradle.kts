plugins {
    kotlin("jvm") version "1.3.61" apply false
    id("com.github.node-gradle.node") version "2.2.1"
}

subprojects {
    version = "4"

    repositories {
        mavenCentral()
        maven("https://kotlin.bintray.com/kotlinx")
        jcenter()
        maven("https://juggernaut0.github.io/m2/repository")
        mavenLocal()
    }

    plugins.withId("com.github.node-gradle.node") {
        this@subprojects.node {
            download = true
            version = "12.16.0"
        }
    }
}