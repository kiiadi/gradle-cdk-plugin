package com.kiiadi.gradle.cdk

import io.kotest.matchers.file.shouldExist
import io.kotest.matchers.file.shouldNotExist
import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.shouldBe
import io.kotest.matchers.string.shouldContain
import org.gradle.testkit.runner.GradleRunner
import org.gradle.testkit.runner.TaskOutcome
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.io.File

class BasicFunctionalTest {

    @BeforeEach
    fun before() {
        clean()
    }

    @Test
    fun canSynthesizeCdkStack() {
        val result = GradleRunner
            .create()
            .withProjectDir(TEST_PROJECT)
            .withArguments("cdkSynth")
            .build()

        result.task(":cdkSynth")?.outcome?.shouldNotBeNull { shouldBe(TaskOutcome.SUCCESS) }
        val template = TEST_PROJECT.resolve("cdk.out").resolve("testStack.template.json")
        template.shouldExist()
        template.readText().shouldContain("somebucket")
    }

    companion object {
        private val TEST_PROJECT = File("test-project")

        @AfterAll
        @JvmStatic
        fun afterAll() {
            clean()
        }

        private fun clean() {
            GradleRunner.create().withProjectDir(TEST_PROJECT).withArguments("clean").build()
            TEST_PROJECT.resolve("cdk.out").shouldNotExist()
        }
    }
}