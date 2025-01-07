# Gradle Plugin for AWS CDK

![Build Status](https://github.com/kiiadi/gradle-cdk-plugin/workflows/Build/badge.svg?branch=main)
[![License](https://img.shields.io/github/license/kiiadi/gradle-cdk-plugin.svg)](http://www.apache.org/licenses/LICENSE-2.0.html)

This plugin allows you to create Java-based [AWS CDK](https://aws.amazon.com/cdk/) projects without having to have a local node version installed, or to install the CDK CLI separately.

## Basic Usage

```gradle
plugins {
  id("com.kiiadi.gradle-cdk-plugin")
}

cdk {
  mainClass = "..." // full qualified reference to the App entrypoint
}
```

The plugin depends on the [Gradle Node Plugin](https://github.com/node-gradle/gradle-node-plugin) to execute Node/NPX commands. By default
this uses a local version of node. If you want to automatically download a node version as part of the build you can override the plugin's behaviour:

```gradle
node {
  download = true
}
```