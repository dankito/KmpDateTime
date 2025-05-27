package net.dankito.datetime

import assertk.assertThat
import assertk.assertions.isEqualTo
import kotlinx.datetime.*
import kotlin.test.Test

class LocalDateTestJvm {

    // assert converting LocalDate to ISO string equals the implementations of Java Time and Kotlinx DateTime

    @Test
    fun assertToStringEqualityWithJavaTime() {
        val javaDate = java.time.LocalDate.now()
        val kmpDate = javaDate.toKmpLocalDate()

        val javaDateString = javaDate.toString()
        val kmpDateString = kmpDate.toString()

        assertThat(javaDateString).isEqualTo(kmpDateString)
    }

    @Test
    fun assertToStringEqualityWithKotlinxDateTime() {
        val kotlinxDate = Clock.System.now().toLocalDateTime(TimeZone.UTC).date
        val kmpDate = kotlinxDate.toJavaLocalDate().toKmpLocalDate()

        val kotlinxDateString = kotlinxDate.toString()
        val kmpDateString = kmpDate.toString()

        assertThat(kotlinxDateString).isEqualTo(kmpDateString)
    }

}