import org.jetbrains.kotlin.gradle.tasks.Kotlin2JsCompile

plugins {
    kotlin("js")
}

dependencies {
    compile(project(":common"))

    compile(kotlin("stdlib-js"))
    compile("org.jetbrains.kotlinx:kotlinx-coroutines-core-js:1.1.1")
    compile("io.ktor:ktor-client-js:1.2.6")

    compile("com.github.juggernaut0.kui:kui:0.10.0")

    compile("dev.twarner.auth:auth-ui:1")
}

tasks.withType<Kotlin2JsCompile>().forEach {
    //it.kotlinOptions.freeCompilerArgs += "-Xuse-experimental=kotlin.Experimental"
    it.kotlinOptions.moduleKind = "commonjs"
}
