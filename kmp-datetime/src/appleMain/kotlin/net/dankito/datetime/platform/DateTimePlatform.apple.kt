package net.dankito.datetime.platform

import kotlinx.cinterop.UnsafeNumber
import net.dankito.datetime.*
import platform.Foundation.*

@OptIn(UnsafeNumber::class)
internal actual object DateTimePlatform {

    private val Utc = NSTimeZone.timeZoneWithAbbreviation("UTC")!!


    actual val timeSinceEpochPrecision = TimeSinceEpochPrecision.Seconds


    actual fun getInstantNow(): Instant = getNSDateNow().toInstant()


    actual fun getLocalDateNow(): LocalDate =
        getNSDateNow().toLocalDate()

    actual fun getLocalTimeNow(): LocalTime =
        getNSDateNow().toLocalTime()

    actual fun getLocalDateTimeNow(): LocalDateTime =
        getNSDateNow().toLocalDateTime()


    actual fun getDayOfWeekIsoDayNumber(date: LocalDate): Int? {
        val nsDate = date.toNSDateAtSystemTimeZone() ?: return null

        val calendar = NSCalendar.currentCalendar

        // The weekday units are the numbers 1 through N (where for the Gregorian calendar N=7 and 1 is Sunday).
        val weekDay = calendar.component(NSCalendarUnitWeekday, nsDate).toInt()
        // NSCalendarUnitWeekdayOrdinal == week of month

        return if (weekDay == 1) 7 else weekDay - 1
    }

    actual fun getDayOfYear(date: LocalDate): Int? {
        val nsDate = date.toNSDateAtSystemTimeZone() ?: return null

        val calendar = NSCalendar.currentCalendar

        return calendar.ordinalityOfUnit(NSCalendarUnitDay, NSCalendarUnitYear, nsDate).toInt()
    }

    actual fun getWeekOfYear(date: LocalDate): Int? =
        getCalendarComponent(date, NSCalendarUnitWeekOfYear)

    actual fun isInDaylightSavingTime(date: LocalDate): Boolean {
        val nsDate = date.toNSDateAtSystemTimeZone() ?: return false

        val timeZone = NSTimeZone.systemTimeZone // .localTimeZone?

        return timeZone.isDaylightSavingTimeForDate(nsDate)
    }

    private fun getCalendarComponent(date: LocalDate, component: ULong): Int? {
        val nsDate = date.toNSDateAtSystemTimeZone() ?: return null

        val calendar = NSCalendar.currentCalendar

        return calendar.component(component, nsDate).toInt()
    }


    // TODO
    fun getEra(date: LocalDate): Int? = getCalendarComponent(date, NSCalendarUnitEra)

    fun getQuarter(date: LocalDate): Int? = getCalendarComponent(date, NSCalendarUnitQuarter)

    fun getWeekOfMonth(date: LocalDate): Int? = getCalendarComponent(date, NSCalendarUnitWeekOfMonth)

    fun getTimeZone(date: LocalDate): Int? = getCalendarComponent(date, NSCalendarUnitTimeZone)


    actual fun toInstantAtUtc(dateTime: LocalDateTime): Instant =
        dateTime.toNSDateAt(Utc).toInstant(dateTime.nanosecondOfSecond)

    actual fun toInstantAtSystemTimeZone(dateTime: LocalDateTime): Instant =
        dateTime.toNSDateAtSystemTimeZone().toInstant(dateTime.nanosecondOfSecond)

    actual fun toLocalDateTimeAtUtc(instant: Instant): LocalDateTime {
        val nsDateAtSystemTimeZone = instant.toNSDate()

        val utcOffset = getOffsetToUtcInSeconds(nsDateAtSystemTimeZone)

        val nsDateAtUtc = NSDate.dateWithTimeInterval(utcOffset * -1, nsDateAtSystemTimeZone)

        return nsDateAtUtc.toLocalDateTime(instant.nanosecondsOfSecond)
    }

    actual fun toLocalDateTimeAtSystemTimeZone(instant: Instant): LocalDateTime =
        instant.toNSDate().toLocalDateTime(instant.nanosecondsOfSecond)

    private fun getOffsetToUtcInSeconds(date: NSDate): NSTimeInterval { // NSTimeInterval is a typealias for Double
        val currentTimeZone = NSTimeZone.localTimeZone

        val currentGMTOffset = currentTimeZone.secondsFromGMTForDate(date)
        val gmtOffset = Utc.secondsFromGMTForDate(date)
        return (currentGMTOffset - gmtOffset).toDouble()
    }


    private fun getNSDateNow() = NSDate()

}