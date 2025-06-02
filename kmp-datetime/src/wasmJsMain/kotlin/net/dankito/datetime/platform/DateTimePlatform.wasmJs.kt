package net.dankito.datetime.platform

import net.dankito.datetime.*
import net.dankito.datetime.calculation.DateTimeCalculator
import net.dankito.datetime.platform.js.IntlLocale
import net.dankito.datetime.platform.js.WeekInfo
import kotlin.math.floor


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

internal fun getCurrentLocaleLanguageTag(): String =
    js("Intl.NumberFormat().resolvedOptions().locale")

internal fun getLocale(languageTag: String): IntlLocale =
    js("new Intl.Locale(languageTag)")

internal fun getWeekInfo(locale: String): JsAny =
    js("new Intl.Locale(locale).weekInfo")


internal actual object DateTimePlatform {

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


    actual fun getDayOfWeekIsoDayNumber(date: LocalDate): Int? {
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

    actual fun getWeekOfYear(date: LocalDate): Int? {
        val languageTag = getCurrentLocaleLanguageTag()

        val weekInfo = getWeekInfo(languageTag)

        return weekInfo?.let { DateTimeCalculator.getWeekOfYear(date, it.firstDay, it.minimalDays ?: 4) }
    }

    fun getWeekInfo(languageTag: String): WeekInfo? = try {
        val locale = getLocale(languageTag)

        locale.weekInfo ?: locale.getWeekInfo()
    } catch (e: Throwable) {
        println("Could not get WeekInfo for language tag '$languageTag': $e")
        null
    }

    actual fun isInDaylightSavingTime(date: LocalDate): Boolean =
        getTimeZoneOffset(date) - getTimeZoneOffset(date.atStartOfYear()) != 0

    fun getTimeZoneOffset(date: LocalDate): Int =
        date.toJsDateInSystemTimeZone().getTimezoneOffset()


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