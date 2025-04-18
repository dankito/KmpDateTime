package net.dankito.kotlin.datetime

data class LocalDateTime(
    val year: Int,
    val month: Month,
    val dayOfMonth: Int,
    val hour: Int = 0,
    val minute: Int = 0,
    val second: Int = 0,
    val nanosecondOfSecond: Int = 0
) {

    constructor(year: Int, monthNumber: Int, dayOfMonth: Int, hour: Int = 0, minute: Int = 0, second: Int = 0, nanosecondOfSecond: Int = 0)
            : this(year, Month.forNumber(monthNumber), dayOfMonth, hour, minute, second, nanosecondOfSecond)

    init {
        require(dayOfMonth in 1..31) { "Invalid day, value must be in bounds [1-31]: $dayOfMonth" }
    }


    val monthNumber: Int by lazy { month.number }

    val date: LocalDate by lazy { LocalDate(year, month, dayOfMonth) }

    val time: LocalTime by lazy { LocalTime(hour, minute, second, nanosecondOfSecond) }

}