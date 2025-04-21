@file:OptIn(UnsafeNumber::class)

package net.dankito.datetime

import kotlinx.cinterop.UnsafeNumber
import platform.Foundation.*

fun NSDate.toLocalDate(): LocalDate {
    val calendar = NSCalendar.currentCalendar
    val components = calendar.components(
        NSCalendarUnitYear or NSCalendarUnitMonth or NSCalendarUnitDay,
        fromDate = this
    )

    return LocalDate(components.year.toInt(), components.month.toInt(), components.day.toInt())
}

fun NSDate.toLocalTime(): LocalTime {
    val calendar = NSCalendar.currentCalendar
    val components = calendar.components(
        NSCalendarUnitHour or NSCalendarUnitMinute or NSCalendarUnitSecond or NSCalendarUnitNanosecond, // TODO: there doesn't seem to be a nanosecond component
        fromDate = this
    )

    return LocalTime(components.hour.toInt(), components.minute.toInt(), components.second.toInt(), components.nanosecond.toInt())
}

fun NSDate.toLocalDateTime() =
    LocalDateTime(this.toLocalDate(), this.toLocalTime())


/**
 * Returns the Instant as NSDate in system time zone.
 */
fun Instant.toNSDate(): NSDate =
    NSDate.dateWithTimeIntervalSince1970(this.epochSeconds.toDouble())