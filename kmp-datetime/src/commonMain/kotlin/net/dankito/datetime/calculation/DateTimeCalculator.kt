package net.dankito.datetime.calculation

import net.dankito.datetime.Instant

object DateTimeCalculator {

    const val SecondsPerMinute = 60

    const val MinutesPerHour = 60

    const val SecondsPerHour = MinutesPerHour * SecondsPerMinute

    const val HoursPerDay = 24

    const val SecondsPerDay: Int = SecondsPerHour * HoursPerDay


    fun instantFromEpochMilli(epochMilli: Long): Instant = Instant(
        // code copied from java.time.Instant.ofEpochMilli()
        Math.floorDiv(epochMilli, 1000),
        (Math.floorMod(epochMilli, 1000) * 1000_000).toInt()
    )

    fun instantFromEpochSeconds(secondsSinceEpoch: Double): Instant {
        val seconds = secondsSinceEpoch.toLong()
        val fractionalPart = secondsSinceEpoch - seconds
        val nanos = (fractionalPart * 1_000_000_000).toInt()

        return Instant(seconds, nanos)
    }


    // code copied from java.time.Instant.toEpochMilli() with additions from kotlinx.datetime.jvmMain/Instant.toEpochMilliseconds()
    /**
     * Converts this instant to the number of milliseconds from the epoch
     * of 1970-01-01T00:00:00Z.
     *
     *
     * If this instant represents a point on the time-line too far in the future
     * or past to fit in a `long` milliseconds, then an exception is thrown.
     *
     *
     * If this instant has greater than millisecond precision, then the conversion
     * will drop any excess precision information as though the amount in nanoseconds
     * was subject to integer division by one million.
     *
     * @return the number of milliseconds since the epoch of 1970-01-01T00:00:00Z
     * @throws ArithmeticException if numeric overflow occurs
     */
    fun toEpochMilliseconds(instant: Instant): Long = with(instant) {
        try {
            if (epochSeconds < 0 && nanosecondsOfSecond > 0) {
                val millis: Long = Math.multiplyExact(epochSeconds + 1, 1000)
                val adjustment: Long = (nanosecondsOfSecond / 1000000 - 1000).toLong()
                return Math.addExact(millis, adjustment)
            } else {
                val millis: Long = Math.multiplyExact(epochSeconds, 1000)
                return Math.addExact(millis, (nanosecondsOfSecond / 1000000).toLong())
            }
        } catch (_: Throwable) {
            if (epochSeconds > 0) Long.MAX_VALUE
            else Long.MIN_VALUE
        }
    }

    fun totalSeconds(hours: Int = 0, minutes: Int = 0, seconds: Int = 0): Int =
        if (hours < 0) {
            hours * SecondsPerHour - minutes * SecondsPerMinute - seconds
        } else {
            hours * SecondsPerHour + minutes * SecondsPerMinute + seconds
        }

}