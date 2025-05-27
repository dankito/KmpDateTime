package net.dankito.datetime

import assertk.assertThat
import assertk.assertions.isEqualTo
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toJavaLocalTime
import kotlinx.datetime.toLocalDateTime
import kotlin.test.Test

class LocalTimeTestJvm {

    // assert converting LocalTime to ISO string equals the implementations of Java Time and Kotlinx DateTime

    @Test
    fun assertToStringEqualityWithJavaTime() {
        val javaTime = java.time.LocalTime.now()
        val kmpTime = javaTime.toKmpLocalTime()

        val javaTimeString = javaTime.toString()
        val kmpTimeString = kmpTime.toString()

        assertThat(javaTimeString).isEqualTo(kmpTimeString)
    }

    @Test
    fun assertToStringEqualityWithKotlinxDateTime() {
        val kotlinxTime = Clock.System.now().toLocalDateTime(TimeZone.UTC).time
        val kmpTime = kotlinxTime.toJavaLocalTime().toKmpLocalTime()

        val kotlinxTimeString = kotlinxTime.toString()
        val kmpTimeString = kmpTime.toString()

        assertThat(kotlinxTimeString).isEqualTo(kmpTimeString)
    }

}