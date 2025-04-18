package net.dankito.kotlin.datetime

data class LocalDate(
    val year: Int,
    val month: Month,
    val dayOfMonth: Int
) {

    constructor(year: Int, monthNumber: Int, dayOfMonth: Int) : this(year, Month.forNumber(monthNumber), dayOfMonth)

    init {
        require(dayOfMonth in 1..31) { "Invalid day, value must be in bounds [1-31]: $dayOfMonth" }
    }


    val monthNumber: Int by lazy { month.number }


    override fun toString() = "$year-$monthNumber-$dayOfMonth"
}