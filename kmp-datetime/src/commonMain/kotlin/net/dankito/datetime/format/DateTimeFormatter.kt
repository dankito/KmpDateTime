package net.dankito.datetime.format

import net.dankito.datetime.*
import net.dankito.datetime.format.pattern.DateTimeComponentFormatter
import net.dankito.datetime.format.pattern.DateTimeFormatPattern
import kotlin.math.abs

class DateTimeFormatter(
    protected val componentFormatter: DateTimeComponentFormatter = DateTimeComponentFormatter.Default
) {

    companion object {
        val Default = DateTimeFormatter()
    }


    fun toIsoString(date: LocalDate): String =
        componentFormatter.format(date, DateTimeFormatPattern.IsoDateComponents)

    fun toDotSeparatedIsoString(date: LocalDate): String =
        componentFormatter.format(date, DateTimeFormatPattern.IsoDateDotSeparatedComponents)

    // LocalTime has varying fraction of second digits, therefore we cannot use a fixed pattern like DateTimeFormatPattern.IsoTimePattern here
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


    fun toIsoString(offset: UtcOffset): String = with(offset) {
        if (totalSeconds == 0) {
            "Z"
        } else {
            (if (hours < 0) "-" else "+") +
                    ofLength(abs(hours), 2) +
                    ":" + ofLength(abs(minutes), 2) +
                    if (seconds != 0) ":${ofLength(seconds, 2)}" else ""
        }
    }

    fun toIsoString(dateTime: OffsetDateTime): String = toIsoString(dateTime.dateTime) + toIsoString(dateTime.offset)


    private fun ofLength(value: Int, length: Int): String = value.toString().padStart(length, '0')

}