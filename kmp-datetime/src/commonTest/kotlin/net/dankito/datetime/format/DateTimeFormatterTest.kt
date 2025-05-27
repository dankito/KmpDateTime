package net.dankito.datetime.format

import assertk.assertThat
import assertk.assertions.isEqualTo
import net.dankito.datetime.LocalDate
import net.dankito.datetime.LocalDateTime
import net.dankito.datetime.LocalTime
import kotlin.test.Test

class DateTimeFormatterTest {

    private val underTest = DateTimeFormatter.Default


    @Test
    fun date() {
        val result = underTest.toIsoString(LocalDate(2015, 10, 21))

        assertThat(result).isEqualTo("2015-10-21")
    }

    @Test
    fun date_PadStart() {
        val result = underTest.toIsoString(LocalDate(15, 1, 2))

        assertThat(result).isEqualTo("0015-01-02")
    }

    @Test
    fun date_toDotSeparatedIsoString() {
        val result = underTest.toDotSeparatedIsoString(LocalDate(2015, 10, 21))

        assertThat(result).isEqualTo("2015.10.21")
    }


    @Test
    fun time_WithoutFractionalSeconds() {
        val result = underTest.toIsoString(LocalTime(10, 11))

        assertThat(result).isEqualTo("10:11:00")
    }

    @Test
    fun time_3FractionalSeconds() {
        val result = underTest.toIsoString(LocalTime(10, 11, 0, 123000000))

        assertThat(result).isEqualTo("10:11:00.123")
    }

    @Test
    fun time_9FractionalSeconds() {
        val result = underTest.toIsoString(LocalTime(10, 11, 0, 123456789))

        assertThat(result).isEqualTo("10:11:00.123456789")
    }


    @Test
    fun dateTime_WithoutFractionalSeconds() {
        val result = underTest.toIsoString(LocalDateTime(2015, 10, 21, 10, 11))

        assertThat(result).isEqualTo("2015-10-21T10:11:00")
    }

    @Test
    fun dateTime_9FractionalSeconds() {
        val result = underTest.toIsoString(LocalDateTime(2015, 10, 21, 10, 11, 0, 123456789))

        assertThat(result).isEqualTo("2015-10-21T10:11:00.123456789")
    }

}