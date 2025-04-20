package net.dankito.kotlin.datetime.platform

import net.dankito.kotlin.datetime.*
import java.time.ZoneOffset

internal actual object Platform {

    actual val timeSinceEpochPrecision = TimeSinceEpochPrecision.Milliseconds


    actual fun getInstantNow(): Instant {
        val javaInstance = java.time.Instant.now()

        return javaInstance.toKmpInstant()
    }

    actual fun getLocalDateNow(): LocalDate {
        val javaLocalDate = java.time.LocalDate.now()

        return javaLocalDate.toKmpLocalDate()
    }

    actual fun getLocalTimeNow(): LocalTime {
        val javaLocalTime = java.time.LocalTime.now()

        return javaLocalTime.toKmpLocalTime()
    }

    actual fun getLocalDateTimeNow(): LocalDateTime {
        val javaLocalDateTime = java.time.LocalDateTime.now()

        return javaLocalDateTime.toKmpLocalDateTime()
    }

    actual fun getDayOfWeekDayNumber(date: LocalDate): Int? =
        date.toJavaLocalDate().dayOfWeek.value


    actual fun toInstantAtUtc(dateTime: LocalDateTime): Instant =
        dateTime.toJavaLocalDateTime().toInstant(ZoneOffset.UTC).toKmpInstant()

    actual fun toLocalDateTimeAtUtc(instant: Instant): LocalDateTime =
        instant.toJavaInstant().atOffset(ZoneOffset.UTC).toLocalDateTime().toKmpLocalDateTime()

    actual fun toLocalDateTimeAtSystemTimeZone(instant: Instant): LocalDateTime =
        instant.toJavaInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDateTime().toKmpLocalDateTime()

}