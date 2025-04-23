package net.dankito.datetime

import assertk.assertThat
import assertk.assertions.isEqualTo
import kotlin.test.Test
import kotlin.test.assertFailsWith

class UtcOffsetTest {

    @Test
    fun parse_Zulu() {
        val result = UtcOffset.parse("Z")

        assertOffset(result, 0)
    }


    @Test
    fun parse_IsoOffset_HoursMinutesSeconds_Positive() {
        val result = UtcOffset.parse("+01:02:03")

        assertOffset(result, 1, 2, 3)
    }

    @Test
    fun parse_IsoOffset_HoursMinutes_Positive() {
        val result = UtcOffset.parse("+01:02")

        assertOffset(result, 1, 2)
    }

    @Test
    fun parse_IsoOffset_Hours_Positive() {
        val result = UtcOffset.parse("+01")

        assertOffset(result, 1)
    }

    @Test
    fun parse_IsoOffset_HoursMinutesSeconds_Negative() {
        val result = UtcOffset.parse("-01:02:03")

        assertOffset(result, -1, 2, 3)
    }

    @Test
    fun parse_IsoOffset_HoursMinutes_Negative() {
        val result = UtcOffset.parse("-01:02")

        assertOffset(result, -1, 2)
    }

    @Test
    fun parse_IsoOffset_Hours_Negative() {
        val result = UtcOffset.parse("-01")

        assertOffset(result, -1)
    }

    @Test
    fun parse_IsoOffset_HoursOnlyOneDigit() {
        assertFailsWith(IllegalArgumentException::class) {
            UtcOffset.parse("+1:02:03")
        }
    }

    @Test
    fun parse_IsoOffset_HoursThreeDigits() {
        assertFailsWith(IllegalArgumentException::class) {
            UtcOffset.parse("+012:02:03")
        }
    }

    @Test
    fun parse_IsoOffset_HoursNotAnInteger() {
        assertFailsWith(IllegalArgumentException::class) {
            UtcOffset.parse("+1.5:02:03")
        }
    }

    @Test
    fun parse_IsoOffset_MinutesOnlyOneDigit() {
        assertFailsWith(IllegalArgumentException::class) {
            UtcOffset.parse("+01:2:03")
        }
    }

    @Test
    fun parse_IsoOffset_MinutesThreeDigits() {
        assertFailsWith(IllegalArgumentException::class) {
            UtcOffset.parse("+01:023:03")
        }
    }

    @Test
    fun parse_IsoOffset_MinutesNotAnInteger() {
        assertFailsWith(IllegalArgumentException::class) {
            UtcOffset.parse("+01:2a:03")
        }
    }

    @Test
    fun parse_IsoOffset_SecondsOnlyOneDigit() {
        assertFailsWith(IllegalArgumentException::class) {
            UtcOffset.parse("+01:02:3")
        }
    }

    @Test
    fun parse_IsoOffset_SecondsThreeDigits() {
        assertFailsWith(IllegalArgumentException::class) {
            UtcOffset.parse("+01:02:034")
        }
    }

    @Test
    fun parse_IsoOffset_SecondsNotAnInteger() {
        assertFailsWith(IllegalArgumentException::class) {
            UtcOffset.parse("+01:02:true")
        }
    }

    @Test
    fun parse_IsoOffset_MoreThanThreeParts() {
        assertFailsWith(IllegalArgumentException::class) {
            UtcOffset.parse("+01:02:03:04")
        }
    }


    @Test
    fun parse_IsoOffsetBasic_HoursMinutesSeconds_Positive() {
        val result = UtcOffset.parse("+010203")

        assertOffset(result, 1, 2, 3)
    }

    @Test
    fun parse_IsoOffsetBasic_HoursMinutes_Positive() {
        val result = UtcOffset.parse("+0102")

        assertOffset(result, 1, 2)
    }

    @Test
    fun parse_IsoOffsetBasic_Hours_Positive() {
        val result = UtcOffset.parse("+01")

        assertOffset(result, 1)
    }

    @Test
    fun parse_IsoOffsetBasic_HoursMinutesSeconds_Negative() {
        val result = UtcOffset.parse("-010203")

        assertOffset(result, -1, 2, 3)
    }

    @Test
    fun parse_IsoOffsetBasic_HoursMinutes_Negative() {
        val result = UtcOffset.parse("-0102")

        assertOffset(result, -1, 2)
    }

    @Test
    fun parse_IsoOffsetBasic_Hours_Negative() {
        val result = UtcOffset.parse("-01")

        assertOffset(result, -1)
    }

    @Test
    fun parse_IsoOffsetBasic_HoursOnlyOneDigit() {
        assertFailsWith(IllegalArgumentException::class) {
            UtcOffset.parse("+10203")
        }
    }

    @Test
    fun parse_IsoOffsetBasic_HoursThreeDigits() {
        assertFailsWith(IllegalArgumentException::class) {
            UtcOffset.parse("+0120203")
        }
    }

    @Test
    fun parse_IsoOffsetBasic_HoursNotAnInteger() {
        assertFailsWith(IllegalArgumentException::class) {
            UtcOffset.parse("+1.50203")
        }
    }

    @Test
    fun parse_IsoOffsetBasic_MinutesOnlyOneDigit() {
        assertFailsWith(IllegalArgumentException::class) {
            UtcOffset.parse("+01203")
        }
    }

    @Test
    fun parse_IsoOffsetBasic_MinutesThreeDigits() {
        assertFailsWith(IllegalArgumentException::class) {
            UtcOffset.parse("+0102303")
        }
    }

    @Test
    fun parse_IsoOffsetBasic_MinutesNotAnInteger() {
        assertFailsWith(IllegalArgumentException::class) {
            UtcOffset.parse("+012a03")
        }
    }

    @Test
    fun parse_IsoOffsetBasic_SecondsOnlyOneDigit() {
        assertFailsWith(IllegalArgumentException::class) {
            UtcOffset.parse("+01023")
        }
    }

    @Test
    fun parse_IsoOffsetBasic_SecondsThreeDigits() {
        assertFailsWith(IllegalArgumentException::class) {
            UtcOffset.parse("+0102034")
        }
    }

    @Test
    fun parse_IsoOffsetBasic_SecondsNotAnInteger() {
        assertFailsWith(IllegalArgumentException::class) {
            UtcOffset.parse("+0102true")
        }
    }

    @Test
    fun parse_IsoOffsetBasic_MoreThanThreeParts() {
        assertFailsWith(IllegalArgumentException::class) {
            UtcOffset.parse("+01020304")
        }
    }



    @Test
    fun parse_FourDigitOffset_Positive() {
        val result = UtcOffset.parse("+0102")

        assertOffset(result, 1, 2)
    }

    @Test
    fun parse_FourDigitOffset_Negative() {
        val result = UtcOffset.parse("-0102")

        assertOffset(result, -1, 2)
    }

    @Test
    fun parse_FourDigitOffset_HoursOnlyOneDigit() {
        assertFailsWith(IllegalArgumentException::class) {
            UtcOffset.parse("+102")
        }
    }

    @Test
    fun parse_FourDigitOffset_HoursThreeDigits() {
        assertFailsWith(IllegalArgumentException::class) {
            UtcOffset.parse("+01202")
        }
    }

    @Test
    fun parse_FourDigitOffset_HoursNotAnInteger() {
        assertFailsWith(IllegalArgumentException::class) {
            UtcOffset.parse("+1.502")
        }
    }

    @Test
    fun parse_FourDigitOffset_MinutesOnlyOneDigit() {
        assertFailsWith(IllegalArgumentException::class) {
            UtcOffset.parse("+012")
        }
    }

    @Test
    fun parse_FourDigitOffset_MinutesThreeDigits() {
        assertFailsWith(IllegalArgumentException::class) {
            UtcOffset.parse("+01023")
        }
    }

    @Test
    fun parse_FourDigitOffset_MinutesNotAnInteger() {
        assertFailsWith(IllegalArgumentException::class) {
            UtcOffset.parse("+012a")
        }
    }


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


    @Test
    fun isoString_UTC_Zulu() {
        val offset = UtcOffset(0)

        val result = offset.isoString

        assertThat(result).isEqualTo("Z")
    }

    @Test
    fun isoString_Positive_WithSeconds() {
        val offset = UtcOffset(hours = 1, minutes = 2, seconds = 3)

        val result = offset.isoString

        assertThat(result).isEqualTo("+01:02:03")
    }

    @Test
    fun isoString_Positive_WithoutSeconds() {
        val offset = UtcOffset(hours = 1, minutes = 2, seconds = 0)

        val result = offset.isoString

        assertThat(result).isEqualTo("+01:02")
    }

    @Test
    fun isoString_Negative_WithSeconds() {
        val offset = UtcOffset(hours = -1, minutes = 2, seconds = 3)

        val result = offset.isoString

        assertThat(result).isEqualTo("-01:02:03")
    }

    @Test
    fun isoString_Negative_WithoutSeconds() {
        val offset = UtcOffset(hours = -1, minutes = 2, seconds = 0)

        val result = offset.isoString

        assertThat(result).isEqualTo("-01:02")
    }


    private fun assertOffset(offset: UtcOffset, expectedHours: Int, expectedMinutes: Int = 0, expectedSeconds: Int = 0,
                             totalSeconds: Int = if (expectedHours < 0) expectedHours * 3600 - expectedMinutes * 60 - expectedSeconds else expectedHours * 3600 + expectedMinutes * 60 + expectedSeconds) {
        assertThat(offset.hours).isEqualTo(expectedHours)
        assertThat(offset.minutes).isEqualTo(expectedMinutes)
        assertThat(offset.seconds).isEqualTo(expectedSeconds)
        assertThat(offset.totalSeconds).isEqualTo(totalSeconds)
    }

}