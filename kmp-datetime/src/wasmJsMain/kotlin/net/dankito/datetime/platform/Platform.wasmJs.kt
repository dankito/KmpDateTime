package net.dankito.datetime.platform

import net.dankito.datetime.*


internal fun getMillisSinceEpoch(): Double =
    js("Date.now()")

internal fun getDateNow(): JsDate =
    js("new Date(Date.now())")

internal fun createDateFromMillisSinceEpoch(millisSinceEpoch: Double): JsDate =
    js("new Date(millisSinceEpoch)")

internal fun createDateInSystemTimeZone(year: Int, month: Int, day: Int, hour: Int = 0, minute: Int = 0, second: Int = 0, millisecond: Int = 0): JsDate =
    js("new Date(year, month, day, hour, minute, second, millisecond)")

internal fun createDateInUTC(year: Int, month: Int, day: Int, hour: Int = 0, minute: Int = 0, second: Int = 0, millisecond: Int = 0): Double =
    js("Date.UTC(year, month, day, hour, minute, second, millisecond)")


internal actual object Platform {

    actual val timeSinceEpochPrecision = TimeSinceEpochPrecision.Milliseconds


    actual fun getInstantNow(): Instant {
        val millisSinceEpoch = getMillisSinceEpoch()

        return Instant.ofEpochMilli(millisSinceEpoch.toLong())
    }

    actual fun getLocalDateNow(): LocalDate =
        getDateNow().toLocalDate()

    actual fun getLocalTimeNow(): LocalTime =
        getDateNow().toLocalTime()

    actual fun getLocalDateTimeNow(): LocalDateTime =
        getDateNow().toLocalDateTime()


    actual fun getDayOfWeekDayNumber(date: LocalDate): Int? {
        val jsDate = createDateInSystemTimeZone(date.year, date.monthNumber - 1, date.day)

        // 0 = Sunday
        return jsDate.getDay().let {
            if (it == 0) 7
            else it
        }
    }


    actual fun toInstantAtUtc(dateTime: LocalDateTime): Instant {
        // do not use JsDate(), it interprets the values in system's timezone rather than in UTC
        val millisSinceEpoch = createDateInUTC(dateTime.year, dateTime.monthNumber - 1, dateTime.day,
            dateTime.hour, dateTime.minute, dateTime.second, dateTime.nanosecondOfSecond / 1_000_000)

        return instantOfEpochMilli(millisSinceEpoch, dateTime)
    }

    actual fun toInstantAtSystemTimeZone(dateTime: LocalDateTime): Instant {
        val jsDate = createDateInSystemTimeZone(dateTime.year, dateTime.monthNumber - 1, dateTime.day,
            dateTime.hour, dateTime.minute, dateTime.second, dateTime.nanosecondOfSecond / 1_000_000)

        val millisSinceEpoch = jsDate.getTime()

        return instantOfEpochMilli(millisSinceEpoch, dateTime)
    }

    actual fun toLocalDateTimeAtUtc(instant: Instant): LocalDateTime =
        instant.toJsDate().toLocalDateTimeAtUtc(instant.nanosecondsOfSecond)

    actual fun toLocalDateTimeAtSystemTimeZone(instant: Instant): LocalDateTime =
        instant.toJsDate().toLocalDateTime(instant.nanosecondsOfSecond)


    private fun instantOfEpochMilli(millisSinceEpoch: Double, originalDateTime: LocalDateTime): Instant =
        Instant((millisSinceEpoch / 1_000).toLong(), originalDateTime.nanosecondOfSecond)

}