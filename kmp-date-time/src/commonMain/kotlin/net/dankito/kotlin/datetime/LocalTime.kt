package net.dankito.kotlin.datetime

import net.dankito.kotlin.datetime.format.DateTimeFormatter

data class LocalTime(
    val hour: Int,
    val minute: Int,
    val second: Int = 0,
    val nanosecondOfSecond: Int = 0
) {
    companion object {
        val StartOfDay = LocalTime(0, 0)
        val Midnight = StartOfDay
        val Min = StartOfDay

        val Noon = LocalTime(12, 0)

        val EndOfDay = LocalTime(23, 59, 59, 999_999_999)
        val Max = EndOfDay
    }


    val isoString: String by lazy { DateTimeFormatter.toIsoString(this) }


    override fun toString() = isoString

}