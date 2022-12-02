plugins {
    kotlin("multiplatform")
    kotlin("plugin.serialization")
    id("dev.twarner.common")
}

kotlin {
    jvm()
    js {
        browser()
    }

    sourceSets {
        val commonMain by getting {
            dependencies {
                api(libs.multiplatformUtils)
            }
        }

        val jvmMain by getting {
            dependencies {
                api(libs.multiplatformUtils.ktor)
            }
        }

        val jsMain by getting {
            dependencies {
                api(libs.multiplatformUtils.ktor)
            }
        }

        val jsTest by getting {
            dependencies {
                implementation(kotlin("test-js"))
            }
        }
    }
}
