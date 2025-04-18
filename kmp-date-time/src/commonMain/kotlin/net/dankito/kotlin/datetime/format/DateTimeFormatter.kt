package net.dankito.kotlin.datetime.format

import net.dankito.kotlin.datetime.LocalDate
import net.dankito.kotlin.datetime.LocalDateTime
import net.dankito.kotlin.datetime.LocalTime

object DateTimeFormatter {

    fun toIsoString(date: LocalDate): String = with(date) { "$year-$monthNumber-$day" }

    fun toIsoString(time: LocalTime): String = with(time) {
        hour.toString().padStart(2, '0') +
            ":${minute.toString().padStart(2, '0')}" +
            ":${second.toString().padStart(2, '0')}" +
            if (nanosecondOfSecond > 0) {
                ".${nanosecondOfSecond.toString().padStart(9, '0').trimEnd('0')}"
            } else {
                ""
            }
    }

    fun toIsoString(dateTime: LocalDateTime) = with(dateTime) {
        date.isoString + "T" + time.isoString
    }

}