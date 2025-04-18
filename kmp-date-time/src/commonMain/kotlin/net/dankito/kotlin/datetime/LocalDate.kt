package net.dankito.kotlin.datetime

data class LocalDate(
    val year: Int,
    val monthNumber: Int,
    val dayOfMonth: Int
) {

    constructor(year: Int, month: Month, dayOfMonth: Int) : this(year, month.number, dayOfMonth)

    init {
        require(monthNumber in 1..12) { "Invalid month, value must be in bounds [1-12]: $monthNumber" }
        require(dayOfMonth in 1..31) { "Invalid day, value must be in bounds [1-31]: $dayOfMonth" }
    }


    val month: Month by lazy { Month.forNumber(monthNumber) }


    override fun toString() = "$year-$monthNumber-$dayOfMonth"
}