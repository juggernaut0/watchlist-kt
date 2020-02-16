import com.moowork.gradle.node.npm.NpmTask
import org.jetbrains.kotlin.gradle.tasks.Kotlin2JsCompile
import java.time.LocalDate
import java.time.format.DateTimeFormatter

version = LocalDate.now().format(DateTimeFormatter.BASIC_ISO_DATE)

plugins {
    id("kotlin2js")
    id("com.github.node-gradle.node")
}

sourceSets {
    val renderer by creating { }
}

dependencies {
    implementation(kotlin("stdlib-js"))

    "rendererImplementation"(kotlin("stdlib-js"))
    "rendererImplementation"(project(":ui"))
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
            from(zipTree(file.absolutePath)) {
                includeEmptyDirs = false
                include { it.path.endsWith(".js") }
            }
        }
        include("**/*.js")
        includeEmptyDirs = false
        into("$buildDir/dist/node_modules")
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
            File("$buildDir/dist/package.json").writeText("""
                {
                    "name": "watchlist",
                    "version": "$version", 
                    "main": "electron.js"
                }
            """.trimIndent() + "\n")
        }
    }

    getByName("assemble").dependsOn(copyStatic, assembleScripts, populateNodeModules, generatePackageJson)

    val run by registering(NpmTask::class) {
        dependsOn("assemble")
        setArgs(listOf("run", "start"))
    }

    val `package` by registering(NpmTask::class) {
        dependsOn("assemble")
        setArgs(listOf("run", "package"))
    }
}
