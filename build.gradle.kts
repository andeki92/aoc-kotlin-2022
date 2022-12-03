import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.7.22"
    id("org.jetbrains.kotlinx.kover") version "0.6.1"
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


application {
    mainClass.set("AdventOfCodeKt")
}
val compileKotlin: KotlinCompile by tasks
compileKotlin.kotlinOptions {
    jvmTarget = "17"
    useK2 = true
    freeCompilerArgs = listOf("-Xcontext-receivers")
}