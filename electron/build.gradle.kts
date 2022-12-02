import dev.twarner.gradle.YarnTask
import org.jetbrains.kotlin.gradle.targets.js.webpack.KotlinWebpack
import org.jetbrains.kotlin.gradle.targets.js.yarn.yarn
import org.jetbrains.kotlin.gradle.tasks.Kotlin2JsCompile

plugins {
    kotlin("js")
    id("dev.twarner.common")
    id("dev.twarner.kotlin")
}

kotlin {
    js {
        nodejs()
    }
}

// Required to properly install electron
yarn.ignoreScripts = false

dependencies {

    implementation(devNpm("electron", "21.3.1"))
    implementation(devNpm("electron-packager", "17.1.1"))

    implementation(libs.asyncLite)
}

tasks {
    withType<Kotlin2JsCompile>().configureEach {
        kotlinOptions.moduleKind = "commonjs"
    }

    val unpackJar by registering(Copy::class) {
        from(zipTree(named<Jar>("jsJar").flatMap { it.archiveFile }))
        into(layout.buildDirectory.dir("electron"))
    }

    val copyRenderer by registering(Copy::class) {
        val browserProductionWebpack = project(":electron:renderer").tasks.named<KotlinWebpack>("browserProductionWebpack")
        from(browserProductionWebpack.map { it.destinationDirectory })
        into(unpackJar.map { it.destinationDir })
    }

    val electronYarnInstall by registering(YarnTask::class) {
        dependsOn(copyRenderer)
        dir.set(layout.dir(unpackJar.map { it.destinationDir }))
        arguments.set(listOf("install"))
    }

    val populateNodeModules by registering(Copy::class) {
        dependsOn(unpackJar, electronYarnInstall)
        configurations.getByName("runtimeClasspath").forEach { file ->
            from(zipTree(file.absolutePath)) {
                includeEmptyDirs = false
                include { it.path.endsWith(".js") }
                exclude { it.path.endsWith(".meta.js") }
            }
        }
        include("**/*.js")
        exclude("**/watchlist-electron.js")
        includeEmptyDirs = false
        into(unpackJar.map { it.destinationDir.resolve("node_modules") })
    }

    val runElectron by registering(YarnTask::class) {
        dependsOn(populateNodeModules)
        dir.set(layout.dir(unpackJar.map { it.destinationDir }))
        arguments.set(listOf("run", "electron", "."))
        environment.putAll(mapOf("WATCHLIST_DATA_DIR" to "$buildDir/data"))
    }

    val packageElectron by registering(YarnTask::class) {
        dependsOn(populateNodeModules)
        dir.set(layout.dir(unpackJar.map { it.destinationDir }))
        arguments.set(listOf("run", "electron-packager", ".", "--out=$buildDir/package", "--overwrite"/*, "--no-prune"*/))
        outputs.dir(buildDir.resolve("package"))
    }
}
