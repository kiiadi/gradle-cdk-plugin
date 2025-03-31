
plugins {
    kotlin("jvm") version "2.0.21"
    id("com.gradle.plugin-publish") version "1.3.0"
    `kotlin-dsl`
    id("net.researchgate.release") version "3.1.0"
}

group = "com.kiiadi"

repositories {
    mavenCentral()
    gradlePluginPortal()
}

tasks.afterReleaseBuild.map { it.dependsOn(tasks.publishPlugins) }

gradlePlugin {
    vcsUrl = "https://github.com/kiiadi/gradle-cdk-plugin"
    website = "https://github.com/kiiadi/gradle-cdk-plugin/blob/main/README.md"
    plugins {
        create("gradle-cdk-plugin") {
            id = "com.kiiadi.gradle-cdk-plugin"
            displayName = "Gradle CDK Plugin"
            description = "This plugin allows you to create Java-based AWS CDK projects without having to have a local node version installed, or to install the CDK CLI separately."
            implementationClass = "com.kiiadi.gradle.cdk.CdkPlugin"
            tags = listOf("aws", "cdk", "iac", "java")
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