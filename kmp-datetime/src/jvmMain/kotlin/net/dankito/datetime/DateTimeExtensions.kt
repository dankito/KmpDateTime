package net.dankito.datetime

import java.math.BigDecimal
import java.math.RoundingMode


fun Instant.toJavaInstant() = java.time.Instant.ofEpochSecond(epochSeconds, nanosecondsOfSecond.toLong())

fun java.time.Instant.toKmpInstant() = Instant(epochSecond, nano)

fun Instant.toEpochSecondsAsBigDecimal(): BigDecimal = BigDecimal(this.toEpochSecondsAsFloatingPointString())

fun Instant.Companion.ofEpochSeconds(secondsSinceEpoch: BigDecimal): Instant {
    val epochSeconds = secondsSinceEpoch.setScale(0, RoundingMode.DOWN).longValueExact()
    val nanos = secondsSinceEpoch.subtract(BigDecimal.valueOf(epochSeconds))
        .multiply(BigDecimal.valueOf(1_000_000_000))
        .setScale(0, RoundingMode.HALF_UP)
        .intValueExact()

    return Instant(epochSeconds, nanos)
}


fun LocalDate.toJavaLocalDate() = java.time.LocalDate.of(year, monthNumber, day)

fun java.time.LocalDate.toKmpLocalDate() = LocalDate(year, monthValue, dayOfMonth)


fun LocalTime.toJavaLocalTime() = java.time.LocalTime.of(hour, minute, second, nanosecondOfSecond)

fun java.time.LocalTime.toKmpLocalTime() = LocalTime(hour, minute, second, nano)


fun LocalDateTime.toJavaLocalDateTime() = java.time.LocalDateTime.of(year, monthNumber, day, hour, minute, second, nanosecondOfSecond)

fun java.time.LocalDateTime.toKmpLocalDateTime() = LocalDateTime(year, monthValue, dayOfMonth, hour, minute, second, nano)

