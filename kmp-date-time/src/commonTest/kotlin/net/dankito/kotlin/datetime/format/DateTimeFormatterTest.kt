package net.dankito.kotlin.datetime.format

import assertk.assertThat
import assertk.assertions.isEqualTo
import net.dankito.kotlin.datetime.LocalDate
import net.dankito.kotlin.datetime.LocalDateTime
import net.dankito.kotlin.datetime.LocalTime
import kotlin.test.Test

class DateTimeFormatterTest {

    @Test
    fun date() {
        val result = DateTimeFormatter.toIsoString(LocalDate(2015, 10, 21))

        assertThat(result).isEqualTo("2015-10-21")
    }

    @Test
    fun date_PadStart() {
        val result = DateTimeFormatter.toIsoString(LocalDate(15, 1, 2))

        assertThat(result).isEqualTo("0015-01-02")
    }

    @Test
    fun date_toDotSeparatedIsoString() {
        val result = DateTimeFormatter.toDotSeparatedIsoString(LocalDate(2015, 10, 21))

        assertThat(result).isEqualTo("2015.10.21")
    }


    @Test
    fun time_WithoutFractionalSeconds() {
        val result = DateTimeFormatter.toIsoString(LocalTime(10, 11))

        assertThat(result).isEqualTo("10:11:00")
    }

    @Test
    fun time_3FractionalSeconds() {
        val result = DateTimeFormatter.toIsoString(LocalTime(10, 11, 0, 123000000))

        assertThat(result).isEqualTo("10:11:00.123")
    }

    @Test
    fun time_9FractionalSeconds() {
        val result = DateTimeFormatter.toIsoString(LocalTime(10, 11, 0, 123456789))

        assertThat(result).isEqualTo("10:11:00.123456789")
    }


    @Test
    fun dateTime_WithoutFractionalSeconds() {
        val result = DateTimeFormatter.toIsoString(LocalDateTime(2015, 10, 21, 10, 11))

        assertThat(result).isEqualTo("2015-10-21T10:11:00")
    }

    @Test
    fun dateTime_9FractionalSeconds() {
        val result = DateTimeFormatter.toIsoString(LocalDateTime(2015, 10, 21, 10, 11, 0, 123456789))

        assertThat(result).isEqualTo("2015-10-21T10:11:00.123456789")
    }

}