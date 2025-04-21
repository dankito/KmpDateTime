package net.dankito.datetime

import kotlin.js.Date

fun Date.toLocalDate() =
    // getMonth() = month as number (0 - 11) -> + 1; getDate() = day as number (1 - 31), getDay() = weekday (0 - 6)
    LocalDate(this.getFullYear(), this.getMonth() + 1, this.getDate())

fun Date.toLocalTime() =
    LocalTime(this.getHours(), this.getMinutes(), this.getSeconds(), this.getMilliseconds() * 1_000_000)

fun Date.toLocalDateTime() =
    LocalDateTime(this.toLocalDate(), this.toLocalTime())

fun Date.toLocalDateAtUtc() =
    // getMonth() = month as number (0 - 11) -> + 1; getDate() = day as number (1 - 31), getDay() = weekday (0 - 6)
    LocalDate(this.getUTCFullYear(), this.getUTCMonth() + 1, this.getUTCDate())

fun Date.toLocalTimeAtUtc() =
    LocalTime(this.getUTCHours(), this.getUTCMinutes(), this.getUTCSeconds(), this.getUTCMilliseconds() * 1_000_000)

fun Date.toLocalDateTimeAtUtc() =
    LocalDateTime(this.toLocalDateAtUtc(), this.toLocalTimeAtUtc())


fun Instant.toJsDate(): Date = Date(this.toEpochMilliseconds())