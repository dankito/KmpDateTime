package net.dankito.kotlin.datetime

import net.dankito.kotlin.datetime.format.DateTimeFormatter

data class LocalDateTime(
    val year: Int,
    val month: Month = Month.January,
    val day: Int = 1,
    val hour: Int = 0,
    val minute: Int = 0,
    val second: Int = 0,
    val nanosecondOfSecond: Int = 0
) : Comparable<LocalDateTime> {

    constructor(year: Int, monthNumber: Int = 1, day: Int = 1, hour: Int = 0, minute: Int = 0, second: Int = 0, nanosecondOfSecond: Int = 0)
            : this(year, Month.forNumber(monthNumber), day, hour, minute, second, nanosecondOfSecond)

    constructor(date: LocalDate, hour: Int = 0, minute: Int = 0, second: Int = 0, nanosecondOfSecond: Int = 0) :
            this(date.year, date.month, date.day, hour, minute, second, nanosecondOfSecond)

    constructor(date: LocalDate, time: LocalTime) : this(date.year, date.month, date.day, time.hour, time.minute, time.second, time.nanosecondOfSecond)

    init {
        require(day in 1..31) { "Invalid day, value must be in bounds [1-31]: $day" }
    }


    val monthNumber: Int by lazy { month.number }

    val date: LocalDate by lazy { LocalDate(year, month, day) }

    val time: LocalTime by lazy { LocalTime(hour, minute, second, nanosecondOfSecond) }

    val isoString: String by lazy { DateTimeFormatter.toIsoString(this) }


    override fun compareTo(other: LocalDateTime): Int {
        val dateCompare = date.compareTo(other.date)
        if (dateCompare != 0) return dateCompare

        return time.compareTo(other.time)
    }


    override fun toString() = isoString

}