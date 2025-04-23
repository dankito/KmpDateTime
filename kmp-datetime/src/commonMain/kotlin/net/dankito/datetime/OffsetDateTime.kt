package net.dankito.datetime

import net.dankito.datetime.format.DateTimeFormatter
import net.dankito.datetime.format.DateTimeParser

@ExperimentalMultiplatform
data class OffsetDateTime(
    val year: Int,
    val month: Month,
    val day: Int = 1,
    val hour: Int = 0,
    val minute: Int = 0,
    val second: Int = 0,
    val nanosecondOfSecond: Int = 0,
    val offset: UtcOffset = UtcOffset.UTC
) {
    companion object {
        fun parse(offsetDateTimeString: String): OffsetDateTime = DateTimeParser.parseOffsetDateTimeString(offsetDateTimeString)

        fun parseOrNull(offsetDateTimeString: String): OffsetDateTime? = DateTimeParser.parseOffsetDateTimeStringOrNull(offsetDateTimeString)
    }


    constructor(dateTime: LocalDateTime, offset: UtcOffset = UtcOffset.UTC) :
            this(dateTime.year, dateTime.month, dateTime.day, dateTime.hour, dateTime.minute, dateTime.second, dateTime.nanosecondOfSecond, offset)

    constructor(date: LocalDate, time: LocalTime, offset: UtcOffset = UtcOffset.UTC) :
            this(date.year, date.month, date.day, time.hour, time.minute, time.second, time.nanosecondOfSecond, offset)

    constructor(date: LocalDate, hour: Int = 0, minute: Int = 0, second: Int = 0, nanosecondOfSecond: Int = 0, offset: UtcOffset = UtcOffset.UTC) :
            this(date.year, date.month, date.day, hour, minute, second, nanosecondOfSecond, offset)

    constructor(year: Int, monthNumber: Int = 1, day: Int = 1, hour: Int = 0, minute: Int = 0, second: Int = 0, nanosecondOfSecond: Int = 0, offset: UtcOffset = UtcOffset.UTC) :
            this(year, Month.forNumber(monthNumber), day, hour, minute, second, nanosecondOfSecond, offset) {
        require(monthNumber in 1..12) { "Invalid monthNumber, value must be in bounds [1-12]: $monthNumber" }
        require(day in 1..31) { "Invalid day, value must be in bounds [1-31]: $day" }
    }

    init {
        require(day in 1..31) { "Invalid day, value must be in bounds [1-31]: $day" }
    }


    val dateTime: LocalDateTime by lazy { LocalDateTime(year, month, day, hour, minute, second, nanosecondOfSecond) }

    val isoString: String by lazy { DateTimeFormatter.toIsoString(this) }


    override fun toString() = isoString

}
