package net.dankito.kotlin.datetime.platform

import net.dankito.kotlin.datetime.Instant
import net.dankito.kotlin.datetime.LocalDate
import net.dankito.kotlin.datetime.LocalDateTime
import net.dankito.kotlin.datetime.LocalTime


internal fun getMillisSinceEpoch(): Double =
    js("Date.now()")

internal fun getDateNow(): JsDate =
    js("new Date(Date.now())")


internal actual object Platform {

    actual fun getInstantNow(): Instant {
        val millisSinceEpoch = getMillisSinceEpoch()

        return Instant.ofEpochMilli(millisSinceEpoch.toLong())
    }

    actual fun getLocalDateNow(): LocalDate {
        val now = getDateNow()

        // getMonth() = month as number (0 - 11) -> + 1; getDate() = day as number (1 - 31), getDay() = weekday (0 - 6)
        return LocalDate(now.getFullYear(), now.getMonth() + 1, now.getDate())
    }

    actual fun getLocalTimeNow(): LocalTime {
        val now = getDateNow()

        return toLocalTime(now)
    }

    actual fun getLocalDateTimeNow(): LocalDateTime {
        val now = getDateNow()

        return LocalDateTime(toLocalDate(now), toLocalTime(now))
    }

    private fun toLocalDate(now: JsDate) =
        // getMonth() = month as number (0 - 11) -> + 1; getDate() = day as number (1 - 31), getDay() = weekday (0 - 6)
        LocalDate(now.getFullYear(), now.getMonth() + 1, now.getDate())

    private fun toLocalTime(now: JsDate) =
        LocalTime(now.getHours(), now.getMinutes(), now.getSeconds(), now.getMilliseconds() * 1_000_000)

}