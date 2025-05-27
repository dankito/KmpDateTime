package net.dankito.datetime.format.pattern

import assertk.assertThat
import assertk.assertions.isEqualTo
import net.dankito.datetime.LocalDate
import net.dankito.datetime.toJavaLocalDate
import java.time.format.DateTimeFormatter
import java.time.temporal.TemporalAccessor
import java.util.*
import kotlin.test.Test

class DateTimeComponentFormatterCompareWithJavaTimeTest {

    companion object {
        private val date = LocalDate(2015, 10, 21)
        private val shortDate = LocalDate(1960, 5, 7)
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

    private fun compare(date: LocalDate, pattern: String) {
        val result = underTest.format(date, pattern)

        val javaTimeResult = formatWithJavaTime(date.toJavaLocalDate(), pattern)

        assertThat(result).isEqualTo(javaTimeResult)
    }



    private fun formatWithJavaTime(temporal: TemporalAccessor, pattern: String): String {
        val javaTimeFormatter = DateTimeFormatter.ofPattern(pattern, Locale.ENGLISH)

        return javaTimeFormatter.format(temporal)
    }

}