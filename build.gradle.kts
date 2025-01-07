
plugins {
    kotlin("jvm") version "2.0.21"
    id("com.gradle.plugin-publish") version "1.3.0"
    `kotlin-dsl`
}

group = "com.kiiadi"
version = "0.1-SNAPSHOT"

repositories {
    mavenCentral()
    gradlePluginPortal()
}

gradlePlugin {
    plugins {
        create("gradle-cdk-plugin") {
            id = "com.kiiadi.gradle-cdk-plugin"
            implementationClass = "com.kiiadi.gradle.cdk.CdkPlugin"
        }
    }
}

dependencies {
    implementation("com.github.node-gradle:gradle-node-plugin:7.1.0")
    implementation("com.gradleup.shadow:shadow-gradle-plugin:8.3.5")
    testImplementation(kotlin("test"))
    testImplementation(gradleTestKit())
    testImplementation("io.kotest:kotest-assertions-core:5.9.1")
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(21)
}