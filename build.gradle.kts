import com.moowork.gradle.node.npm.NpmTask
import org.jetbrains.kotlin.gradle.tasks.Kotlin2JsCompile

buildscript {
    repositories {
        mavenCentral()
    }
    dependencies {
        classpath("org.jetbrains.kotlin:kotlin-serialization:1.3.31")
    }
}

plugins {
    id("kotlin2js") version "1.3.31"
    id("com.moowork.node") version "1.3.1"
}

apply(plugin = "kotlinx-serialization")

version = "1.0-SNAPSHOT"

repositories {
    maven("https://kotlin.bintray.com/kotlinx")
    mavenCentral()
    maven("https://juggernaut0.github.io/m2/repository")
}

sourceSets {
    val renderer by creating {

    }
}

dependencies {
    implementation(kotlin("stdlib-js"))

    "rendererImplementation"(kotlin("stdlib-js"))
    "rendererImplementation"("com.github.juggernaut0.kui:kui:0.5.0")
    "rendererImplementation"("org.jetbrains.kotlinx:kotlinx-serialization-runtime-js:0.10.0")
}

tasks {
    withType<Kotlin2JsCompile>().forEach {
        it.kotlinOptions.moduleKind = "commonjs"
    }

    // either need this or 'nodeIntegration = true' on BrowserWindow
    //getByName<Kotlin2JsCompile>("compileRendererKotlin2Js").kotlinOptions.moduleKind = "plain"

    val copyStatic by registering(Copy::class) {
        group = "build"
        from("static")
        into("$buildDir/dist")
    }

    val populateNodeModules by registering(Copy::class) {
        // TODO auto-detect configurations
        configurations.getByName("rendererRuntimeClasspath").forEach { file ->
            from(zipTree(file.absolutePath), {
                includeEmptyDirs = false
                include { it.path.endsWith(".js") }
            })
        }
        include("**/*.js")
        includeEmptyDirs = false
        into("$buildDir/node_modules")
    }

    val assembleScripts by registering(Copy::class) {
        dependsOn("classes", "rendererClasses")
        from(getByName<Kotlin2JsCompile>("compileKotlin2Js").destinationDir)
        from(getByName<Kotlin2JsCompile>("compileRendererKotlin2Js").destinationDir)
        include("**/*.js")
        includeEmptyDirs = false
        into("$buildDir/dist")
    }

    val generatePackageJson by registering {
        outputs.files("$buildDir/dist/package.json")
        doLast {
            File("$buildDir/dist/package.json").writeText("{\"main\": \"watchlist.js\"}\n")
        }
    }

    getByName("assemble").dependsOn(copyStatic, assembleScripts, populateNodeModules, generatePackageJson)

    val run by registering(NpmTask::class) {
        dependsOn("assemble")
        setArgs(listOf("run", "start"))
    }
}
