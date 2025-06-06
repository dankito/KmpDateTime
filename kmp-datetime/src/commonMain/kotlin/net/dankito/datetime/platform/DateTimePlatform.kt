package net.dankito.datetime.platform

import net.dankito.datetime.*

internal expect object DateTimePlatform {

    val timeSinceEpochPrecision: TimeSinceEpochPrecision


    fun getInstantNow(): Instant

    fun getLocalDateNow(): LocalDate

    fun getLocalTimeNow(): LocalTime

    fun getLocalDateTimeNow(): LocalDateTime

    /**
     * If day of week can be determined returns a value from 1 = Monday to 7 = Sunday.
     */
    fun getDayOfWeekIsoDayNumber(date: LocalDate): Int?

    fun getDayOfYear(date: LocalDate): Int?

    fun getWeekOfYear(date: LocalDate): Int?

    fun isInDaylightSavingTime(date: LocalDate): Boolean


    fun toInstantAtUtc(dateTime: LocalDateTime): Instant

    fun toInstantAtSystemTimeZone(dateTime: LocalDateTime): Instant

    fun toLocalDateTimeAtUtc(instant: Instant): LocalDateTime

    fun toLocalDateTimeAtSystemTimeZone(instant: Instant): LocalDateTime

}