package net.dankito.datetime

import platform.posix.*

fun tm.toLocalDate(): LocalDate {
    val year = this.tm_year + 1900  // tm_year is years since 1900
    val month = this.tm_mon + 1   // tm_mon is 0-based
    val day = this.tm_mday

    return LocalDate(year, month, day)
}

// when converting from another datetime type, set providedNanosecondsOfSecond to retain nanos of second (will be lost otherwise)
fun tm.toLocalTime(providedNanosecondsOfSecond: Int = 0): LocalTime {
    val hour = this.tm_hour
    val minute = this.tm_min
    val second = this.tm_sec

    return LocalTime(hour, minute, second, providedNanosecondsOfSecond)
}

// when converting from another datetime type, set providedNanosecondsOfSecond to retain nanos of second (will be lost otherwise)
fun tm.toLocalDateTime(providedNanosecondsOfSecond: Int = 0) =
    LocalDateTime(this.toLocalDate(), this.toLocalTime(providedNanosecondsOfSecond))