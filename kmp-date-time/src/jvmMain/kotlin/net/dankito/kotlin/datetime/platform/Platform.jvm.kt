package net.dankito.kotlin.datetime.platform

import net.dankito.kotlin.datetime.*
import java.time.ZoneOffset

internal actual object Platform {

    actual val timeSinceEpochPrecision = TimeSinceEpochPrecision.Milliseconds


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

    actual fun getDayOfWeekDayNumber(date: LocalDate): Int? =
        date.toJavaLocalDate().dayOfWeek.value


    actual fun toInstantAtUtc(dateTime: LocalDateTime): Instant =
        dateTime.toJavaLocalDateTime().toInstant(ZoneOffset.UTC).toKotlinInstant()

    actual fun toLocalDateTimeAtUtc(instant: Instant): LocalDateTime =
        instant.toJavaInstant().atOffset(ZoneOffset.UTC).toLocalDateTime().toKotlinLocalDateTime()

    actual fun toLocalDateTimeAtSystemTimeZone(instant: Instant): LocalDateTime =
        instant.toJavaInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDateTime().toKotlinLocalDateTime()

}