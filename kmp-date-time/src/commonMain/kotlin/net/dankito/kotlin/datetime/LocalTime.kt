package net.dankito.kotlin.datetime

import net.dankito.kotlin.datetime.format.DateTimeFormatter
import net.dankito.kotlin.datetime.format.DateTimeParser

data class LocalTime(
    val hour: Int,
    val minute: Int = 0,
    val second: Int = 0,
    val nanosecondOfSecond: Int = 0
) : Comparable<LocalTime> {
    companion object {
        val StartOfDay = LocalTime(0, 0)
        val Midnight = StartOfDay
        val Min = StartOfDay

        val Noon = LocalTime(12, 0)

        val EndOfDay = LocalTime(23, 59, 59, 999_999_999)
        val Max = EndOfDay


        fun parse(isoTime: String): LocalTime = DateTimeParser.parseIsoTimeString(isoTime)

        fun parseOrNull(isoTime: String): LocalTime? = DateTimeParser.parseIsoTimeStringOrNull(isoTime)
    }


    val isoString: String by lazy { DateTimeFormatter.toIsoString(this) }


    override fun compareTo(other: LocalTime): Int {
        val hourCompare = hour.compareTo(other.hour)
        if (hourCompare != 0) return hourCompare

        val minuteCompare = minute.compareTo(other.minute)
        if (minuteCompare != 0) return minuteCompare

        val secondCompare = second.compareTo(other.second)
        if (secondCompare != 0) return secondCompare

        return nanosecondOfSecond.compareTo(other.nanosecondOfSecond)
    }


    override fun toString() = isoString

}