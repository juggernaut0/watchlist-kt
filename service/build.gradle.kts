import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm")
    java
    application
    id("nu.studer.jooq").version("4.0")
}

dependencies {
    implementation(project(":common"))

    implementation(kotlin("stdlib-jdk8"))

    val ktorVersion = "1.2.6"
    implementation("io.ktor:ktor-server-core:$ktorVersion")
    implementation("io.ktor:ktor-server-jetty:$ktorVersion")
    implementation("io.ktor:ktor-auth:$ktorVersion")
    implementation("io.ktor:ktor-html-builder:$ktorVersion")

    implementation("org.jetbrains.kotlinx:kotlinx-html-jvm:0.6.12")

    implementation("com.google.inject:guice:4.2.2:no_aop")

    implementation("ch.qos.logback:logback-classic:1.2.3")

    implementation("org.postgresql:postgresql:42.2.5")
    implementation("org.jooq:jooq:3.12.3")
    jooqRuntime("org.postgresql:postgresql:42.2.5")
    implementation("com.zaxxer:HikariCP:3.2.0")

    testImplementation(kotlin("test-junit"))
}

application {
    mainClassName = "watchlist.MainKt"
}

apply {
    from("jooq.gradle")
}
tasks {
    withType<KotlinCompile>().forEach {
        it.kotlinOptions.jvmTarget = "1.8"
        it.dependsOn("generatePostgresJooqSchemaSource")
    }

    val copyWeb by registering(Copy::class) {
        if (System.getenv("RELEASE") != null) {
            dependsOn(":web:webpackMin")
        } else {
            dependsOn(":web:webpack")
        }
        group = "build"
        from("${project(":web").buildDir}/webpack")
        into("$buildDir/resources/main/static/js")
    }

    getByName("classes").dependsOn(copyWeb)
}


