import org.jetbrains.kotlin.gradle.tasks.Kotlin2JsCompile

plugins {
    kotlin("multiplatform")
    kotlin("plugin.serialization").version("1.3.61")
}

kotlin {
    jvm()
    js()

    sourceSets {
        val multiplatformUtilsVersion = "0.2.0"

        val commonMain by getting {
            dependencies {
                api("com.github.juggernaut0:multiplatform-utils:$multiplatformUtilsVersion")
                api("org.jetbrains.kotlinx:kotlinx-serialization-runtime-common:0.14.0")
            }
        }

        val jvmMain by getting {
            dependencies {
                api("com.github.juggernaut0:multiplatform-utils-ktor-jvm:$multiplatformUtilsVersion")
                api("org.jetbrains.kotlinx:kotlinx-serialization-runtime:0.14.0")
            }
        }

        val jsMain by getting {
            dependencies {
                api("com.github.juggernaut0:multiplatform-utils-ktor-js:$multiplatformUtilsVersion")
                api("org.jetbrains.kotlinx:kotlinx-serialization-runtime-js:0.14.0")
            }
        }

        val jsTest by getting {
            dependencies {
                implementation(kotlin("test-js"))
            }
        }
    }
}

tasks.withType<Kotlin2JsCompile>().forEach {
    it.kotlinOptions.moduleKind = "umd"
}
