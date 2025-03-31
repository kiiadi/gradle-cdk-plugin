package com.kiiadi.gradle.cdk

import org.gradle.api.Project
import org.gradle.api.initialization.resolve.RepositoriesMode
import org.gradle.api.provider.Property
import org.gradle.kotlin.dsl.property

open class CdkExtension(project: Project) {

    /**
     * The main infrastructure class that contains a call to [App.synth]
     */
    val mainClass: Property<String> = project.objects.property<String>()

    /**
     * The version of the CDK CLI to use for CDK commands
     */
    val cdkVersion: Property<String> = project.objects.property<String>().convention(DEFAULT_CDK_CLI_VERSION)

    /**
     * Require approval for sensitive changes on deployment
     * Possible values: never, any-change, and broadening
     */
    val requireApproval: Property<String> = project.objects.property<String>().convention("never")

    companion object {
        private const val DEFAULT_CDK_CLI_VERSION = "2.174.0"
        const val NAME = "cdk"
    }
}