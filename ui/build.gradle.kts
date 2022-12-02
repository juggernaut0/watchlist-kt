import org.jetbrains.kotlin.gradle.tasks.Kotlin2JsCompile

plugins {
    kotlin("js")
    id("dev.twarner.common")
}

dependencies {
    api(project(":common"))

    api("org.jetbrains.kotlinx:kotlinx-coroutines-core-js:1.6.4")
    api(platform(libs.ktor.bom))
    api(libs.ktor.client.js)

    api(libs.kui)

    api(libs.twarner.auth.ui)
}

kotlin {
    js {
        browser()
    }
}

tasks.withType<Kotlin2JsCompile>().forEach {
    it.kotlinOptions.moduleKind = "commonjs"
}
