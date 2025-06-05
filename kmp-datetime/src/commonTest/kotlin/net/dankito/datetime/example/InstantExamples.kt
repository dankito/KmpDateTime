package net.dankito.datetime.example

import net.dankito.datetime.*

@Suppress("UNUSED_VARIABLE")
class InstantExamples {

    fun create() {
        // by seconds since epoch (1970-01-01 midnight)
        val instant = Instant(epochSeconds = 1_745_000_000, nanosecondsOfSecond = 654)
        // by milliseconds since epoch
        val instant2 = Instant.ofEpochMilli(1_745_000_000_654) // returns Instant(1_745_000_000, 654_000_000)
        // with default values
        val instant3 = Instant(1_745_000_000) // default values for nanosecondsOfSecond is 0; returns Instant(1_745_000_000, 0)
        // by datetime
        val instant4 = LocalDateTime(2015, Month.October, 21, 12, 15).toInstantAtUtc()

        // current instant
        val now = Instant.now()

        // Instant constants
        val epoch = Instant.Epoch

        // creating from ISO 8601 instant string
        val fromIsoString = Instant.parse("2015-10-21T12:15:30.654Z")
        // lenient parsing, returns null in case of invalid ISO string (Instant.parse() throws an exception in case of invalid ISO string)
        val fromIsoStringLenient = LocalTime.parseOrNull("2015-10-21T12:15:30.654") // returns null, does not end with 'Z'
    }

    fun convert() {
        val instant = Instant(1_445_418_487, 654_000_000)

        // components
        val epochSeconds: Long = instant.epochSeconds // returns 1_445_418_487
        val nanosecondsOfSecond: Int = instant.nanosecondsOfSecond // returns 654_000_000
        val epochMillis: Long = instant.toEpochMilliseconds() // returns 1_445_418_487_654

        // to LocalDateTime (from there you can convert it further to LocalDate and LocalTime, see LocalDateTime examples)
        val dateTimeAtUtc: LocalDateTime = instant.toLocalDateTimeAtUtc()
        val dateTimeAtSystemTimeZone: LocalDateTime = instant.toLocalDateTimeAtSystemTimeZone()

        // to ISO 8601 string
        val isoString = instant.isoString // returns "2015-10-21T12:15:30.654Z"
        val isoStringAtSystemTimeZone = instant.isoStringAtSystemTimeZone // result depends on your system's time zone, e.g. for CET "2015-10-21T11:15:30.654Z"
    }

}