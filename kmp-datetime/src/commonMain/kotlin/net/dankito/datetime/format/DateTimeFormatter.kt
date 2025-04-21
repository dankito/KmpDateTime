package net.dankito.datetime.format

import net.dankito.datetime.Instant
import net.dankito.datetime.LocalDate
import net.dankito.datetime.LocalDateTime
import net.dankito.datetime.LocalTime

object DateTimeFormatter {

    fun toIsoString(date: LocalDate): String = with(date) {
        "${ofLength(year, 4)}-${ofLength(monthNumber, 2)}-${ofLength(day, 2)}"
    }

    fun toDotSeparatedIsoString(date: LocalDate): String = with(date) {
        "${ofLength(year, 4)}.${ofLength(monthNumber, 2)}.${ofLength(day, 2)}"
    }

    fun toIsoString(time: LocalTime): String = with(time) {
            "${ofLength(hour, 2)}:${ofLength(minute, 2)}:${ofLength(second, 2)}" +
            "" +
            if (nanosecondOfSecond > 0) {
                ".${ofLength(nanosecondOfSecond, 9).trimEnd('0')}"
            } else {
                ""
            }
    }

    fun toIsoString(dateTime: LocalDateTime) = with(dateTime) {
        date.isoString + "T" + time.isoString
    }

    fun toIsoString(instant: Instant): String =
        toIsoString(instant.toLocalDateTimeAtUtc()) + "Z"

    fun toIsoStringAtSystemTimeZone(instant: Instant): String =
        toIsoString(instant.toLocalDateTimeAtSystemTimeZone()) + "Z"


    private fun ofLength(value: Int, length: Int): String = value.toString().padStart(length, '0')

}