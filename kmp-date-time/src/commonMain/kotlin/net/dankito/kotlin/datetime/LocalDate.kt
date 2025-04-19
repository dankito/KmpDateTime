package net.dankito.kotlin.datetime

import kotlinx.serialization.Serializable
import net.dankito.kotlin.datetime.format.DateTimeFormatter
import net.dankito.kotlin.datetime.format.DateTimeParser
import net.dankito.kotlin.datetime.serialization.LocalDateIso8601Serializer

@Serializable(with = LocalDateIso8601Serializer::class)
data class LocalDate(
    val year: Int,
    val month: Month = Month.January,
    val day: Int = 1
) : Comparable<LocalDate> {

    companion object {
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

    val isoString: String by lazy { DateTimeFormatter.toIsoString(this) }


    override fun compareTo(other: LocalDate): Int {
        val yearCompare = year.compareTo(other.year)
        if (yearCompare != 0) return yearCompare

        val monthCompare = month.compareTo(other.month)
        if (monthCompare != 0) return monthCompare

        return day.compareTo(other.day)
    }


    override fun toString() = isoString
}