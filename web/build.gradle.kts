plugins {
    kotlin("js")
    id("dev.twarner.common")
}

dependencies {
    implementation(project(":common"))
    implementation(project(":ui"))
}

kotlin {
    js {
        browser()
    }
}
