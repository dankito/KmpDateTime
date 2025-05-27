package net.dankito.datetime.format.pattern

import assertk.assertThat
import assertk.assertions.isEqualTo
import net.dankito.datetime.*
import java.time.format.DateTimeFormatter
import java.time.temporal.TemporalAccessor
import java.util.*
import kotlin.test.Test

class DateTimeComponentFormatterCompareWithJavaTimeTest {

    companion object {
        private val date = LocalDate(2015, 10, 21)
        private val shortDate = LocalDate(1960, 5, 7)

        private val time = LocalTime(9, 8, 7, 600_000_000)
        private val timeAfterNoon = LocalTime(21, 8, 7, 600_000_000)

        private val dateTime = LocalDateTime(shortDate, time)
    }


    private val underTest = DateTimeComponentFormatter()


    @Test
    fun localDate_IsoDate() {
        compare(date, "yyyy-MM-dd")
    }

    @Test
    fun localDate_ShortGermanDateFormat() {
        compare(date, "d.M.yy")
    }

    @Test
    fun localDate_ShortGermanDateFormat_ShortDate() {
        compare(shortDate, "d.M.yy")
    }

    @Test
    fun localDate_DayOfMonth_Abbreviated() {
        compare(date, "MMM")
    }

    @Test
    fun localDate_DayOfMonth_Wide() {
        compare(date, "MMMM")
    }

    @Test
    fun localDate_DayOfMonth_Narrow() {
        compare(date, "MMMMM")
    }

    @Test
    fun localDate_DayOfWeek_E() {
        compare(date, "E")
    }

    @Test
    fun localDate_DayOfWeek_EE() {
        compare(date, "EE")
    }

    @Test
    fun localDate_DayOfWeek_EEE() {
        compare(date, "EEE")
    }

    @Test
    fun localDate_DayOfWeek_EEEE() {
        compare(date, "EEEE")
    }

    @Test
    fun localDate_DayOfWeek_EEEEE() {
        compare(date, "EEEEE")
    }

    // Java Time does not support 'EEEEEE'

    private fun compare(date: LocalDate, pattern: String) {
        val result = underTest.format(date, pattern)

        val javaTimeResult = formatWithJavaTime(date.toJavaLocalDate(), pattern)

        assertThat(result).isEqualTo(javaTimeResult)
    }


    @Test
    fun localTime_IsoTime() {
        compare(time, "HH:mm:ss.SSS")
    }

    @Test
    fun localTime_12Hours_1_Based_MinDigits() {
        compare(time, "h")
    }

    @Test
    fun localTime_12Hours_1_Based_MinDigits_Afternoon() {
        compare(timeAfterNoon, "h")
    }

    @Test
    fun localTime_12Hours_1_Based_MinDigits_Midnight() {
        compare(LocalTime.Midnight, "h")
    }

    @Test
    fun localTime_12Hours_1_Based_MinDigits_Noon() {
        compare(LocalTime.Noon, "h")
    }

    @Test
    fun localTime_12Hours_1_Based_2Digits() {
        compare(time, "hh")
    }

    @Test
    fun localTime_12Hours_1_Based_2Digits_Afternoon() {
        compare(timeAfterNoon, "hh")
    }

    @Test
    fun localTime_24Hours_0_Based_MinDigits() {
        compare(time, "H")
    }

    @Test
    fun localTime_24Hours_0_Based_MinDigits_Afternoon() {
        compare(timeAfterNoon, "H")
    }

    @Test
    fun localTime_24Hours_0_Based_MinDigits_Midnight() {
        compare(LocalTime.Midnight, "H")
    }

    @Test
    fun localTime_24Hours_0_Based_MinDigits_Noon() {
        compare(LocalTime.Noon, "H")
    }

    @Test
    fun localTime_24Hours_0_Based_2Digits() {
        compare(time, "HH")
    }

    @Test
    fun localTime_24Hours_0_Based_2Digits_Afternoon() {
        compare(timeAfterNoon, "HH")
    }

    @Test
    fun localTime_12Hours_0_Based_MinDigits() {
        compare(time, "K")
    }

    @Test
    fun localTime_12Hours_0_Based_MinDigits_Afternoon() {
        compare(timeAfterNoon, "K")
    }

    @Test
    fun localTime_12Hours_0_Based_MinDigits_Midnight() {
        compare(LocalTime.Midnight, "K")
    }

    @Test
    fun localTime_12Hours_0_Based_MinDigits_Noon() {
        compare(LocalTime.Noon, "K")
    }

    @Test
    fun localTime_12Hours_0_Based_2Digits() {
        compare(time, "KK")
    }

    @Test
    fun localTime_12Hours_0_Based_2Digits_Afternoon() {
        compare(timeAfterNoon, "KK")
    }

    @Test
    fun localTime_24Hours_1_Based_MinDigits() {
        compare(time, "k")
    }

    @Test
    fun localTime_24Hours_1_Based_MinDigits_Afternoon() {
        compare(timeAfterNoon, "k")
    }

    @Test
    fun localTime_24Hours_1_Based_MinDigits_Midnight() {
        compare(LocalTime.Midnight, "k")
    }

    @Test
    fun localTime_24Hours_1_Based_MinDigits_Noon() {
        compare(LocalTime.Noon, "k")
    }

    @Test
    fun localTime_24Hours_1_Based_2Digits() {
        compare(time, "kk")

    }

    @Test
    fun localTime_24Hours_1_Based_2Digits_Afternoon() {
        compare(timeAfterNoon, "kk")
    }

    private fun compare(time: LocalTime, pattern: String) {
        val result = underTest.format(time, pattern)

        val javaTimeResult = formatWithJavaTime(time.toJavaLocalTime(), pattern)

        assertThat(result).isEqualTo(javaTimeResult)
    }


    @Test
    fun dateTime_IsoDateTime() {
        compare(dateTime, "yyyy-MM-dd'T'HH:mm:ss.SSS")
    }

    @Test
    fun dateTime_DateAndTimeMixedInPattern() { // to see if mixing date and time components in random order works
        compare(dateTime, "yyyy h MMM m d s 'S' SSSS")
    }

    private fun compare(dateTime: LocalDateTime, pattern: String) {
        val result = underTest.format(dateTime, pattern)

        val javaTimeResult = formatWithJavaTime(dateTime.toJavaLocalDateTime(), pattern)

        assertThat(result).isEqualTo(javaTimeResult)
    }



    private fun formatWithJavaTime(temporal: TemporalAccessor, pattern: String): String {
        val javaTimeFormatter = DateTimeFormatter.ofPattern(pattern, Locale.ENGLISH)

        return javaTimeFormatter.format(temporal)
    }

}