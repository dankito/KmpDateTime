package net.dankito.kotlin.datetime.format

import assertk.all
import assertk.assertFailure
import assertk.assertions.hasClass
import assertk.assertions.messageContains
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


    private fun assertIllegalArgumentException(message: String, block: () -> Unit) {
        assertFailure {
            block()
        }.all {
            hasClass(IllegalArgumentException::class)
            messageContains(message)
        }
    }

}