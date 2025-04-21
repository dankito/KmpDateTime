package net.dankito.kotlin.datetime.calculation

import net.dankito.kotlin.datetime.Instant
import kotlin.math.abs

object DateTimeCalculator {

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
                val millis: Long = multiplyExact(epochSeconds + 1, 1000)
                val adjustment: Long = (nanosecondsOfSecond / 1000000 - 1000).toLong()
                return addExact(millis, adjustment)
            } else {
                val millis: Long = multiplyExact(epochSeconds, 1000)
                return addExact(millis, (nanosecondsOfSecond / 1000000).toLong())
            }
        } catch (_: Throwable) {
            if (epochSeconds > 0) Long.MAX_VALUE
            else Long.MIN_VALUE
        }
    }

    // copied from java.lang.Math.multiplyExact()
    /**
     * Returns the product of the arguments,
     * throwing an exception if the result overflows a {@code long}.
     *
     * @param x the first value
     * @param y the second value
     * @return the result
     * @throws ArithmeticException if the result overflows a long
     */
    private fun multiplyExact(x: Long, y: Long): Long {
        val r = x * y
        val ax = abs(x.toDouble()).toLong()
        val ay = abs(y.toDouble()).toLong()
        if (((ax or ay) ushr 31 != 0L)) {
            // Some bits greater than 2^31 that might cause overflow
            // Check the result using the divide operator
            // and check for the special case of Long.MIN_VALUE * -1
            if (((y != 0L) && (r / y != x)) ||
                (x == Long.MIN_VALUE && y == -1L)
            ) {
                throw ArithmeticException("long overflow")
            }
        }
        return r
    }

    // copied from java.lang.Math.addExact()
    /**
     * Returns the sum of its arguments,
     * throwing an exception if the result overflows a `long`.
     *
     * @param x the first value
     * @param y the second value
     * @return the result
     * @throws ArithmeticException if the result overflows a long
     */
    fun addExact(x: Long, y: Long): Long {
        val r = x + y
        // HD 2-12 Overflow iff both arguments have the opposite sign of the result
        if (((x xor r) and (y xor r)) < 0) {
            throw ArithmeticException("long overflow")
        }
        return r
    }

}