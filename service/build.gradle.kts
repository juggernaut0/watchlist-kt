import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm")
    java
    application
    alias(libs.plugins.jooq)
    kotlin("kapt")
    //alias(libs.plugins.docker.api)
    id("dev.twarner.docker")
    id("dev.twarner.common")
}

dependencies {
    implementation(project(":common"))
    implementation(project(":dbmigrate"))

    implementation(kotlin("stdlib-jdk8"))

    implementation(platform(libs.ktor.bom))
    implementation(libs.bundles.ktor.server.jetty)
    implementation(libs.ktor.client.apache)

    implementation(libs.dagger)
    kapt(libs.dagger.compiler)

    implementation(libs.logback)

    implementation(libs.postgresql)
    jooqGenerator(libs.postgresql)
    implementation("com.zaxxer:HikariCP:5.0.1")

    implementation(libs.config4k)

    implementation(libs.twarner.auth.common)

    testImplementation(kotlin("test-junit"))
}

application {
    mainClass.set("watchlist.MainKt")
}

jooq {
    configurations {
        create("main") {
            generateSchemaSourceOnCompilation.set(true)
            jooqConfiguration.apply {
                jdbc.apply {
                    driver = "org.postgresql.Driver"
                    url = "jdbc:postgresql://localhost:6432/watchlist"
                    user = "watchlist"
                    password = "watchlist"
                }
                generator.apply {
                    name = "org.jooq.codegen.DefaultGenerator"
                    strategy.apply {
                        name = "org.jooq.codegen.DefaultGeneratorStrategy"
                    }
                    database.apply {
                        name = "org.jooq.meta.postgres.PostgresDatabase"
                        inputSchema = "public"
                        includes = ".*"
                        excludes = "flyway_schema_history"
                    }
                    generate.apply {
                        isRelations = true
                        isDeprecated = false
                        isRecords = true
                        isFluentSetters = false
                    }
                    target.apply {
                        packageName = "watchlist.db.jooq"
                    }
                }
            }
        }
    }
}

tasks {
    withType<KotlinCompile>().configureEach {
        kotlinOptions.jvmTarget = "17"
    }

    val copyWeb by registering(Copy::class) {
        if (version.toString().endsWith("SNAPSHOT")) {
            dependsOn(":web:browserDevelopmentWebpack")
        } else {
            dependsOn(":web:browserProductionWebpack")
        }
        group = "build"
        from("${project(":web").buildDir}/distributions")
        into("$buildDir/resources/main/static/js")
        include("*.js", "*.js.map")
    }

    classes {
        dependsOn(copyWeb)
    }

    (run) {
        systemProperty("config.file", "local.conf")
    }
}
