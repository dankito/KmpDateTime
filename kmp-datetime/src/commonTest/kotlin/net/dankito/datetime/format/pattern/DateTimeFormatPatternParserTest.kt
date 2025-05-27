package net.dankito.datetime.format.pattern

import assertk.assertThat
import assertk.assertions.*
import net.dankito.datetime.format.pattern.component.*
import kotlin.test.Test
import kotlin.test.assertFailsWith

class DateTimeFormatPatternParserTest {

    private val underTest = DateTimeFormatPatternParser()


    @Test
    fun year_2Digits() {
        val result = underTest.parse("yy")

        assertComponentWithMinLength<YearComponent>(result, 2)
    }

    @Test
    fun year_4Digits() {
        val result = underTest.parse("yyyy")

        assertComponentWithMinLength<YearComponent>(result, 4)
    }


    @Test
    fun month_minDigits() {
        val result = underTest.parse("M")

        assertMonthComponent(result, MonthStyle.NumericMinDigits)
    }

    @Test
    fun month_2Digits() {
        val result = underTest.parse("MM")

        assertMonthComponent(result, MonthStyle.Numeric2Digits)
    }

    @Test
    fun month_Abbreviated() {
        val result = underTest.parse("MMM")

        assertMonthComponent(result, MonthStyle.Abbreviated)
    }

    @Test
    fun month_Wide() {
        val result = underTest.parse("MMMM")

        assertMonthComponent(result, MonthStyle.Wide)
    }

    @Test
    fun month_Narrow() {
        val result = underTest.parse("MMMMM")

        assertMonthComponent(result, MonthStyle.Narrow)
    }


    @Test
    fun dayOfMonth_MinDigits() {
        val result = underTest.parse("d")

        assertComponentWithMinLength<DayOfMonthComponent>(result, 1)
    }

    @Test
    fun dayOfMonth_2Digits() {
        val result = underTest.parse("dd")

        assertComponentWithMinLength<DayOfMonthComponent>(result, 2)
    }


    @Test
    fun dayOfWeek_Abbreviated_1_Digit() {
        val result = underTest.parse("E")

        assertDayOfWeekComponent(result, DayOfWeekStyle.Abbreviated)
    }

    @Test
    fun dayOfWeek_Abbreviated_2_Digits() {
        val result = underTest.parse("EE")

        assertDayOfWeekComponent(result, DayOfWeekStyle.Abbreviated)
    }

    @Test
    fun dayOfWeek_Abbreviated_3_Digits() {
        val result = underTest.parse("EEE")

        assertDayOfWeekComponent(result, DayOfWeekStyle.Abbreviated)
    }

    @Test
    fun dayOfWeek_Wide() {
        val result = underTest.parse("EEEE")

        assertDayOfWeekComponent(result, DayOfWeekStyle.Wide)
    }

    @Test
    fun dayOfWeek_Narrow() {
        val result = underTest.parse("EEEEE")

        assertDayOfWeekComponent(result, DayOfWeekStyle.Narrow)
    }

    @Test
    fun dayOfWeek_Short() {
        val result = underTest.parse("EEEEEE")

        assertDayOfWeekComponent(result, DayOfWeekStyle.Short)
    }


    @Test
    fun hour_12Hours_1_Based_MinDigits() {
        val result = underTest.parse("h")

        assertHourComponent(result, HourStyle.Hour_12_Start_1, 1)
    }

    @Test
    fun hour_12Hours_1_Based_2Digits() {
        val result = underTest.parse("hh")

        assertHourComponent(result, HourStyle.Hour_12_Start_1, 2)
    }

    @Test
    fun hour_24Hours_0_Based_MinDigits() {
        val result = underTest.parse("H")

        assertHourComponent(result, HourStyle.Hour_24_Start_0, 1)
    }

    @Test
    fun hour_24Hours_0_Based_2Digits() {
        val result = underTest.parse("HH")

        assertHourComponent(result, HourStyle.Hour_24_Start_0, 2)
    }

    @Test
    fun hour_12Hours_0_Based_MinDigits() {
        val result = underTest.parse("K")

        assertHourComponent(result, HourStyle.Hour_12_Start_0, 1)
    }

    @Test
    fun hour_12Hours_0_Based_2Digits() {
        val result = underTest.parse("KK")

        assertHourComponent(result, HourStyle.Hour_12_Start_0, 2)
    }

    @Test
    fun hour_24Hours_1_Based_MinDigits() {
        val result = underTest.parse("k")

        assertHourComponent(result, HourStyle.Hour_24_Start_1, 1)
    }

    @Test
    fun hour_24Hours_1_Based_2Digits() {
        val result = underTest.parse("kk")

        assertHourComponent(result, HourStyle.Hour_24_Start_1, 2)
    }


    @Test
    fun minute_MinDigits() {
        val result = underTest.parse("m")

        assertComponentWithMinLength<MinuteComponent>(result, 1)
    }

    @Test
    fun minute_2Digits() {
        val result = underTest.parse("mm")

        assertComponentWithMinLength<MinuteComponent>(result, 2)
    }


    @Test
    fun second_MinDigits() {
        val result = underTest.parse("s")

        assertComponentWithMinLength<SecondComponent>(result, 1)
    }

    @Test
    fun second_2Digits() {
        val result = underTest.parse("ss")

        assertComponentWithMinLength<SecondComponent>(result, 2)
    }


    @Test
    fun fractionalSecond_MinDigits() {
        val result = underTest.parse("S")

        assertComponent<FractionalSecondComponent>(result) {
            assertThat(it.length).isEqualTo(1)
        }
    }

    @Test
    fun fractionalSecond_3Digits() {
        val result = underTest.parse("SSS")

        assertComponent<FractionalSecondComponent>(result) {
            assertThat(it.length).isEqualTo(3)
        }
    }

    @Test
    fun fractionalSecond_9Digits() {
        val result = underTest.parse("SSSSSSSSS")

        assertComponent<FractionalSecondComponent>(result) {
            assertThat(it.length).isEqualTo(9)
        }
    }


    @Test
    fun literalString() {
        val result = underTest.parse("'T'")

        assertLiteralComponent(result, "T")
    }

    @Test
    fun unclosedLiteralString() {
        assertFailsWith<IllegalArgumentException> {
            underTest.parse("'T")
        }
    }

    @Test
    fun escapedQuote() {
        val result = underTest.parse("''")

        assertLiteralComponent(result, "'")
    }


    @Test
    fun iso8601DateTimePattern() {
        val result = underTest.parse("yyyy-MM-dd'T'HH:mm:ss.SSS")

        with(result) {
            assertThat(components).hasSize(13)

            // date
            assertThat(components[0]).isInstanceOf<YearComponent>()
            assertThat((components[0] as YearComponent).minLength).isEqualTo(4)

            assertThat(components[1]).isInstanceOf<LiteralComponent>()
            assertThat((components[1] as LiteralComponent).literal).isEqualTo("-")

            assertThat(components[2]).isInstanceOf<MonthComponent>()
            assertThat((components[2] as MonthComponent).style).isEqualByComparingTo(MonthStyle.Numeric2Digits)

            assertThat(components[3]).isInstanceOf<LiteralComponent>()
            assertThat((components[3] as LiteralComponent).literal).isEqualTo("-")

            assertThat(components[4]).isInstanceOf<DayOfMonthComponent>()
            assertThat((components[4] as DayOfMonthComponent).minLength).isEqualTo(2)


            assertThat(components[5]).isInstanceOf<LiteralComponent>()
            assertThat((components[5] as LiteralComponent).literal).isEqualTo("T")
            assertThat((components[5] as LiteralComponent).masked).isTrue()


            // time

            assertThat(components[6]).isInstanceOf<HourComponent>()
            assertThat((components[6] as HourComponent).minLength).isEqualTo(2)
            assertThat((components[6] as HourComponent).style).isEqualByComparingTo(HourStyle.Hour_24_Start_0)

            assertThat(components[7]).isInstanceOf<LiteralComponent>()
            assertThat((components[7] as LiteralComponent).literal).isEqualTo(":")

            assertThat(components[8]).isInstanceOf<MinuteComponent>()
            assertThat((components[8] as MinuteComponent).minLength).isEqualTo(2)

            assertThat(components[9]).isInstanceOf<LiteralComponent>()
            assertThat((components[9] as LiteralComponent).literal).isEqualTo(":")

            assertThat(components[10]).isInstanceOf<SecondComponent>()
            assertThat((components[10] as SecondComponent).minLength).isEqualTo(2)

            assertThat(components[11]).isInstanceOf<LiteralComponent>()
            assertThat((components[11] as LiteralComponent).literal).isEqualTo(".")

            assertThat(components[12]).isInstanceOf<FractionalSecondComponent>()
            assertThat((components[12] as FractionalSecondComponent).length).isEqualTo(3)
        }
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