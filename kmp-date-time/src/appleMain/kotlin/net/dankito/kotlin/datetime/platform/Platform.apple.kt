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

        val nanosString = secondsSinceEpoch.toString().substringAfter('.').let {
            if (it.length > 9) it.substring(0, 9)
            else it.padEnd(9, '0')
        }

        return Instant(secondsSinceEpoch.toLong(), nanosString.toInt())
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

}