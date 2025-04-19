package net.dankito.kotlin.datetime


fun Instant.toJavaInstant() = java.time.Instant.ofEpochSecond(epochSeconds, nanosecondsOfSecond.toLong())

fun java.time.Instant.toKotlinInstant() = Instant(epochSecond, nano)


fun LocalDate.toJavaLocalDate() = java.time.LocalDate.of(year, monthNumber, day)

fun java.time.LocalDate.toKotlinLocalDate() = LocalDate(year, monthValue, dayOfMonth)

