package net.dankito.datetime

import kotlinx.serialization.Serializable
import net.dankito.datetime.format.DateTimeFormatter
import net.dankito.datetime.format.DateTimeParser
import net.dankito.datetime.platform.Platform
import net.dankito.datetime.serialization.LocalTimeDelegatingSerializer

@Serializable(with = LocalTimeDelegatingSerializer::class)
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


        fun now(): LocalTime = Platform.getLocalTimeNow()

        fun parse(isoTime: String): LocalTime = DateTimeParser.parseIsoTimeString(isoTime)

        fun parseOrNull(isoTime: String): LocalTime? = DateTimeParser.parseIsoTimeStringOrNull(isoTime)
    }


    val isoString: String by lazy { DateTimeFormatter.Default.toIsoString(this, true) }

    fun atDate(year: Int, month: Month, day: Int = 1): LocalDateTime =
        LocalDateTime(year, month, day, hour, minute, second, nanosecondOfSecond)

    fun atDate(year: Int, monthNumber: Int = 1, day: Int = 1): LocalDateTime =
        LocalDateTime(year, monthNumber, day, hour, minute, second, nanosecondOfSecond)

    fun atDate(date: LocalDate): LocalDateTime = LocalDateTime(date, this)


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