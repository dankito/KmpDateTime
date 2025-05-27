package net.dankito.datetime.platform

import net.dankito.datetime.*
import java.time.ZoneId
import java.time.ZoneOffset
import java.time.temporal.IsoFields

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

    actual fun getDayOfYear(date: LocalDate): Int? =
        date.toJavaLocalDate().dayOfYear

    actual fun getWeekOfYear(date: LocalDate): Int? =
        date.toJavaLocalDate().get(IsoFields.WEEK_OF_WEEK_BASED_YEAR)

    actual fun isInDaylightSavingTime(date: LocalDate): Boolean =
        ZoneId.systemDefault().rules.isDaylightSavings(date.atStartOfDay().toInstantAtSystemTimeZone().toJavaInstant())


    actual fun toInstantAtUtc(dateTime: LocalDateTime): Instant =
        dateTime.toJavaLocalDateTime().toInstant(ZoneOffset.UTC).toKmpInstant()

    actual fun toInstantAtSystemTimeZone(dateTime: LocalDateTime): Instant =
        dateTime.toJavaLocalDateTime().atZone(ZoneId.systemDefault()).toInstant().toKmpInstant()

    actual fun toLocalDateTimeAtUtc(instant: Instant): LocalDateTime =
        instant.toJavaInstant().atOffset(ZoneOffset.UTC).toLocalDateTime().toKmpLocalDateTime()

    actual fun toLocalDateTimeAtSystemTimeZone(instant: Instant): LocalDateTime =
        instant.toJavaInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDateTime().toKmpLocalDateTime()

}