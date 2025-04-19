package net.dankito.kotlin.datetime


fun Instant.toJavaInstant() = java.time.Instant.ofEpochSecond(epochSeconds, nanosecondsOfSecond.toLong())

fun java.time.Instant.toKotlinInstant() = Instant(epochSecond, nano)


fun LocalDate.toJavaLocalDate() = java.time.LocalDate.of(year, monthNumber, day)

fun java.time.LocalDate.toKotlinLocalDate() = LocalDate(year, monthValue, dayOfMonth)


fun LocalTime.toJavaLocalTime() = java.time.LocalTime.of(hour, minute, second, nanosecondOfSecond)

fun java.time.LocalTime.toKotlinLocalTime() = LocalTime(hour, minute, second, nano)


fun LocalDateTime.toJavaLocalDateTime() = java.time.LocalDateTime.of(year, monthNumber, day, hour, minute, second, nanosecondOfSecond)

fun java.time.LocalDateTime.toKotlinLocalDateTime() = LocalDateTime(year, monthValue, dayOfMonth, hour, minute, second, nano)

