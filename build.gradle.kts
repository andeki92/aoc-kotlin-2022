import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.7.22"
    application
}

group = "com.klever-kirkeby"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("reflect"))

    implementation("com.github.ajalt.mordant:mordant:2.0.0-beta9")

    testImplementation(kotlin("test"))
}

tasks {
    test {
        useJUnitPlatform()
    }

    wrapper {
        gradleVersion = "7.6"
    }
}


tasks.withType<KotlinCompile> {
    kotlinOptions {
        jvmTarget = "17"
        useK2 = true
        freeCompilerArgs = listOf("-Xcontext-receivers")
    }
}

application {
    mainClass.set("AdventOfCodeKt")
}