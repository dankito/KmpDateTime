package net.dankito.kotlin.datetime.platform

import net.dankito.kotlin.datetime.Instant
import net.dankito.kotlin.datetime.LocalDate
import kotlin.js.Date

internal actual object Platform {

    actual fun getInstantNow(): Instant {
        val millisSinceEpoch = Date.now()

        return Instant.ofEpochMilli(millisSinceEpoch.toLong())
    }

    actual fun getLocalDateNow(): LocalDate {
        val now = Date(Date.now())

        // getMonth() = month as number (0 - 11) -> + 1; getDate() = day as number (1 - 31), getDay() = weekday (0 - 6)
        return LocalDate(now.getFullYear(), now.getMonth() + 1, now.getDate())
    }

}