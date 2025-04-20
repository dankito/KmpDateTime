package net.dankito.kotlin.datetime


fun Instant.toJavaInstant() = java.time.Instant.ofEpochSecond(epochSeconds, nanosecondsOfSecond.toLong())

fun java.time.Instant.toKmpInstant() = Instant(epochSecond, nano)


fun LocalDate.toJavaLocalDate() = java.time.LocalDate.of(year, monthNumber, day)

fun java.time.LocalDate.toKmpLocalDate() = LocalDate(year, monthValue, dayOfMonth)


fun LocalTime.toJavaLocalTime() = java.time.LocalTime.of(hour, minute, second, nanosecondOfSecond)

fun java.time.LocalTime.toKmpLocalTime() = LocalTime(hour, minute, second, nano)


fun LocalDateTime.toJavaLocalDateTime() = java.time.LocalDateTime.of(year, monthNumber, day, hour, minute, second, nanosecondOfSecond)

fun java.time.LocalDateTime.toKmpLocalDateTime() = LocalDateTime(year, monthValue, dayOfMonth, hour, minute, second, nano)

