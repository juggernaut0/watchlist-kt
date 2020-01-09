import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm")
    java
    application
    id("nu.studer.jooq").version("4.0")
    kotlin("kapt")
}

dependencies {
    implementation(project(":common"))

    implementation(kotlin("stdlib-jdk8"))

    val ktorVersion = "1.2.6"
    implementation("io.ktor:ktor-server-core:$ktorVersion")
    implementation("io.ktor:ktor-server-jetty:$ktorVersion")
    implementation("io.ktor:ktor-auth:$ktorVersion")
    implementation("io.ktor:ktor-client-apache:$ktorVersion")

    val daggerVersion = "2.25.4"
    implementation("com.google.dagger:dagger:$daggerVersion")
    kapt("com.google.dagger:dagger-compiler:$daggerVersion")

    implementation("ch.qos.logback:logback-classic:1.2.3")

    implementation("org.postgresql:postgresql:42.2.5")
    implementation("org.jooq:jooq:3.12.3")
    jooqRuntime("org.postgresql:postgresql:42.2.5")
    implementation("com.zaxxer:HikariCP:3.2.0")

    implementation("io.github.config4k:config4k:0.4.1")

    implementation("dev.twarner.auth:auth-common:1-SNAPSHOT")

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

    val run by getting(JavaExec::class) {
        systemProperty("config.file", "local.conf")
    }
}


