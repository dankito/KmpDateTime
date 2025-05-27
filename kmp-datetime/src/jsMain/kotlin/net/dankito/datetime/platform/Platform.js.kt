package net.dankito.datetime.platform

import net.dankito.datetime.*
import net.dankito.datetime.calculation.DateTimeCalculator
import kotlin.js.Date
import kotlin.math.floor

internal actual object Platform {

    actual val timeSinceEpochPrecision = TimeSinceEpochPrecision.Milliseconds


    actual fun getInstantNow(): Instant {
        val millisSinceEpoch = Date.now()

        return Instant.ofEpochMilli(millisSinceEpoch.toLong())
    }

    actual fun getLocalDateNow(): LocalDate =
        getDateNow().toLocalDate()

    actual fun getLocalTimeNow(): LocalTime =
        getDateNow().toLocalTime()

    actual fun getLocalDateTimeNow(): LocalDateTime =
        getDateNow().toLocalDateTime()


    actual fun getDayOfWeekDayNumber(date: LocalDate): Int? {
        val jsDate = date.toJsDateInSystemTimeZone()

        // 0 = Sunday
        return jsDate.getDay().let {
            if (it == 0) 7
            else it
        }
    }

    actual fun getDayOfYear(date: LocalDate): Int? {
        // do not use date.toJsDateInSystemTimeZone(), it has issues with days with dayligh-saving time
        val millisSinceEpoch = date.millisSinceEpochUtc()
        val millisSinceEpochForStartOfYear = date.atStartOfYear().millisSinceEpochUtc()
        val diffInMillis = millisSinceEpoch - millisSinceEpochForStartOfYear

        return floor(diffInMillis / DateTimeCalculator.MillisecondsPerDay).toInt() + 1
    }

    actual fun isInDaylightSavingTime(date: LocalDate): Boolean =
        getTimeZoneOffset(date) - getTimeZoneOffset(date.atStartOfYear()) != 0

    fun getTimeZoneOffset(date: LocalDate): Int =
        date.toJsDateInSystemTimeZone().getTimezoneOffset()


    actual fun toInstantAtUtc(dateTime: LocalDateTime): Instant {
        // do not use Date(), it interprets the values in system's timezone rather than in UTC
        val millisSinceEpoch = Date.UTC(dateTime.year, dateTime.monthNumber - 1, dateTime.day,
            dateTime.hour, dateTime.minute, dateTime.second, dateTime.nanosecondOfSecond / 1_000_000)

        return instantOfEpochMilli(millisSinceEpoch, dateTime)
    }

    actual fun toInstantAtSystemTimeZone(dateTime: LocalDateTime): Instant {
        val jsDate = Date(dateTime.year, dateTime.monthNumber - 1, dateTime.day,
            dateTime.hour, dateTime.minute, dateTime.second, dateTime.nanosecondOfSecond / 1_000_000)

        val millisSinceEpoch = jsDate.getTime()

        return instantOfEpochMilli(millisSinceEpoch, dateTime)
    }

    actual fun toLocalDateTimeAtUtc(instant: Instant): LocalDateTime =
        instant.toJsDate().toLocalDateTimeAtUtc(instant.nanosecondsOfSecond)

    actual fun toLocalDateTimeAtSystemTimeZone(instant: Instant): LocalDateTime =
        instant.toJsDate().toLocalDateTime(instant.nanosecondsOfSecond)


    private fun getDateNow() = Date(Date.now())


    private fun instantOfEpochMilli(millisSinceEpoch: Double, originalDateTime: LocalDateTime): Instant =
        Instant((millisSinceEpoch / 1_000).toLong(), originalDateTime.nanosecondOfSecond)

}