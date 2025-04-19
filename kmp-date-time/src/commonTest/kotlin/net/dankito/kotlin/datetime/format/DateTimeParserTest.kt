package net.dankito.kotlin.datetime.format

import assertk.all
import assertk.assertFailure
import assertk.assertions.hasClass
import assertk.assertions.messageContains
import net.dankito.kotlin.datetime.format.DateTimeParser.LocalDateTimePattern
import kotlin.test.Test

class DateTimeParserTest {

    @Test
    fun parseDate_TooFewDashes() {
        assertIllegalArgumentException("but contained 1 dash(es) instead of 2.") {
            DateTimeParser.parseIsoDateString("2015-10")
        }
    }

    @Test
    fun parseDate_TooManyDashes() {
        assertIllegalArgumentException("but contained 3 dash(es) instead of 2.") {
            DateTimeParser.parseIsoDateString("2015-10-15-")
        }
    }

    @Test
    fun parseDate_YearTooShort() {
        assertIllegalArgumentException("but the year part '215' had 3 instead of 4 digits.") {
            DateTimeParser.parseIsoDateString("215-10-15")
        }
    }

    @Test
    fun parseDate_YearTooLong() {
        assertIllegalArgumentException("and the year part '20150000000' may not exceed 10 decimal places but had 11.") {
            DateTimeParser.parseIsoDateString("20150000000-10-15")
        }
    }

    @Test
    fun parseDate_YearNotAnInt() {
        assertIllegalArgumentException("but the year part 'true' could not be converted to an Int.") {
            DateTimeParser.parseIsoDateString("true-10-15")
        }
    }

    @Test
    fun parseDate_MonthTooShort() {
        assertIllegalArgumentException("but the month part '1' had 1 instead of 2 digits.") {
            DateTimeParser.parseIsoDateString("2015-1-15")
        }
    }

    @Test
    fun parseDate_MonthTooLong() {
        assertIllegalArgumentException("but the month part '100' had 3 instead of 2 digits.") {
            DateTimeParser.parseIsoDateString("2015-100-15")
        }
    }

    @Test
    fun parseDate_MonthNotAnInt() {
        assertIllegalArgumentException("but the month part '1a' could not be converted to an Int.") {
            DateTimeParser.parseIsoDateString("2015-1a-15")
        }
    }

    @Test
    fun parseDate_DayTooShort() {
        assertIllegalArgumentException("but the day part '5' had 1 instead of 2 digits.") {
            DateTimeParser.parseIsoDateString("2015-10-5")
        }
    }

    @Test
    fun parseDate_DayTooLong() {
        assertIllegalArgumentException("but the day part '157' had 3 instead of 2 digits.") {
            DateTimeParser.parseIsoDateString("2015-10-157")
        }
    }

    @Test
    fun parseDate_DayNotAnInt() {
        assertIllegalArgumentException("but the day part '1.' could not be converted to an Int.") {
            DateTimeParser.parseIsoDateString("2015-10-1.")
        }
    }


    @Test
    fun parseTime_TooFewColons() {
        assertIllegalArgumentException("but contained 1 colon(s) instead of 2.") {
            DateTimeParser.parseIsoTimeString("10:15")
        }
    }

    @Test
    fun parseTime_TooManyColons() {
        assertIllegalArgumentException("but contained 3 colon(s) instead of 2.") {
            DateTimeParser.parseIsoTimeString("10:15:47:123")
        }
    }

    @Test
    fun parseTime_HourTooShort() {
        assertIllegalArgumentException("but the hour part '1' had 1 instead of 2 digits.") {
            DateTimeParser.parseIsoTimeString("1:15:47.123456789")
        }
    }

    @Test
    fun parseTime_HourTooLong() {
        assertIllegalArgumentException("but the hour part '100' had 3 instead of 2 digits.") {
            DateTimeParser.parseIsoTimeString("100:15:47.123456789")
        }
    }

    @Test
    fun parseTime_HourNotAnInt() {
        assertIllegalArgumentException("but the hour part '1a' could not be converted to an Int.") {
            DateTimeParser.parseIsoTimeString("1a:15:47.123456789")
        }
    }

    @Test
    fun parseTime_MinuteTooShort() {
        assertIllegalArgumentException("but the minute part '5' had 1 instead of 2 digits.") {
            DateTimeParser.parseIsoTimeString("10:5:47.123456789")
        }
    }

    @Test
    fun parseTime_MinuteTooLong() {
        assertIllegalArgumentException("but the minute part '157' had 3 instead of 2 digits.") {
            DateTimeParser.parseIsoTimeString("10:157:47.123456789")
        }
    }

    @Test
    fun parseTime_MinuteNotAnInt() {
        assertIllegalArgumentException("but the minute part '1.' could not be converted to an Int.") {
            DateTimeParser.parseIsoTimeString("10:1.:47.123456789")
        }
    }

    @Test
    fun parseTime_SecondTooShort() {
        assertIllegalArgumentException("but the second part '4' had 1 instead of 2 digits.") {
            DateTimeParser.parseIsoTimeString("10:15:4.123456789")
        }
    }

    @Test
    fun parseTime_SecondTooLong() {
        assertIllegalArgumentException("but the second part '479' had 3 instead of 2 digits.") {
            DateTimeParser.parseIsoTimeString("10:15:479.123456789")
        }
    }

    @Test
    fun parseTime_SecondNotAnInt() {
        assertIllegalArgumentException("but the second part '4d' could not be converted to an Int.") {
            DateTimeParser.parseIsoTimeString("10:15:4d.123456789")
        }
    }

    @Test
    fun parseTime_NanosecondOfSecondTooShort() {
        assertIllegalArgumentException("but the nanosecond of second part '' had 0 instead of 1-9 digits.") {
            DateTimeParser.parseIsoTimeString("10:15:47.")
        }
    }

    @Test
    fun parseTime_NanosecondOfSecondTooLong() {
        assertIllegalArgumentException("but the nanosecond of second part '1234567890' had 10 instead of 1-9 digits.") {
            DateTimeParser.parseIsoTimeString("10:15:47.1234567890")
        }
    }

    @Test
    fun parseTime_NanosecondOfSecondNotAnInt() {
        assertIllegalArgumentException("but the nanosecond of second part 'true' could not be converted to an Int.") {
            DateTimeParser.parseIsoTimeString("10:15:47.true")
        }
    }


    @Test
    fun parseDateTime_TooFewTs() {
        assertIllegalArgumentException(" representation '$LocalDateTimePattern' but contained 0 'T' instead of 1.") {
            DateTimeParser.parseIsoDateTimeString("2015-10-15S10:15:47.123456789")
        }
    }

    @Test
    fun parseDateTime_TooManyTs() {
        assertIllegalArgumentException(" representation '$LocalDateTimePattern' but contained 2 'T' instead of 1.") {
            DateTimeParser.parseIsoDateTimeString("2015-10-15TT10:15:47.123456789")
        }
    }

    @Test
    fun parseDateTime_TooManyTs_CaseInsensitive() {
        assertIllegalArgumentException(" representation '$LocalDateTimePattern' but contained 2 'T' instead of 1.") {
            DateTimeParser.parseIsoDateTimeString("2015-10-15tt10:15:47.123456789")
        }
    }

    @Test
    fun parseDateTime_InvalidDate() {
        assertIllegalArgumentException(" representation '$LocalDateTimePattern' but the day part '1' had 1 instead of 2 digits.") {
            DateTimeParser.parseIsoDateTimeString("2015-10-1T10:15:47.123456789")
        }
    }

    @Test
    fun parseDateTime_InvalidTime() {
        assertIllegalArgumentException(" representation '$LocalDateTimePattern' but the second part '479' had 3 instead of 2 digits.") {
            DateTimeParser.parseIsoDateTimeString("2015-10-15T10:15:479.123456789")
        }
    }


    private fun assertIllegalArgumentException(message: String, block: () -> Unit) {
        assertFailure {
            block()
        }.all {
            hasClass(IllegalArgumentException::class)
            messageContains(message)
        }
    }

}