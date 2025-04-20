package net.dankito.kotlin.datetime.platform

import net.dankito.kotlin.datetime.*

internal expect object Platform {

    val timeSinceEpochPrecision: TimeSinceEpochPrecision


    fun getInstantNow(): Instant

    fun getLocalDateNow(): LocalDate

    fun getLocalTimeNow(): LocalTime

    fun getLocalDateTimeNow(): LocalDateTime

    /**
     * If day of week can be determined returns a value from 1 = Monday to 7 = Sunday.
     */
    fun getDayOfWeekDayNumber(date: LocalDate): Int?


    fun toInstantAtUtc(dateTime: LocalDateTime): Instant

    fun toLocalDateTimeAtUtc(instant: Instant): LocalDateTime

    fun toLocalDateTimeAtSystemTimeZone(instant: Instant): LocalDateTime

}