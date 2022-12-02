plugins {
    kotlin("js")
    id("dev.twarner.common")
}

kotlin {
    js {
        browser()
    }
}

dependencies {
    implementation(project(":ui"))
}
