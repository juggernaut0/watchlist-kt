plugins {
    kotlin("jvm")
    application
    id("dev.twarner.common")
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))

    implementation(libs.postgresql)
    implementation(libs.flyway.core)
}

application {
    mainClass.set("watchlist.MigrateKt")
}

tasks {
    (run) {
        args = listOf("postgres://watchlist:watchlist@localhost:6432/watchlist")
    }
}
