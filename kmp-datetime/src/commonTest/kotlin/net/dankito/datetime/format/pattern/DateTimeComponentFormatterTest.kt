package net.dankito.datetime.format.pattern

import assertk.assertThat
import assertk.assertions.isEqualTo
import net.dankito.datetime.LocalDate
import net.dankito.datetime.format.pattern.component.MinuteComponent
import kotlin.test.Test
import kotlin.test.assertFailsWith

class DateTimeComponentFormatterTest {

    companion object {
        private val date = LocalDate(2015, 10, 21)
        private val shortDate = LocalDate(1960, 5, 7)
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
    fun localDate_DayOfWeek() {
        assertFailsWith<UnsupportedOperationException> {
            underTest.format(date, "E")
        }
    }

    @Test
    fun localDate_UnsupportedComponent() {
        assertFailsWith<IllegalArgumentException> {
            underTest.format(date, DateTimeFormatPattern(listOf(MinuteComponent(2))))
        }
    }

}