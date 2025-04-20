package net.dankito.kotlin.datetime.platform

import kotlinx.cinterop.UnsafeNumber
import net.dankito.kotlin.datetime.*
import platform.Foundation.*

@OptIn(UnsafeNumber::class)
internal actual object Platform {

    actual val timeSinceEpochPrecision = TimeSinceEpochPrecision.Seconds


    actual fun getInstantNow(): Instant {
        val secondsSinceEpoch = getNSDateNow().timeIntervalSince1970

        return epochSecondsToInstant(secondsSinceEpoch)
    }


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


    actual fun toInstantAtUtc(dateTime: LocalDateTime): Instant {
        val components = NSDateComponents().apply {
            // date components expect an NSInteger, which is Int on 32-bit system and Long on 64-bit systems -> convert Ints to NSInteger
            this.year = dateTime.year.toNSInteger()
            this.month = dateTime.monthNumber.toNSInteger()
            this.day = dateTime.day.toNSInteger()
            this.hour = dateTime.hour.toNSInteger()
            this.minute = dateTime.minute.toNSInteger()
            this.second = dateTime.second.toNSInteger()
            timeZone = NSTimeZone.timeZoneWithAbbreviation("UTC")  // Ensure it's interpreted as UTC
        }

        val calendar = NSCalendar.currentCalendar
        val nsDate = calendar.dateFromComponents(components)
            ?: throw IllegalArgumentException("Could not convert $dateTime to NSDate")

        val secondsSinceEpoch = nsDate.timeIntervalSince1970

        return epochSecondsToInstant(secondsSinceEpoch)
    }

    private fun epochSecondsToInstant(secondsSinceEpoch: Double): Instant { // TimeInterval is a typealias for Double
        val nanosString = secondsSinceEpoch.toString().substringAfter('.').let {
            if (it.length > 9) it.substring(0, 9)
            else it.padEnd(9, '0')
        }

        return Instant(secondsSinceEpoch.toLong(), nanosString.toInt())
    }


    private fun getNSDateNow() = NSDate()

}