package net.dankito.kotlin.datetime.platform

import net.dankito.kotlin.datetime.*
import kotlin.js.Date

internal actual object Platform {

    actual val timeSinceEpochPrecision = TimeSinceEpochPrecision.Milliseconds


    actual fun getInstantNow(): Instant {
        val millisSinceEpoch = Date.now()

        return Instant.ofEpochMilli(millisSinceEpoch.toLong())
    }

    actual fun getLocalDateNow(): LocalDate {
        val now = Date(Date.now())

        return toLocalDate(now)
    }

    actual fun getLocalTimeNow(): LocalTime {
        val now = Date(Date.now())

        return toLocalTime(now)
    }

    actual fun getLocalDateTimeNow(): LocalDateTime {
        val now = Date(Date.now())

        return LocalDateTime(toLocalDate(now), toLocalTime(now))
    }

    private fun toLocalDate(now: Date) =
        // getMonth() = month as number (0 - 11) -> + 1; getDate() = day as number (1 - 31), getDay() = weekday (0 - 6)
        LocalDate(now.getFullYear(), now.getMonth() + 1, now.getDate())

    private fun toLocalTime(now: Date) =
        LocalTime(now.getHours(), now.getMinutes(), now.getSeconds(), now.getMilliseconds() * 1_000_000)


    actual fun getDayOfWeekDayNumber(date: LocalDate): Int? {
        val jsDate = Date(date.year, date.monthNumber - 1, date.day)

        // 0 = Sunday
        return jsDate.getDay().let {
            if (it == 0) 7
            else it
        }
    }


    actual fun toInstantAtUtc(dateTime: LocalDateTime): Instant {
        // do not use Date(), it interprets the values in system's timezone rather than in UTC
        val millisSinceEpoch = Date.UTC(dateTime.year, dateTime.monthNumber - 1, dateTime.day,
            dateTime.hour, dateTime.minute, dateTime.second, dateTime.nanosecondOfSecond / 1_000_000)

        return Instant.ofEpochMilli(millisSinceEpoch.toLong())
    }

}