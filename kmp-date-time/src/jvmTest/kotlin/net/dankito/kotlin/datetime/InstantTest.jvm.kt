package net.dankito.kotlin.datetime

import assertk.assertThat
import assertk.assertions.isEqualTo
import kotlinx.datetime.Clock
import kotlinx.datetime.toJavaInstant
import kotlin.test.Test

class InstantTestJvm {

    // assert converting Instant to ISO string equals the implementations of Java Time and Kotlinx DateTime

    @Test
    fun assertToStringEqualityWithJavaTime() {
        val javaInstant = java.time.Instant.now()
        val kmpInstant = javaInstant.toKmpInstant()

        val javaInstantString = javaInstant.toString()
        val kmpInstantString = kmpInstant.toString()

        assertThat(javaInstantString).isEqualTo(kmpInstantString)
    }

    @Test
    fun assertToStringEqualityWithKotlinxDateTime() {
        val kotlinxInstant = Clock.System.now()
        val kmpInstant = kotlinxInstant.toJavaInstant().toKmpInstant()

        val kotlinxInstantString = kotlinxInstant.toString()
        val kmpInstantString = kmpInstant.toString()

        assertThat(kotlinxInstantString).isEqualTo(kmpInstantString)
    }

}