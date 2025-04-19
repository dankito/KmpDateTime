package net.dankito.kotlin.datetime.platform

import kotlinx.cinterop.UnsafeNumber
import net.dankito.kotlin.datetime.Instant
import net.dankito.kotlin.datetime.LocalDate
import net.dankito.kotlin.datetime.LocalDateTime
import net.dankito.kotlin.datetime.LocalTime
import platform.Foundation.*

@OptIn(UnsafeNumber::class)
internal actual object Platform {

    actual fun getInstantNow(): Instant {
        val secondsSinceEpoch = NSDate().timeIntervalSince1970

        return epochSecondsToInstant(secondsSinceEpoch)
    }


    actual fun getLocalDateNow(): LocalDate =
        toLocalDate(NSDate())

    private fun toLocalDate(date: NSDate): LocalDate {
        val calendar = NSCalendar.currentCalendar
        val components = calendar.components(
            NSCalendarUnitYear or NSCalendarUnitMonth or NSCalendarUnitDay,
            fromDate = date
        )

        return LocalDate(components.year.toInt(), components.month.toInt(), components.day.toInt())
    }

    actual fun getLocalTimeNow(): LocalTime =
        toLocalTime(NSDate())

    private fun toLocalTime(date: NSDate): LocalTime {
        val calendar = NSCalendar.currentCalendar
        val components = calendar.components(
            NSCalendarUnitHour or NSCalendarUnitMinute or NSCalendarUnitSecond or NSCalendarUnitNanosecond, // TODO: there doesn't seem to be a nanosecond component
            fromDate = date
        )

        return LocalTime(components.hour.toInt(), components.minute.toInt(), components.second.toInt(), components.nanosecond.toInt())
    }

    actual fun getLocalDateTimeNow(): LocalDateTime {
        val now = NSDate()

        return LocalDateTime(toLocalDate(now), toLocalTime(now))
    }


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

}