package net.dankito.datetime.platform

import kotlinx.cinterop.UnsafeNumber
import net.dankito.datetime.*
import platform.Foundation.*

@OptIn(UnsafeNumber::class)
internal actual object Platform {

    private val Utc = NSTimeZone.timeZoneWithAbbreviation("UTC")!!


    actual val timeSinceEpochPrecision = TimeSinceEpochPrecision.Seconds


    actual fun getInstantNow(): Instant = getNSDateNow().toInstant()


    actual fun getLocalDateNow(): LocalDate =
        getNSDateNow().toLocalDate()

    actual fun getLocalTimeNow(): LocalTime =
        getNSDateNow().toLocalTime()

    actual fun getLocalDateTimeNow(): LocalDateTime =
        getNSDateNow().toLocalDateTime()


    actual fun getDayOfWeekDayNumber(date: LocalDate): Int? {
        val components = NSDateComponents().apply {
            // date components expect an NSInteger, which is Int on 32-bit system and Long on 64-bit systems -> convert Ints to NSInteger
            this.year = date.year.toNSInteger()
            this.month = date.monthNumber.toNSInteger()
            this.day = date.day.toNSInteger()
        }

        val calendar = NSCalendar.currentCalendar
        val nsDate = calendar.dateFromComponents(components) ?: return null

        // The weekday units are the numbers 1 through N (where for the Gregorian calendar N=7 and 1 is Sunday).
        val weekDay = calendar.component(NSCalendarUnitWeekday, nsDate).toInt()
        // NSCalendarUnitWeekdayOrdinal == week of month

        return if (weekDay == 1) 7 else weekDay - 1
    }


    actual fun toInstantAtUtc(dateTime: LocalDateTime): Instant =
        dateTime.toNSDateAt(Utc).toInstant()

    actual fun toInstantAtSystemTimeZone(dateTime: LocalDateTime): Instant =
        dateTime.toNSDateAtSystemTimeZone().toInstant()

    actual fun toLocalDateTimeAtUtc(instant: Instant): LocalDateTime {
        val nsDateAtSystemTimeZone = instant.toNSDate()

        val utcOffset = getOffsetToUtcInSeconds(nsDateAtSystemTimeZone)

        val nsDateAtUtc = NSDate.dateWithTimeInterval(utcOffset * -1, nsDateAtSystemTimeZone)

        return nsDateAtUtc.toLocalDateTime()
    }

    actual fun toLocalDateTimeAtSystemTimeZone(instant: Instant): LocalDateTime =
        instant.toNSDate().toLocalDateTime()

    private fun getOffsetToUtcInSeconds(date: NSDate): NSTimeInterval { // NSTimeInterval is a typealias for Double
        val currentTimeZone = NSTimeZone.localTimeZone

        val currentGMTOffset = currentTimeZone.secondsFromGMTForDate(date)
        val gmtOffset = Utc.secondsFromGMTForDate(date)
        return (currentGMTOffset - gmtOffset).toDouble()
    }


    private fun getNSDateNow() = NSDate()

}