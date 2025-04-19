package net.dankito.kotlin.datetime.platform

import net.dankito.kotlin.datetime.*

internal actual object Platform {

    actual fun getInstantNow(): Instant {
        val javaInstance = java.time.Instant.now()

        return javaInstance.toKotlinInstant()
    }

    actual fun getLocalDateNow(): LocalDate {
        val javaLocalDate = java.time.LocalDate.now()

        return javaLocalDate.toKotlinLocalDate()
    }

    actual fun getLocalTimeNow(): LocalTime {
        val javaLocalTime = java.time.LocalTime.now()

        return javaLocalTime.toKotlinLocalTime()
    }

    actual fun getLocalDateTimeNow(): LocalDateTime {
        val javaLocalDateTime = java.time.LocalDateTime.now()

        return javaLocalDateTime.toKotlinLocalDateTime()
    }

}