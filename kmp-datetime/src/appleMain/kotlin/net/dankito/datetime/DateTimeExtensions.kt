@file:OptIn(UnsafeNumber::class)

package net.dankito.datetime

import kotlinx.cinterop.UnsafeNumber
import net.dankito.datetime.platform.toNSInteger
import platform.Foundation.*

fun NSDate.toLocalDate(): LocalDate {
    val calendar = NSCalendar.currentCalendar
    val components = calendar.components(
        NSCalendarUnitYear or NSCalendarUnitMonth or NSCalendarUnitDay,
        fromDate = this
    )

    return LocalDate(components.year.toInt(), components.month.toInt(), components.day.toInt())
}

// when converting from another datetime type, set providedNanosecondsOfSecond to retain nanos of second (will be lost otherwise)
fun NSDate.toLocalTime(providedNanosecondsOfSecond: Int? = null): LocalTime {
    val calendar = NSCalendar.currentCalendar
    val components = calendar.components(
        NSCalendarUnitHour or NSCalendarUnitMinute or NSCalendarUnitSecond or NSCalendarUnitNanosecond, // TODO: there doesn't seem to be a nanosecond component
        fromDate = this
    )

    return LocalTime(components.hour.toInt(), components.minute.toInt(), components.second.toInt(), providedNanosecondsOfSecond ?: components.nanosecond.toInt())
}

// when converting from another datetime type, set providedNanosecondsOfSecond to retain nanos of second (will be lost otherwise)
fun NSDate.toLocalDateTime(providedNanosecondsOfSecond: Int? = null) =
    LocalDateTime(this.toLocalDate(), this.toLocalTime(providedNanosecondsOfSecond))

// when converting from another datetime type, set providedNanosecondsOfSecond to retain nanos of second (will be lost otherwise)
fun NSDate.toInstant(providedNanosecondsOfSecond: Int? = null): Instant {
    val secondsSinceEpoch = this.timeIntervalSince1970

    return if (providedNanosecondsOfSecond != null) {
        Instant(secondsSinceEpoch.toLong(), providedNanosecondsOfSecond)
    } else {
        Instant.ofEpochSeconds(secondsSinceEpoch)

    }
}


fun LocalDateTime.toNSDateAtSystemTimeZone(): NSDate = this.toNSDateAt(NSTimeZone.localTimeZone)

fun LocalDateTime.toNSDateAt(timeZone: NSTimeZone = NSTimeZone.localTimeZone): NSDate = this.let { dateTime ->
    val components = NSDateComponents().apply {
        // date components expect an NSInteger, which is Int on 32-bit system and Long on 64-bit systems -> convert Ints to NSInteger
        this.year = dateTime.year.toNSInteger()
        this.month = dateTime.monthNumber.toNSInteger()
        this.day = dateTime.day.toNSInteger()
        this.hour = dateTime.hour.toNSInteger()
        this.minute = dateTime.minute.toNSInteger()
        this.second = dateTime.second.toNSInteger()
        this.timeZone = timeZone
    }

    val calendar = NSCalendar.currentCalendar
    return calendar.dateFromComponents(components)
        ?: throw IllegalArgumentException("Could not convert $dateTime to NSDate")
}

/**
 * Returns the Instant as NSDate in system time zone.
 */
fun Instant.toNSDate(): NSDate =
    NSDate.dateWithTimeIntervalSince1970(this.epochSeconds.toDouble())