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
    /**
     * Formats the given [LocalTime] as an ISO 8601-compliant string.
     *
     * ISO 8601 does not specify a fixed number of digits for the fractional second part. The implementation rule is:
     * - If [LocalTime.nanosecondOfSecond] is `0`, the fractional second part is omitted.
     * - If the nanosecond component is non-zero, by default all available digits are used, with no truncation or padding.
     * - To instead group fractional digits based on resolution — 3 for milliseconds, 6 for microseconds,
     * or 9 for nanoseconds — set [useGroupedFractionDigits] to `true`.
     *
     * @param time the [LocalTime] instance to format
     * @param useGroupedFractionDigits whether to format the fractional seconds using
     * 3/6/9 digits depending on resolution (e.g., milliseconds, microseconds, nanoseconds)
     * @return the ISO 8601 formatted time string
     */
    fun toIsoString(time: LocalTime, useGroupedFractionDigits: Boolean = false): String = with(time) {
            "${ofLength(hour, 2)}:${ofLength(minute, 2)}:${ofLength(second, 2)}" +
            formatNanosecondOfSecond(nanosecondOfSecond, useGroupedFractionDigits)
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

    private fun formatNanosecondOfSecond(nanosecondOfSecond: Int, useGroupedFractionDigits: Boolean): String = when (nanosecondOfSecond) {
        0 -> ""
        else -> {
            val formatted = ".${ofLength(nanosecondOfSecond, 9)}"
            when (useGroupedFractionDigits) {
                false -> formatted.trimEnd('0')
                true -> if (formatted.endsWith("000000")) {
                    formatted.dropLast(6)
                } else if (formatted.endsWith("000")) {
                    formatted.dropLast(3)
                } else {
                    formatted
                }
            }
        }
    }

}