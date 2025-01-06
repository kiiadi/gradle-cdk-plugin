
plugins {
    kotlin("jvm") version "2.0.21"
    id("com.kiiadi.gradle-cdk-plugin")
}

group = "com.kiiadi"

repositories {
    mavenCentral()
}

node {
    download = true
}

cdk {
    mainClass.set("CdkTestAppKt")
}

dependencies {
    implementation("software.amazon.awscdk:aws-cdk-lib:2.174.0")
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(21)
}