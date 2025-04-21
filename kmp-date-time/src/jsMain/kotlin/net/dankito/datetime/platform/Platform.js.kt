package net.dankito.datetime.platform

import net.dankito.datetime.*
import kotlin.js.Date

internal actual object Platform {

    actual val timeSinceEpochPrecision = TimeSinceEpochPrecision.Milliseconds


    actual fun getInstantNow(): Instant {
        val millisSinceEpoch = Date.now()

        return instantOfEpochMilli(millisSinceEpoch)
    }

    actual fun getLocalDateNow(): LocalDate =
        getDateNow().toLocalDate()

    actual fun getLocalTimeNow(): LocalTime =
        getDateNow().toLocalTime()

    actual fun getLocalDateTimeNow(): LocalDateTime =
        getDateNow().toLocalDateTime()


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

        return instantOfEpochMilli(millisSinceEpoch)
    }

    actual fun toLocalDateTimeAtUtc(instant: Instant): LocalDateTime =
        instant.toJsDate().toLocalDateTimeAtUtc()

    actual fun toLocalDateTimeAtSystemTimeZone(instant: Instant): LocalDateTime =
        instant.toJsDate().toLocalDateTime()


    private fun getDateNow() = Date(Date.now())


    private fun instantOfEpochMilli(millisSinceEpoch: Double): Instant =
        Instant.ofEpochMilli(millisSinceEpoch.toLong())

}