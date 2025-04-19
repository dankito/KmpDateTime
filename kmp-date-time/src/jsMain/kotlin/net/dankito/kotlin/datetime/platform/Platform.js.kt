package net.dankito.kotlin.datetime.platform

import net.dankito.kotlin.datetime.Instant
import net.dankito.kotlin.datetime.LocalDate
import net.dankito.kotlin.datetime.LocalDateTime
import net.dankito.kotlin.datetime.LocalTime
import kotlin.js.Date

internal actual object Platform {

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

}