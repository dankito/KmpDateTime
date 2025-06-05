package net.dankito.datetime.format.pattern

import assertk.assertThat
import assertk.assertions.isEqualTo
import net.dankito.datetime.LocalDate
import net.dankito.datetime.LocalDateTime
import net.dankito.datetime.LocalTime
import net.dankito.datetime.format.pattern.component.MinuteComponent
import kotlin.test.Test
import kotlin.test.assertFailsWith

class DateTimeComponentFormatterTest {

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
        val result = underTest.format(date, "yyyy-MM-dd")

        assertThat(result).isEqualTo("2015-10-21")
    }

    @Test
    fun localDate_ShortGermanDateFormat() {
        val result = underTest.format(date, "d.M.yy")

        assertThat(result).isEqualTo("21.10.15")
    }

    @Test
    fun localDate_ShortGermanDateFormat_ShortDate() {
        val result = underTest.format(shortDate, "d.M.yy")

        assertThat(result).isEqualTo("7.5.60")
    }

    @Test
    fun localDate_DayOfMonth_Abbreviated() {
        val result = underTest.format(date, "MMM")

        assertThat(result).isEqualTo("Oct")
    }

    @Test
    fun localDate_DayOfMonth_Wide() {
        val result = underTest.format(date, "MMMM")

        assertThat(result).isEqualTo("October")
    }

    @Test
    fun localDate_DayOfMonth_Narrow() {
        val result = underTest.format(date, "MMMMM")

        assertThat(result).isEqualTo("O")
    }

    @Test
    fun localDate_DayOfYear_D() {
        val result = underTest.format(date, "D")

        assertThat(result).isEqualTo("294")
    }

    @Test
    fun localDate_DayOfYear_DD() {
        val date = LocalDate(0, 1, 8)
        val result = underTest.format(date, "DD")

        assertThat(result).isEqualTo("08")
    }

    @Test
    fun localDate_DayOfYear_DDD() {
        val date = LocalDate(0, 1, 2)
        val result = underTest.format(date, "DDD")

        assertThat(result).isEqualTo("002")
    }

    @Test
    fun localDate_DayOfWeek_E() {
        val result = underTest.format(date, "EE")

        assertThat(result).isEqualTo("Wed")
    }

    @Test
    fun localDate_DayOfWeek_EE() {
        val result = underTest.format(date, "EE")

        assertThat(result).isEqualTo("Wed")
    }

    @Test
    fun localDate_DayOfWeek_EEE() {
        val result = underTest.format(date, "EEE")

        assertThat(result).isEqualTo("Wed")
    }

    @Test
    fun localDate_DayOfWeek_EEEE() {
        val result = underTest.format(date, "EEEE")

        assertThat(result).isEqualTo("Wednesday")
    }

    @Test
    fun localDate_DayOfWeek_EEEEE() {
        val result = underTest.format(date, "EEEEE")

        assertThat(result).isEqualTo("W")
    }

    @Test
    fun localDate_DayOfWeek_EEEEEE() {
        val result = underTest.format(date, "EEEEEE")

        assertThat(result).isEqualTo("We")
    }


    @Test
    fun localDate_Quarter_minDigits() {
        val result = underTest.format(date, "Q")

        assertThat(result).isEqualTo("4")
    }

    @Test
    fun localDate_Quarter_2Digits() {
        val result = underTest.format(date, "QQ")

        assertThat(result).isEqualTo("04")
    }

    @Test
    fun localDate_Quarter_Abbreviated() {
        val result = underTest.format(date, "QQQ")

        assertThat(result).isEqualTo("Q4")
    }

    @Test
    fun localDate_Quarter_Wide_FirstQuarter_Start() {
        val result = underTest.format(LocalDate(0, 1, 1), "QQQQ")

        assertThat(result).isEqualTo("1st quarter")
    }

    @Test
    fun localDate_Quarter_Wide_FirstQuarter_End() {
        val result = underTest.format(LocalDate(0, 3, 31), "QQQQ")

        assertThat(result).isEqualTo("1st quarter")
    }

    @Test
    fun localDate_Quarter_Wide_SecondQuarter_Start() {
        val result = underTest.format(LocalDate(0, 4, 1), "QQQQ")

        assertThat(result).isEqualTo("2nd quarter")
    }

    @Test
    fun localDate_Quarter_Wide_SecondQuarter_End() {
        val result = underTest.format(LocalDate(0, 6, 30), "QQQQ")

        assertThat(result).isEqualTo("2nd quarter")
    }

    @Test
    fun localDate_Quarter_Wide_ThirdQuarter_Start() {
        val result = underTest.format(LocalDate(0, 7, 1), "QQQQ")

        assertThat(result).isEqualTo("3rd quarter")
    }

    @Test
    fun localDate_Quarter_Wide_ThirdQuarter_End() {
        val result = underTest.format(LocalDate(0, 9, 30), "QQQQ")

        assertThat(result).isEqualTo("3rd quarter")
    }

    @Test
    fun localDate_Quarter_Wide_FourthQuarter_Start() {
        val result = underTest.format(LocalDate(0, 10, 1), "QQQQ")

        assertThat(result).isEqualTo("4th quarter")
    }

    @Test
    fun localDate_Quarter_Wide_FourthQuarter_End() {
        val result = underTest.format(LocalDate(0, 12, 31), "QQQQ")

        assertThat(result).isEqualTo("4th quarter")
    }

    @Test
    fun localDate_Quarter_Narrow() {
        val result = underTest.format(date, "QQQQQ")

        assertThat(result).isEqualTo("4")
    }

    @Test
    fun localDate_UnsupportedComponent() {
        assertFailsWith<IllegalArgumentException> {
            underTest.format(date, DateTimeFormatPattern(listOf(MinuteComponent(2))))
        }
    }


    @Test
    fun localTime_IsoTime() {
        val result = underTest.format(time, "HH:mm:ss.SSS")

        assertThat(result).isEqualTo("09:08:07.600")
    }

    @Test
    fun localTime_12Hours_1_Based_MinDigits() {
        val result = underTest.format(time, "h")

        assertThat(result).isEqualTo("9")
    }

    @Test
    fun localTime_12Hours_1_Based_MinDigits_Afternoon() {
        val result = underTest.format(timeAfterNoon, "h")

        assertThat(result).isEqualTo("9")
    }

    @Test
    fun localTime_12Hours_1_Based_MinDigits_Midnight() {
        val result = underTest.format(LocalTime.Midnight, "h")

        assertThat(result).isEqualTo("12")
    }

    @Test
    fun localTime_12Hours_1_Based_MinDigits_Noon() {
        val result = underTest.format(LocalTime.Noon, "h")

        assertThat(result).isEqualTo("12")
    }

    @Test
    fun localTime_12Hours_1_Based_2Digits() {
        val result = underTest.format(time, "hh")

        assertThat(result).isEqualTo("09")
    }

    @Test
    fun localTime_12Hours_1_Based_2Digits_Afternoon() {
        val result = underTest.format(timeAfterNoon, "hh")

        assertThat(result).isEqualTo("09")
    }

    @Test
    fun localTime_24Hours_0_Based_MinDigits() {
        val result = underTest.format(time, "H")

        assertThat(result).isEqualTo("9")
    }

    @Test
    fun localTime_24Hours_0_Based_MinDigits_Afternoon() {
        val result = underTest.format(timeAfterNoon, "H")

        assertThat(result).isEqualTo("21")
    }

    @Test
    fun localTime_24Hours_0_Based_MinDigits_Midnight() {
        val result = underTest.format(LocalTime.Midnight, "H")

        assertThat(result).isEqualTo("0")
    }

    @Test
    fun localTime_24Hours_0_Based_MinDigits_Noon() {
        val result = underTest.format(LocalTime.Noon, "H")

        assertThat(result).isEqualTo("12")
    }

    @Test
    fun localTime_24Hours_0_Based_2Digits() {
        val result = underTest.format(time, "HH")

        assertThat(result).isEqualTo("09")
    }

    @Test
    fun localTime_24Hours_0_Based_2Digits_Afternoon() {
        val result = underTest.format(timeAfterNoon, "HH")

        assertThat(result).isEqualTo("21")
    }

    @Test
    fun localTime_12Hours_0_Based_MinDigits() {
        val result = underTest.format(time, "K")

        assertThat(result).isEqualTo("9")
    }

    @Test
    fun localTime_12Hours_0_Based_MinDigits_Afternoon() {
        val result = underTest.format(timeAfterNoon, "K")

        assertThat(result).isEqualTo("9")
    }

    @Test
    fun localTime_12Hours_0_Based_MinDigits_Midnight() {
        val result = underTest.format(LocalTime.Midnight, "K")

        assertThat(result).isEqualTo("0")
    }

    @Test
    fun localTime_12Hours_0_Based_MinDigits_Noon() {
        val result = underTest.format(LocalTime.Noon, "K")

        assertThat(result).isEqualTo("0")
    }

    @Test
    fun localTime_12Hours_0_Based_2Digits() {
        val result = underTest.format(time, "KK")

        assertThat(result).isEqualTo("09")
    }

    @Test
    fun localTime_12Hours_0_Based_2Digits_Afternoon() {
        val result = underTest.format(timeAfterNoon, "KK")

        assertThat(result).isEqualTo("09")
    }

    @Test
    fun localTime_24Hours_1_Based_MinDigits() {
        val result = underTest.format(time, "k")

        assertThat(result).isEqualTo("9")
    }

    @Test
    fun localTime_24Hours_1_Based_MinDigits_Afternoon() {
        val result = underTest.format(timeAfterNoon, "k")

        assertThat(result).isEqualTo("21")
    }

    @Test
    fun localTime_24Hours_1_Based_MinDigits_Midnight() {
        val result = underTest.format(LocalTime.Midnight, "k")

        assertThat(result).isEqualTo("24")
    }

    @Test
    fun localTime_24Hours_1_Based_MinDigits_Noon() {
        val result = underTest.format(LocalTime.Noon, "k")

        assertThat(result).isEqualTo("12")
    }

    @Test
    fun localTime_24Hours_1_Based_2Digits() {
        val result = underTest.format(time, "kk")

        assertThat(result).isEqualTo("09")
    }

    @Test
    fun localTime_24Hours_1_Based_2Digits_Afternoon() {
        val result = underTest.format(timeAfterNoon, "kk")

        assertThat(result).isEqualTo("21")
    }


    @Test
    fun dateTime_IsoDateTime() {
        val result = underTest.format(dateTime, "yyyy-MM-dd'T'HH:mm:ss.SSS")

        assertThat(result).isEqualTo("1960-05-07T09:08:07.600")
    }

    @Test
    fun dateTime_DateAndTimeMixedInPattern() { // to see if mixing date and time components in random order works
        val result = underTest.format(dateTime, "yyyy h MMM m d s 'S' SSSS")

        assertThat(result).isEqualTo("1960 9 May 8 7 7 S 6000")
    }

}