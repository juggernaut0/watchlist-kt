import com.moowork.gradle.node.npm.NpmTask
import org.jetbrains.kotlin.gradle.tasks.Kotlin2JsCompile
import org.jetbrains.kotlin.gradle.tasks.KotlinJsDce

plugins {
    kotlin("js")
    id("kotlin-dce-js")
    id("com.github.node-gradle.node")
}

dependencies {
    implementation(project(":common"))
    implementation(project(":ui"))
    implementation(kotlin("stdlib-js"))
}

tasks.withType<Kotlin2JsCompile>().forEach {
    it.kotlinOptions.moduleKind = "commonjs"
}

tasks.getByName<KotlinJsDce>("runDceKotlin").keep("kotlin.defineModule")

tasks {
    val populateNodeModules by registering(Copy::class) {
        dependsOn("runDceKotlin")

        from(getByName<KotlinJsDce>("runDceKotlin").destinationDir)
        include("*.js", "*.js.map")
        into("$buildDir/node_modules")
    }

    val webpack by registering(NpmTask::class) {
        dependsOn(populateNodeModules, npmInstall)
        setArgs(listOf("run", "webpack"))
    }

    val webpackMin by registering(NpmTask::class) {
        dependsOn(populateNodeModules, npmInstall)
        setArgs(listOf("run", "webpackMin"))
    }
}
