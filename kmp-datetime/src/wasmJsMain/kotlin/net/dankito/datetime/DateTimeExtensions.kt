package net.dankito.datetime

import net.dankito.datetime.platform.JsDate
import net.dankito.datetime.platform.createDateFromMillisSinceEpoch

fun JsDate.toLocalDate() =
    // getMonth() = month as number (0 - 11) -> + 1; getDate() = day as number (1 - 31), getDay() = weekday (0 - 6)
    LocalDate(this.getFullYear(), this.getMonth() + 1, this.getDate())

// when converting from another datetime type, set providedNanosecondsOfSecond to retain nanos of second (will be lost otherwise)
fun JsDate.toLocalTime(providedNanosecondsOfSecond: Int? = 0) =
    LocalTime(this.getHours(), this.getMinutes(), this.getSeconds(), getNanosecondsOfSecond(this.getMilliseconds(), providedNanosecondsOfSecond))

// when converting from another datetime type, set providedNanosecondsOfSecond to retain nanos of second (will be lost otherwise)
fun JsDate.toLocalDateTime(providedNanosecondsOfSecond: Int? = null) =
    LocalDateTime(this.toLocalDate(), this.toLocalTime(providedNanosecondsOfSecond))

fun JsDate.toLocalDateAtUtc() =
    // getMonth() = month as number (0 - 11) -> + 1; getDate() = day as number (1 - 31), getDay() = weekday (0 - 6)
    LocalDate(this.getUTCFullYear(), this.getUTCMonth() + 1, this.getUTCDate())

// when converting from another datetime type, set providedNanosecondsOfSecond to retain nanos of second (will be lost otherwise)
fun JsDate.toLocalTimeAtUtc(providedNanosecondsOfSecond: Int? = null) =
    LocalTime(this.getUTCHours(), this.getUTCMinutes(), this.getUTCSeconds(), getNanosecondsOfSecond(this.getUTCMilliseconds(), providedNanosecondsOfSecond))

// when converting from another datetime type, set providedNanosecondsOfSecond to retain nanos of second (will be lost otherwise)
fun JsDate.toLocalDateTimeAtUtc(providedNanosecondsOfSecond: Int? = null) =
    LocalDateTime(this.toLocalDateAtUtc(), this.toLocalTimeAtUtc(providedNanosecondsOfSecond))


fun Instant.toJsDate(): JsDate =
    // don't know why but for WASM epochMillis have to be converted to Double (in JS it works with Long)
    createDateFromMillisSinceEpoch(this.toEpochMilliseconds().toDouble())

private fun getNanosecondsOfSecond(millisecondsOfJsDate: Int, providedNanosecondsOfSecond: Int?): Int =
    providedNanosecondsOfSecond ?: (millisecondsOfJsDate * 1_000_000)