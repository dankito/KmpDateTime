package net.dankito.datetime.example

import net.dankito.datetime.*

class LocalDateExamples {

    fun create() {
        // month as number from 1 - 12
        val date = LocalDate(2015, 10, 21)
        // month as enum
        val date2 = LocalDate(2015, Month.October, 21)
        // with default values
        val date3 = LocalDate(2015) // default values for month and day are January and 1; returns LocalDate(2015, Month.January, 1)

        // current date
        val today = LocalDate.today()

        // creating from ISO 8601 date string
        val fromIsoString = LocalDate.parse("2015-10-21")
        // lenient parsing, returns null in case of invalid ISO string (LocalDate.parse() throws an exception in case of invalid ISO string)
        val fromIsoStringLenient = LocalTime.parseOrNull("2015-10-21GMT") // returns null, 'GMT' is not a valid part of an ISO date string
    }

    fun convert() {
        val date = LocalDate(2015, Month.October, 21)

        // components
        val year: Int = date.year // returns 2015
        val month: Month = date.month // returns Month.October
        val monthNumber: Int = date.monthNumber // returns 10
        val day: Int = date.day // returns 21
        // the day of week of this date; returns null in case of an invalid date
        val dayOfWeek: DayOfWeek? = date.dayOfWeek // returns DayOfWeek.Wednesday

        // to LocalTimeTime (from there you can convert it further to Instant or OffsetDateTime, see LocalDateTime examples)
        val atMidnight: LocalDateTime = date.atStartOfDay()
        val byHourMinutesSeconds: LocalDateTime = date.atTime(12, 15, 30)
        val byLocalTime: LocalDateTime = date.atTime(LocalTime.Noon)

        // to ISO 8601 string
        val isoString = date.isoString // returns "2015-10-21"
        val isoStringDotSeparated = date.isoStringDotSeparated // variant that uses '.' instead of '-' as separator; returns "2015.10.21"
    }

}