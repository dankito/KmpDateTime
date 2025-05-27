package net.dankito.datetime.format.pattern

import assertk.assertThat
import assertk.assertions.hasSize
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
    fun dayOfMonth_Min1Digit() {
        val result = underTest.parsePattern("d")

        assertComponentWithMinLength<DayOfMonthComponent>(result, 1)
    }

    @Test
    fun dayOfMonth_2Digits() {
        val result = underTest.parsePattern("dd")

        assertComponentWithMinLength<DayOfMonthComponent>(result, 2)
    }


    @Test
    fun minute_Min1Digit() {
        val result = underTest.parsePattern("m")

        assertComponentWithMinLength<MinuteComponent>(result, 1)
    }

    @Test
    fun minute_2Digits() {
        val result = underTest.parsePattern("mm")

        assertComponentWithMinLength<MinuteComponent>(result, 2)
    }


    @Test
    fun second_Min1Digit() {
        val result = underTest.parsePattern("s")

        assertComponentWithMinLength<SecondComponent>(result, 1)
    }

    @Test
    fun second_2Digits() {
        val result = underTest.parsePattern("ss")

        assertComponentWithMinLength<SecondComponent>(result, 2)
    }


    @Test
    fun fractionalSecond_Min1Digit() {
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