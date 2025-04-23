package net.dankito.datetime

import assertk.assertThat
import assertk.assertions.isEqualTo
import kotlin.test.Test
import kotlin.test.assertFailsWith

@OptIn(ExperimentalMultiplatform::class)
class UtcOffsetTest {

    @Test
    fun fromTotalSeconds_PositiveHours() {
        val result = UtcOffset(14 * 3_600 + 45 * 60 + 17)

        assertOffset(result, 14, 45, 17)
    }

    @Test
    fun fromTotalSeconds_NegativeHours() {
        val result = UtcOffset(-5 * 3_600 - 30 * 60 - 48)

        assertOffset(result, -5, 30, 48)
    }

    @Test
    fun totalSecondsOutOfRange_PositiveTotalSeconds() {
        assertFailsWith(IllegalArgumentException::class) {
            UtcOffset(64801)
        }
    }

    @Test
    fun totalSecondsOutOfRange_NegativeTotalSeconds() {
        assertFailsWith(IllegalArgumentException::class) {
            UtcOffset(-64801)
        }
    }


    @Test
    fun hoursAreCorrect_PositiveHours() {
        val result = UtcOffset(hours = 14)

        assertOffset(result, 14)
    }

    @Test
    fun hoursAreCorrect_NegativeHours() {
        val result = UtcOffset(hours = -5)

        assertOffset(result, -5)
    }

    @Test
    fun hoursAreCorrect_FromTotalSeconds() {
        val result = UtcOffset(7 * 3_600)

        assertOffset(result, 7)
    }

    @Test
    fun hoursOutOfRange_PositiveHours() {
        assertFailsWith(IllegalArgumentException::class) {
            UtcOffset(hours = 19)
        }
    }

    @Test
    fun hoursOutOfRange_NegativeHours() {
        assertFailsWith(IllegalArgumentException::class) {
            UtcOffset(hours = -19)
        }
    }


    @Test
    fun minutesAreCorrect() {
        val result = UtcOffset(minutes = 40)

        assertOffset(result, 0, 40)
    }

    @Test
    fun minutesAreCorrect_FromTotalSeconds() {
        val result = UtcOffset(20 * 60)

        assertOffset(result, 0, 20)
    }

    @Test
    fun minutesOutOfRange_NegativeMinutes() {
        assertFailsWith(IllegalArgumentException::class) {
            UtcOffset(minutes = -1)
        }
    }

    @Test
    fun minutesOutOfRange_Greater59() {
        assertFailsWith(IllegalArgumentException::class) {
            UtcOffset(minutes = 60)
        }
    }


    @Test
    fun secondsAreCorrect() {
        val result = UtcOffset(seconds = 40)

        assertOffset(result, 0, 0, 40)
    }

    @Test
    fun secondsAreCorrect_FromTotalSeconds() {
        val result = UtcOffset(20)

        assertOffset(result, 0, 0, 20)
    }

    @Test
    fun secondsOutOfRange_NegativeSeconds() {
        assertFailsWith(IllegalArgumentException::class) {
            UtcOffset(seconds = -1)
        }
    }

    @Test
    fun secondsOutOfRange_Greater59() {
        assertFailsWith(IllegalArgumentException::class) {
            UtcOffset(seconds = 60)
        }
    }


    private fun assertOffset(offset: UtcOffset, expectedHours: Int, expectedMinutes: Int = 0, expectedSeconds: Int = 0,
                             totalSeconds: Int = if (expectedHours < 0) expectedHours * 3600 - expectedMinutes * 60 - expectedSeconds else expectedHours * 3600 + expectedMinutes * 60 + expectedSeconds) {
        assertThat(offset.hours).isEqualTo(expectedHours)
        assertThat(offset.minutes).isEqualTo(expectedMinutes)
        assertThat(offset.seconds).isEqualTo(expectedSeconds)
        assertThat(offset.totalSeconds).isEqualTo(totalSeconds)
    }

}