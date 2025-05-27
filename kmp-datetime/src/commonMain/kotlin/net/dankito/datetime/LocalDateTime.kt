package net.dankito.datetime

import kotlinx.serialization.Serializable
import net.dankito.datetime.format.DateTimeFormatter
import net.dankito.datetime.format.DateTimeParser
import net.dankito.datetime.platform.Platform
import net.dankito.datetime.serialization.LocalDateTimeDelegatingSerializer

@Serializable(with = LocalDateTimeDelegatingSerializer::class)
data class LocalDateTime(
    val year: Int,
    val month: Month,
    val day: Int = 1,
    val hour: Int = 0,
    val minute: Int = 0,
    val second: Int = 0,
    val nanosecondOfSecond: Int = 0
) : Comparable<LocalDateTime> {

    companion object {
        fun now(): LocalDateTime = Platform.getLocalDateTimeNow()

        fun parse(isoDateTime: String): LocalDateTime = DateTimeParser.parseIsoDateTimeString(isoDateTime)

        fun parseOrNull(isoDateTime: String): LocalDateTime? = DateTimeParser.parseIsoDateTimeStringOrNull(isoDateTime)
    }


    constructor(year: Int, monthNumber: Int = 1, day: Int = 1, hour: Int = 0, minute: Int = 0, second: Int = 0, nanosecondOfSecond: Int = 0)
            : this(year, Month.forNumber(monthNumber), day, hour, minute, second, nanosecondOfSecond) {
        require(monthNumber in 1..12) { "Invalid monthNumber, value must be in bounds [1-12]: $monthNumber" }
        require(day in 1..31) { "Invalid day, value must be in bounds [1-31]: $day" }
    }

    constructor(date: LocalDate, hour: Int = 0, minute: Int = 0, second: Int = 0, nanosecondOfSecond: Int = 0) :
            this(date.year, date.month, date.day, hour, minute, second, nanosecondOfSecond)

    constructor(date: LocalDate, time: LocalTime) : this(date.year, date.month, date.day, time.hour, time.minute, time.second, time.nanosecondOfSecond)

    init {
        require(day in 1..31) { "Invalid day, value must be in bounds [1-31]: $day" }
    }


    val monthNumber: Int = month.number

    val dayOfWeek: DayOfWeek? by lazy { date.dayOfWeek }

    val dayOfYear: Int? by lazy { date.dayOfYear }

    val isInDaylightSavingTime: Boolean by lazy { date.isInDaylightSavingTime }

    val date: LocalDate by lazy { LocalDate(year, month, day) }

    val time: LocalTime by lazy { LocalTime(hour, minute, second, nanosecondOfSecond) }

    val isoString: String by lazy { DateTimeFormatter.Default.toIsoString(this) }

    fun toInstantAtUtc(): Instant = Platform.toInstantAtUtc(this)

    fun toInstantAtSystemTimeZone(): Instant = Platform.toInstantAtSystemTimeZone(this)


    override fun compareTo(other: LocalDateTime): Int {
        val dateCompare = date.compareTo(other.date)
        if (dateCompare != 0) return dateCompare

        return time.compareTo(other.time)
    }


    override fun toString() = isoString

}