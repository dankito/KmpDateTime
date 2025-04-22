package net.dankito.datetime.calculation

import assertk.assertThat
import assertk.assertions.isEqualTo
import kotlin.test.Test

class DateTimeCalculatorTest {

    @Test
    fun instantFromEpochSeconds_LessThan9DecimalPlaces() {
        val result = DateTimeCalculator.instantFromEpochSeconds(123.456)

        assertThat(result.epochSeconds).isEqualTo(123)
        assertThat(result.nanosecondsOfSecond).isEqualTo(456_000_000)
    }

    @Test
    fun instantFromEpochSeconds_MoreThan9DecimalPlaces() {
        val result = DateTimeCalculator.instantFromEpochSeconds(123.456_789_123_456)

        assertThat(result.epochSeconds).isEqualTo(123)
        assertThat(result.nanosecondsOfSecond).isEqualTo(456_789_123)
    }

}