package net.dankito.datetime

import kotlin.js.Date

fun Date.toLocalDate() =
    // getMonth() = month as number (0 - 11) -> + 1; getDate() = day as number (1 - 31), getDay() = weekday (0 - 6)
    LocalDate(this.getFullYear(), this.getMonth() + 1, this.getDate())

// when converting from another datetime type, set providedNanosecondsOfSecond to retain nanos of second (will be lost otherwise)
fun Date.toLocalTime(providedNanosecondsOfSecond: Int? = null) =
    LocalTime(this.getHours(), this.getMinutes(), this.getSeconds(), getNanosecondsOfSecond(this.getMilliseconds(), providedNanosecondsOfSecond))

// when converting from another datetime type, set providedNanosecondsOfSecond to retain nanos of second (will be lost otherwise)
fun Date.toLocalDateTime(providedNanosecondsOfSecond: Int? = null) =
    LocalDateTime(this.toLocalDate(), this.toLocalTime(providedNanosecondsOfSecond))

fun Date.toLocalDateAtUtc() =
    // getMonth() = month as number (0 - 11) -> + 1; getDate() = day as number (1 - 31), getDay() = weekday (0 - 6)
    LocalDate(this.getUTCFullYear(), this.getUTCMonth() + 1, this.getUTCDate())

// when converting from another datetime type, set providedNanosecondsOfSecond to retain nanos of second (will be lost otherwise)
fun Date.toLocalTimeAtUtc(providedNanosecondsOfSecond: Int? = null) =
    LocalTime(this.getUTCHours(), this.getUTCMinutes(), this.getUTCSeconds(), getNanosecondsOfSecond(this.getUTCMilliseconds(), providedNanosecondsOfSecond))

// when converting from another datetime type, set providedNanosecondsOfSecond to retain nanos of second (will be lost otherwise)
fun Date.toLocalDateTimeAtUtc(providedNanosecondsOfSecond: Int? = null) =
    LocalDateTime(this.toLocalDateAtUtc(), this.toLocalTimeAtUtc(providedNanosecondsOfSecond))


fun LocalDate.toJsDateInSystemTimeZone(): Date = Date(this.year, this.monthNumber - 1, this.day)

fun LocalDate.millisSinceEpochUtc(): Double = Date.UTC(this.year, this.monthNumber - 1, this.day)

fun Instant.toJsDate(): Date = Date(this.toEpochMilliseconds())

private fun getNanosecondsOfSecond(millisecondsOfJsDate: Int, providedNanosecondsOfSecond: Int?): Int =
    providedNanosecondsOfSecond ?: (millisecondsOfJsDate * 1_000_000)