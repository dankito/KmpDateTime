package net.dankito.datetime.example

import net.dankito.datetime.*

class LocalDateTimeExamples {

    fun create() {
        // month as number from 1 - 12
        val dateTime = LocalDateTime(year = 2015, monthNumber = 10, day = 21, hour = 12, minute = 15, second = 30, nanosecondOfSecond = 654)
        // month as enum
        val dateTime2 = LocalDateTime(2015, Month.October, 21, 12, 15)
        // with default values
        val dateTime3 = LocalDateTime(2015) // default values for month and day are January and 1, for all other values 0; returns LocalDateTime(2015, Month.January, 1, 0, 0, 0, 0)
        // by date and time
        val dateTime4 = LocalDateTime(LocalDate(2015, Month.October, 21), LocalTime(12, 15, 30, 654))

        // current datetime
        val now = LocalDateTime.now()

        // creating from ISO 8601 datetime string
        val fromIsoString = LocalDateTime.parse("2015-10-21T12:15:30.654")
        // lenient parsing, returns null in case of invalid ISO string (LocalDateTime.parse() throws an exception in case of invalid ISO string)
        val fromIsoStringLenient = LocalTime.parseOrNull("2015-10-21T12:15:30.654GMT") // returns null, 'GMT' is not a valid part of an ISO datetime string
    }

    fun convert() {
        val dateTime = LocalDateTime(2015, Month.October, 21, 12, 15, 30)

        // get date or time
        val date = dateTime.date
        val time = dateTime.time

        // components
        val year: Int = dateTime.year // returns 2015
        val month: Month = dateTime.month // returns Month.October
        val monthNumber: Int = dateTime.monthNumber // returns 10
        val day: Int = dateTime.day // returns 21
        // the day of week of this date; returns null in case of an invalid date
        val dayOfWeek: DayOfWeek? = dateTime.dayOfWeek // returns DayOfWeek.Wednesday
        val hour: Int = dateTime.hour // returns 12
        val minute: Int = dateTime.minute // returns 15
        val second: Int = dateTime.second // returns 30
        val nanosecondOfSecond: Int = dateTime.nanosecondOfSecond // returns 0

        // to Instant
        val instantAtUtc: Instant = dateTime.toInstantAtUtc()
        val instantAtSystemTimeZone: Instant = dateTime.toInstantAtSystemTimeZone()

        // to ISO 8601 string
        val isoString = dateTime.isoString // returns "2015-10-21T12:15:30"
    }

}