package net.dankito.kotlin.datetime

import net.dankito.kotlin.datetime.platform.JsDate

fun JsDate.toLocalDate() =
    // getMonth() = month as number (0 - 11) -> + 1; getDate() = day as number (1 - 31), getDay() = weekday (0 - 6)
    LocalDate(this.getFullYear(), this.getMonth() + 1, this.getDate())

fun JsDate.toLocalTime() =
    LocalTime(this.getHours(), this.getMinutes(), this.getSeconds(), this.getMilliseconds() * 1_000_000)

fun JsDate.toLocalDateTime() =
    LocalDateTime(this.toLocalDate(), this.toLocalTime())