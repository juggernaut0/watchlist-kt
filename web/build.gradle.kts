import com.moowork.gradle.node.npm.NpmTask
import org.jetbrains.kotlin.gradle.tasks.Kotlin2JsCompile
import org.jetbrains.kotlin.gradle.tasks.KotlinJsDce

plugins {
    id("kotlin2js")
    id("kotlin-dce-js")
    id("com.moowork.node")
}

dependencies {
    implementation(project(":ui"))
    implementation(kotlin("stdlib-js"))
}

tasks.withType<Kotlin2JsCompile>().forEach {
    it.kotlinOptions.moduleKind = "umd"
}

tasks.getByName<KotlinJsDce>("runDceKotlinJs").keep("kotlin.defineModule")

tasks {
    val populateNodeModules by registering(Copy::class) {
        dependsOn("runDceKotlinJs")

        from(getByName<KotlinJsDce>("runDceKotlinJs").destinationDir)
        include("*.js")
        into("$buildDir/node_modules")
    }

    val webpack by registering(NpmTask::class) {
        dependsOn(populateNodeModules)
        setArgs(listOf("run", "webpack"))
    }

    val webpackMin by registering(NpmTask::class) {
        dependsOn(populateNodeModules)
        setArgs(listOf("run", "webpackMin"))
    }
}
