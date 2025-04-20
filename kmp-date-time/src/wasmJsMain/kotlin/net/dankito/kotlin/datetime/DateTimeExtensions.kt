package net.dankito.kotlin.datetime

import net.dankito.kotlin.datetime.platform.JsDate
import net.dankito.kotlin.datetime.platform.createDateFromMillisSinceEpoch

fun JsDate.toLocalDate() =
    // getMonth() = month as number (0 - 11) -> + 1; getDate() = day as number (1 - 31), getDay() = weekday (0 - 6)
    LocalDate(this.getFullYear(), this.getMonth() + 1, this.getDate())

fun JsDate.toLocalTime() =
    LocalTime(this.getHours(), this.getMinutes(), this.getSeconds(), this.getMilliseconds() * 1_000_000)

fun JsDate.toLocalDateTime() =
    LocalDateTime(this.toLocalDate(), this.toLocalTime())

fun JsDate.toLocalDateAtUtc() =
    // getMonth() = month as number (0 - 11) -> + 1; getDate() = day as number (1 - 31), getDay() = weekday (0 - 6)
    LocalDate(this.getUTCFullYear(), this.getUTCMonth() + 1, this.getUTCDate())

fun JsDate.toLocalTimeAtUtc() =
    LocalTime(this.getUTCHours(), this.getUTCMinutes(), this.getUTCSeconds(), this.getUTCMilliseconds() * 1_000_000)

fun JsDate.toLocalDateTimeAtUtc() =
    LocalDateTime(this.toLocalDateAtUtc(), this.toLocalTimeAtUtc())


fun Instant.toJsDate(): JsDate =
    // don't know why but for WASM epochMillis have to be converted to Double (in JS it works with Long)
    createDateFromMillisSinceEpoch(this.toEpochMilliseconds().toDouble())