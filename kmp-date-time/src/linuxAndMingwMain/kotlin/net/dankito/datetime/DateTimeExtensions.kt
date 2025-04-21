package net.dankito.datetime

import platform.posix.*

fun tm.toLocalDate(): LocalDate {
    val year = this.tm_year + 1900  // tm_year is years since 1900
    val month = this.tm_mon + 1   // tm_mon is 0-based
    val day = this.tm_mday

    return LocalDate(year, month, day)
}

fun tm.toLocalTime(): LocalTime {
    val hour = this.tm_hour
    val minute = this.tm_min
    val second = this.tm_sec

    return LocalTime(hour, minute, second)
}

fun tm.toLocalDateTime() =
    LocalDateTime(this.toLocalDate(), this.toLocalTime())