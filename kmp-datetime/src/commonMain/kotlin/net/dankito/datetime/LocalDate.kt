package net.dankito.datetime

import kotlinx.serialization.Serializable
import net.dankito.datetime.format.DateTimeFormatter
import net.dankito.datetime.format.DateTimeParser
import net.dankito.datetime.platform.Platform
import net.dankito.datetime.serialization.LocalDateDelegatingSerializer

@Serializable(with = LocalDateDelegatingSerializer::class)
data class LocalDate(
    val year: Int,
    val month: Month,
    val day: Int = 1
) : Comparable<LocalDate> {

    companion object {
        fun today(): LocalDate = Platform.getLocalDateNow()

        fun parse(isoDate: String): LocalDate = DateTimeParser.parseIsoDateString(isoDate)

        fun parseOrNull(isoDate: String): LocalDate? = DateTimeParser.parseIsoDateStringOrNull(isoDate)
    }


    constructor(year: Int, monthNumber: Int = 1, day: Int = 1) : this(year, Month.forNumber(monthNumber), day) {
        require(monthNumber in 1..12) { "Invalid monthNumber, value must be in bounds [1-12]: $monthNumber" }
    }

    init {
        require(day in 1..31) { "Invalid day, value must be in bounds [1-31]: $day" }
    }


    val monthNumber: Int by lazy { month.number }

    /**
     * The day of week of this date. May returns `null` in case of an invalid date.
     */
    val dayOfWeek: DayOfWeek? by lazy {
        Platform.getDayOfWeekDayNumber(this)?.let { DayOfWeek.forDayNumber(it) }
    }

    val isoString: String by lazy { DateTimeFormatter.toIsoString(this) }

    /**
     * Variant of [isoString] that uses '.' instead of '-' as separator.
     */
    val isoStringDotSeparated: String by lazy { DateTimeFormatter.toDotSeparatedIsoString(this) }

    fun atStartOfDay(): LocalDateTime = LocalDateTime(year, month, day, 0, 0, 0, 0)

    fun atTime(hour: Int, minute: Int = 0, second: Int = 0, nanosecondOfSecond: Int = 0): LocalDateTime =
        LocalDateTime(year, month, day, hour, minute, second, nanosecondOfSecond)

    fun atTime(time: LocalTime): LocalDateTime = LocalDateTime(this, time)


    override fun compareTo(other: LocalDate): Int {
        val yearCompare = year.compareTo(other.year)
        if (yearCompare != 0) return yearCompare

        val monthCompare = month.compareTo(other.month)
        if (monthCompare != 0) return monthCompare

        return day.compareTo(other.day)
    }


    override fun toString() = isoString
}