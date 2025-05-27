package net.dankito.datetime.format.pattern

import assertk.assertThat
import assertk.assertions.hasSize
import assertk.assertions.isEqualByComparingTo
import assertk.assertions.isEqualTo
import assertk.assertions.isInstanceOf
import net.dankito.datetime.format.pattern.component.*
import kotlin.test.Test
import kotlin.test.assertFailsWith

class DateTimeFormatPatternParserTest {

    private val underTest = DateTimeFormatPatternParser()


    @Test
    fun year_2Digits() {
        val result = underTest.parsePattern("yy")

        assertComponentWithMinLength<YearComponent>(result, 2)
    }

    @Test
    fun year_4Digits() {
        val result = underTest.parsePattern("yyyy")

        assertComponentWithMinLength<YearComponent>(result, 4)
    }


    @Test
    fun month_minDigits() {
        val result = underTest.parsePattern("M")

        assertMonthComponent(result, MonthStyle.NumericMinDigits)
    }

    @Test
    fun month_2Digits() {
        val result = underTest.parsePattern("MM")

        assertMonthComponent(result, MonthStyle.Numeric2Digits)
    }

    @Test
    fun month_Abbreviated() {
        val result = underTest.parsePattern("MMM")

        assertMonthComponent(result, MonthStyle.Abbreviated)
    }

    @Test
    fun month_Wide() {
        val result = underTest.parsePattern("MMMM")

        assertMonthComponent(result, MonthStyle.Wide)
    }

    @Test
    fun month_Narrow() {
        val result = underTest.parsePattern("MMMMM")

        assertMonthComponent(result, MonthStyle.Narrow)
    }


    @Test
    fun dayOfMonth_MinDigits() {
        val result = underTest.parsePattern("d")

        assertComponentWithMinLength<DayOfMonthComponent>(result, 1)
    }

    @Test
    fun dayOfMonth_2Digits() {
        val result = underTest.parsePattern("dd")

        assertComponentWithMinLength<DayOfMonthComponent>(result, 2)
    }


    @Test
    fun dayOfWeek_Abbreviated_1_Digit() {
        val result = underTest.parsePattern("E")

        assertDayOfWeekComponent(result, DayOfWeekStyle.Abbreviated)
    }

    @Test
    fun dayOfWeek_Abbreviated_2_Digits() {
        val result = underTest.parsePattern("EE")

        assertDayOfWeekComponent(result, DayOfWeekStyle.Abbreviated)
    }

    @Test
    fun dayOfWeek_Abbreviated_3_Digits() {
        val result = underTest.parsePattern("EEE")

        assertDayOfWeekComponent(result, DayOfWeekStyle.Abbreviated)
    }

    @Test
    fun dayOfWeek_Wide() {
        val result = underTest.parsePattern("EEEE")

        assertDayOfWeekComponent(result, DayOfWeekStyle.Wide)
    }

    @Test
    fun dayOfWeek_Narrow() {
        val result = underTest.parsePattern("EEEEE")

        assertDayOfWeekComponent(result, DayOfWeekStyle.Narrow)
    }

    @Test
    fun dayOfWeek_Short() {
        val result = underTest.parsePattern("EEEEEE")

        assertDayOfWeekComponent(result, DayOfWeekStyle.Short)
    }


    @Test
    fun hour_12Hours_1_Based_MinDigits() {
        val result = underTest.parsePattern("h")

        assertHourComponent(result, HourStyle.Hour_12_Start_1, 1)
    }

    @Test
    fun hour_12Hours_1_Based_2Digits() {
        val result = underTest.parsePattern("hh")

        assertHourComponent(result, HourStyle.Hour_12_Start_1, 2)
    }

    @Test
    fun hour_24Hours_0_Based_MinDigits() {
        val result = underTest.parsePattern("H")

        assertHourComponent(result, HourStyle.Hour_24_Start_0, 1)
    }

    @Test
    fun hour_24Hours_0_Based_2Digits() {
        val result = underTest.parsePattern("HH")

        assertHourComponent(result, HourStyle.Hour_24_Start_0, 2)
    }

    @Test
    fun hour_12Hours_0_Based_MinDigits() {
        val result = underTest.parsePattern("K")

        assertHourComponent(result, HourStyle.Hour_12_Start_0, 1)
    }

    @Test
    fun hour_12Hours_0_Based_2Digits() {
        val result = underTest.parsePattern("KK")

        assertHourComponent(result, HourStyle.Hour_12_Start_0, 2)
    }

    @Test
    fun hour_24Hours_1_Based_MinDigits() {
        val result = underTest.parsePattern("k")

        assertHourComponent(result, HourStyle.Hour_24_Start_1, 1)
    }

    @Test
    fun hour_24Hours_1_Based_2Digits() {
        val result = underTest.parsePattern("kk")

        assertHourComponent(result, HourStyle.Hour_24_Start_1, 2)
    }


    @Test
    fun minute_MinDigits() {
        val result = underTest.parsePattern("m")

        assertComponentWithMinLength<MinuteComponent>(result, 1)
    }

    @Test
    fun minute_2Digits() {
        val result = underTest.parsePattern("mm")

        assertComponentWithMinLength<MinuteComponent>(result, 2)
    }


    @Test
    fun second_MinDigits() {
        val result = underTest.parsePattern("s")

        assertComponentWithMinLength<SecondComponent>(result, 1)
    }

    @Test
    fun second_2Digits() {
        val result = underTest.parsePattern("ss")

        assertComponentWithMinLength<SecondComponent>(result, 2)
    }


    @Test
    fun fractionalSecond_MinDigits() {
        val result = underTest.parsePattern("S")

        assertComponent<FractionalSecondComponent>(result) {
            assertThat(it.length).isEqualTo(1)
        }
    }

    @Test
    fun fractionalSecond_3Digits() {
        val result = underTest.parsePattern("SSS")

        assertComponent<FractionalSecondComponent>(result) {
            assertThat(it.length).isEqualTo(3)
        }
    }

    @Test
    fun fractionalSecond_9Digits() {
        val result = underTest.parsePattern("SSSSSSSSS")

        assertComponent<FractionalSecondComponent>(result) {
            assertThat(it.length).isEqualTo(9)
        }
    }


    @Test
    fun literalString() {
        val result = underTest.parsePattern("'T'")

        assertLiteralComponent(result, "T")
    }

    @Test
    fun unclosedLiteralString() {
        assertFailsWith<IllegalArgumentException> {
            underTest.parsePattern("'T")
        }
    }

    @Test
    fun escapedQuote() {
        val result = underTest.parsePattern("''")

        assertLiteralComponent(result, "'")
    }


    private inline fun <reified T : DateTimeFormatPatternComponentWithMinLength> assertComponentWithMinLength(result: DateTimeFormatPattern, minLength: Int) {
        assertComponent<T>(result) {
            assertThat(it.minLength).isEqualTo(minLength)
        }
    }

    private fun assertMonthComponent(result: DateTimeFormatPattern, style: MonthStyle) {
        assertComponent<MonthComponent>(result) { component ->
            assertThat(component.style).isEqualByComparingTo(style)
        }
    }

    private fun assertDayOfWeekComponent(result: DateTimeFormatPattern, style: DayOfWeekStyle) {
        assertComponent<DayOfWeekComponent>(result) { component ->
            assertThat(component.style).isEqualByComparingTo(style)
        }
    }

    private fun assertHourComponent(result: DateTimeFormatPattern, style: HourStyle, minLength: Int) {
        assertComponent<HourComponent>(result) { component ->
            assertThat(component.style).isEqualByComparingTo(style)
            assertThat(component.minLength).isEqualTo(minLength)
        }
    }

    private fun assertLiteralComponent(result: DateTimeFormatPattern, literal: String) {
        assertComponent<LiteralComponent>(result) {
            assertThat(it.literal).isEqualTo(literal)
        }
    }

    private inline fun <reified T : DateTimeFormatPatternComponent> assertComponent(result: DateTimeFormatPattern, asserter: (T) -> Unit) {
        assertThat(result.components).hasSize(1)

        val component = result.components.first()
        assertThat(component).isInstanceOf<T>()

        asserter(component as T)
    }

}