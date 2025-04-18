package net.dankito.kotlin.datetime

import net.dankito.kotlin.datetime.format.DateTimeFormatter

data class LocalDate(
    val year: Int,
    val month: Month,
    val day: Int
) {

    constructor(year: Int, monthNumber: Int, day: Int) : this(year, Month.forNumber(monthNumber), day)

    init {
        require(day in 1..31) { "Invalid day, value must be in bounds [1-31]: $day" }
    }


    val monthNumber: Int by lazy { month.number }

    val isoString: String by lazy { DateTimeFormatter.toIsoString(this) }


    override fun toString() = isoString
}