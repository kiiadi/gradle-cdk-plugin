package com.kiiadi.gradle.cdk

import com.github.gradle.node.NodePlugin
import com.github.gradle.node.npm.task.NpxTask
import com.github.jengelman.gradle.plugins.shadow.ShadowPlugin
import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.plugins.ApplicationPlugin
import org.gradle.api.plugins.JavaApplication
import org.gradle.api.tasks.Delete
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.getByType
import org.gradle.kotlin.dsl.register

class CdkPlugin: Plugin<Project> {
    override fun apply(project: Project) {

        project.extensions.add(CdkExtension.NAME, CdkExtension::class.java)
        val cdkExtension = project.extensions.getByType<CdkExtension>()

        val cdkCli = cdkExtension.cdkVersion.map { "aws-cdk@$it" }
        val requireApproval = cdkExtension.requireApproval
        project.plugins.apply(NodePlugin::class.java)

        project.plugins.apply(ApplicationPlugin::class.java)
        project.extensions.configure<JavaApplication> {
            mainClass.set(cdkExtension.mainClass)
        }

        project.plugins.apply(ShadowPlugin::class.java)

        val cdkSynth = project.tasks.register<NpxTask>("cdkSynth") {
            group = TASK_GROUP

            val shadowJarTask = project.tasks.getByName("shadowJar") as ShadowJar
            val shadowJar = shadowJarTask.outputs.files.first()
            inputs.files(shadowJar)
            outputs.dir("cdk.out")

            command.set(cdkCli)

            args.set(listOf("synth", "--app", "\"java -jar ${shadowJar}\"", "-q"))
            dependsOn(shadowJarTask)
        }
        project.tasks.getByName("build").dependsOn(cdkSynth)

        project.tasks.register<NpxTask>("cdkDeploy") {
            group = TASK_GROUP

            command.set(cdkCli)

            args.set(listOf("deploy", "--app", "cdk.out", "--require-approval", requireApproval.get(), "--all"))

            dependsOn(cdkSynth)
        }

        val cleanCdk = project.tasks.register<Delete>("cleanCdk") {
            delete("cdk.out")
        }
        project.tasks.getByName("clean").dependsOn(cleanCdk)
    }

    companion object {
        private const val TASK_GROUP = "cdk"
    }
}