package com.kiiadi.gradle.cdk

import com.github.gradle.node.NodeExtension
import com.github.gradle.node.NodePlugin
import com.github.gradle.node.npm.task.NpxTask
import com.github.jengelman.gradle.plugins.shadow.ShadowPlugin
import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.initialization.resolve.RepositoriesMode
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
        project.plugins.apply(NodePlugin::class.java)

        project.plugins.apply(ApplicationPlugin::class.java)
        project.extensions.configure<JavaApplication> {
            mainClass.set(cdkExtension.mainClass)
        }

        project.plugins.apply(ShadowPlugin::class.java)

        val cdkSynth = project.tasks.register<NpxTask>("cdkSynth") {
            val shadowJarTask = project.tasks.getByName("shadowJar") as ShadowJar
            group = TASK_GROUP
            command.set(project.extensions.getByType<CdkExtension>().cdkVersion.map { "aws-cdk@$it" })

            val shadowJar = shadowJarTask.outputs.files.first()
            args.set(listOf("synth", "--app", "\"java -jar ${shadowJar}\"", "-q"))
            dependsOn(shadowJarTask)
        }
        project.tasks.getByName("build").dependsOn(cdkSynth)

        val cleanCdk = project.tasks.register<Delete>("cleanCdk") {

            delete("cdk.out")
        }
        project.tasks.getByName("clean").dependsOn(cleanCdk)
    }

    companion object {
        private const val TASK_GROUP = "cdk"
    }
}