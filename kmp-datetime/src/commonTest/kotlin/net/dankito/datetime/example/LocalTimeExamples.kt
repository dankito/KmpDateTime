package net.dankito.datetime.example

import net.dankito.datetime.LocalDate
import net.dankito.datetime.LocalDateTime
import net.dankito.datetime.LocalTime
import net.dankito.datetime.Month

@Suppress("UNUSED_VARIABLE")
class LocalTimeExamples {

    fun create() {
        val time = LocalTime(hour = 12, minute = 15, second = 30, nanosecondOfSecond = 123)
        // with default values
        val time2 = LocalTime(0) // default values for minute, second and nanosecond of second are 0; returns LocalTime(0, 0, 0, 0)

        // current time
        val now = LocalTime.now()

        // time constants
        val midnight = LocalTime.Midnight // synonym for LocalTime.StartOfDay
        val noon = LocalTime.Noon
        val endOfDay = LocalTime.EndOfDay // equals LocalTime(23, 59, 59, 999_999_999)

        // creating from ISO 8601 time string
        val fromIsoString = LocalTime.parse("12:15:30.654")
        // lenient parsing, returns null in case of invalid ISO string (LocalTime.parse() throws an exception in case of invalid ISO string)
        val fromIsoStringLenient = LocalTime.parseOrNull("12:15:30.654+02:00") // returns null, '+02:00' is not a valid part of an ISO time string
    }

    fun convert() {
        val time = LocalTime(12, 15, 30, nanosecondOfSecond = 654_000_000)

        // components
        val hour: Int = time.hour // returns 12
        val minute: Int = time.minute // returns 15
        val second: Int = time.second // returns 30
        val nanosecondOfSecond: Int = time.nanosecondOfSecond // returns 654_000_000

        // to LocalTimeTime (from there you can convert it further to Instant or OffsetDateTime, see LocalDateTime examples)
        val byYearMonthDay: LocalDateTime = time.atDate(2015, Month.October, 21)
        val byLocalDate: LocalDateTime = time.atDate(LocalDate(2015, Month.October, 21))

        // to ISO 8601 string
        val isoString = time.isoString // returns "12:15:30.654"
    }

}