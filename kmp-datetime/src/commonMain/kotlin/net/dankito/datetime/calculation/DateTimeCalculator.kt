package net.dankito.datetime.calculation

import net.dankito.datetime.Instant
import net.dankito.datetime.LocalDate
import net.dankito.datetime.Quarter

object DateTimeCalculator {

    const val SecondsPerMinute = 60

    const val MinutesPerHour = 60

    const val SecondsPerHour = MinutesPerHour * SecondsPerMinute

    const val HoursPerDay = 24

    const val SecondsPerDay: Int = SecondsPerHour * HoursPerDay

    const val MillisecondsPerSecond = 1000L

    const val MillisecondsPerDay = SecondsPerDay * MillisecondsPerSecond

    const val NanosecondsPerSecond = 1_000_000_000L


    fun instantFromEpochMilli(epochMilli: Long): Instant = Instant(
        // code copied from java.time.Instant.ofEpochMilli()
        Math.floorDiv(epochMilli, 1_000),
        (Math.floorMod(epochMilli, 1_000) * 1_000_000).toInt()
    )

    fun instantFromEpochNanoseconds(epochNanoseconds: Long): Instant = Instant(
        Math.floorDiv(epochNanoseconds, 1_000_000_000),
        Math.floorMod(epochNanoseconds, 1_000_000_000).toInt()
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

    fun addToInstant(instant: Instant, secondsToAdd: Long, nanosToAdd: Long): Instant {
        // code copied from java.time.Instant.plus(long secondsToAdd, long nanosToAdd)
        if ((secondsToAdd or nanosToAdd) == 0L) { // micro-optimization to check if both, secondsToAdd and nanosToAdd are 0
            return instant
        }

        // add secondsToAdd and the seconds from nanosToAdd that exceed the pure nanosecondsOfSecond part, to epochSeconds
        var epochSeconds = Math.addExact(instant.epochSeconds, secondsToAdd)
        epochSeconds = Math.addExact(epochSeconds, nanosToAdd / NanosecondsPerSecond)

        val nanosecondsOfSecondOnly = nanosToAdd % NanosecondsPerSecond // pure nanosecondsOfSecond part of nanosToAdd
        val nanosecondsOfSecond = instant.nanosecondsOfSecond + nanosecondsOfSecondOnly  // safe int+NANOS_PER_SECOND

        // e.g. after subtracting 1 from 0, nanosecondsOfSecond may now again exceed its range -> again cut the seconds part from nanosecondsOfSecond
        val secs = Math.addExact(epochSeconds, Math.floorDiv(nanosecondsOfSecond, NanosecondsPerSecond))
        val nos = Math.floorMod(nanosecondsOfSecond, NanosecondsPerSecond).toInt()

        return Instant(secs, nos)
    }


    fun totalSeconds(hours: Int = 0, minutes: Int = 0, seconds: Int = 0): Int =
        if (hours < 0) {
            hours * SecondsPerHour - minutes * SecondsPerMinute - seconds
        } else {
            hours * SecondsPerHour + minutes * SecondsPerMinute + seconds
        }


    fun getQuarter(date: LocalDate): Quarter = when (date.monthNumber) {
        1, 2, 3 -> Quarter.Q1
        4, 5, 6 -> Quarter.Q2
        7, 8, 9 -> Quarter.Q3
        else -> Quarter.Q4
    }

    fun getWeekOfYear(date: LocalDate, firstDayOfWeek: Int, minimalDaysInFirstWeek: Int): Int? {
        // Get day of the week for Jan 1 of this year
        val yearStart = LocalDate(date.year, 1, 1)
        if (yearStart.dayOfWeek == null || yearStart.dayOfYear == null) {
            return null
        }

        val yearStartWeekday = yearStart.dayOfWeek!!.isoDayNumber // 1=Monday, 7=Sunday

        // Shift the first day so 1 = firstDay of week
        fun shiftedDay(isoDay: Int): Int = ((isoDay - firstDayOfWeek + 7) % 7)

        val startShift = shiftedDay(yearStartWeekday)
        val yearStartWeekLength = 7 - startShift

        val startOfWeek1 = if (yearStartWeekLength >= minimalDaysInFirstWeek) {
            yearStart.dayOfYear!! - startShift
        } else {
            yearStart.dayOfYear!! + (7 - startShift)
        }

        val daysBetween = date.dayOfYear!! - startOfWeek1

        return daysBetween / 7 + 1
    }

}