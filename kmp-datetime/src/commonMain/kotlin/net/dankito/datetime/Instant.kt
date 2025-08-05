package net.dankito.datetime

import kotlinx.serialization.Serializable
import net.dankito.datetime.calculation.DateTimeCalculator
import net.dankito.datetime.calculation.Math
import net.dankito.datetime.format.DateTimeFormatter
import net.dankito.datetime.format.DateTimeParser
import net.dankito.datetime.platform.DateTimePlatform
import net.dankito.datetime.serialization.InstantDelegatingSerializer

@Serializable(with = InstantDelegatingSerializer::class)
data class Instant(
    val epochSeconds: Long,
    val nanosecondsOfSecond: Int = 0
) : Comparable<Instant> {

    companion object {
        /**
         * Returns the precision of the time since Epoch of the underlying platform.
         *
         * (Milliseconds for JVM, JS and WasmJS; Seconds for Linux, Windows and Apple systems.)
         */
        val timeSinceEpochPrecision: TimeSinceEpochPrecision = DateTimePlatform.timeSinceEpochPrecision

        fun now(): Instant = DateTimePlatform.getInstantNow()

        val Epoch = Instant(0)

        fun ofEpochMilli(epochMilli: Long): Instant = DateTimeCalculator.instantFromEpochMilli(epochMilli)

        fun ofEpochNanoseconds(epochNanoseconds: Long): Instant = DateTimeCalculator.instantFromEpochNanoseconds(epochNanoseconds)

        fun ofEpochSeconds(secondsSinceEpoch: Double): Instant = DateTimeCalculator.instantFromEpochSeconds(secondsSinceEpoch)

        fun parse(isoInstant: String): Instant = DateTimeParser.parseIsoInstantString(isoInstant)

        fun parseOrNull(isoInstant: String): Instant? = DateTimeParser.parseIsoInstantStringOrNull(isoInstant)
    }


    init {
        require(nanosecondsOfSecond in 0..999_999_999) {
            "nanosecondsOfSecond must be between 0 and 999,999,999"
        }
    }


    /**
     * Converts this Instant to a Double where the integer part is seconds since the Unix epoch and the fractional
     * part is sub-second precision in nanoseconds.
     */
    fun toEpochSecondsAsDouble(): Double = epochSeconds + nanosecondsOfSecond / 1_000_000_000.0

    fun toEpochSecondsAsFloatingPointString(): String = "${epochSeconds}.${nanosecondsOfSecond.toString().padStart(9, '0')}"

    fun toEpochMilliseconds(): Long = DateTimeCalculator.toEpochMilliseconds(this)

    fun toEpochMicroseconds(): Long = Math.addExact(Math.multiplyExact(epochSeconds, 1_000_000L), nanosecondsOfSecond / 1_000L)

    fun toEpochMicrosecondsString(): String = "$epochSeconds${(nanosecondsOfSecond / 1000).toString().padStart(6, '0')}"

    fun toEpochNanoseconds(): Long = Math.addExact(Math.multiplyExact(epochSeconds, 1_000_000_000L), nanosecondsOfSecond.toLong())

    fun toEpochNanosecondsString(): String = "$epochSeconds${nanosecondsOfSecond.toString().padStart(9, '0')}"

    // TODO: add conversion to Double <seconds since epoch>.<nanosecondsOfSecond>`

    fun toLocalDateTimeAtUtc(): LocalDateTime = DateTimePlatform.toLocalDateTimeAtUtc(this)

    fun toLocalDateTimeAtSystemTimeZone(): LocalDateTime = DateTimePlatform.toLocalDateTimeAtSystemTimeZone(this)

    val isoString: String by lazy { DateTimeFormatter.Default.toIsoString(this) }

    val isoStringAtSystemTimeZone: String by lazy { DateTimeFormatter.Default.toIsoStringAtSystemTimeZone(this) }


    fun plusDays(daysToAdd: Long) = plusSeconds(Math.multiplyExact(daysToAdd, DateTimeCalculator.SecondsPerDay.toLong()))

    fun plusHours(hoursToAdd: Long) = plusSeconds(Math.multiplyExact(hoursToAdd, DateTimeCalculator.SecondsPerHour.toLong()))

    fun plusMinutes(minutesToAdd: Long) = plusSeconds(Math.multiplyExact(minutesToAdd, DateTimeCalculator.SecondsPerMinute.toLong()))

    fun plusSeconds(secondsToAdd: Long) = plus(secondsToAdd, 0)

    fun plusMilliseconds(millisecondsToAdd: Long) = plus(millisecondsToAdd / 1_000L, (millisecondsToAdd % 1_000L) * 1_000_000L)

    fun plusMicroseconds(microsecondsToAdd: Long) = plus(microsecondsToAdd / 1_000_000L, (microsecondsToAdd % 1_000_000L) * 1_000L)

    fun plusNanoseconds(nanosecondsToAdd: Int) = plusNanoseconds(nanosecondsToAdd.toLong())
    fun plusNanoseconds(nanosecondsToAdd: Long) = plus(0, nanosecondsToAdd)

    private fun plus(secondsToAdd: Long, nanosToAdd: Long) = DateTimeCalculator.addToInstant(this, secondsToAdd, nanosToAdd)


    fun minusDays(daysToAdd: Long) = plusDays(-daysToAdd)

    fun minusHours(hoursToAdd: Long) = plusHours(-hoursToAdd)

    fun minusMinutes(minutesToAdd: Long) = plusMinutes(-minutesToAdd)

    fun minusSeconds(secondsToAdd: Long) = plusSeconds(-secondsToAdd)

    fun minusMilliseconds(millisecondsToAdd: Long) = plusMilliseconds(-millisecondsToAdd)

    fun minusMicroseconds(microsecondsToAdd: Long) = plusMicroseconds(-microsecondsToAdd)

    fun minusNanoseconds(nanosecondsToAdd: Int) = plusNanoseconds(-nanosecondsToAdd)
    fun minusNanoseconds(nanosecondsToAdd: Long) = plusNanoseconds(-nanosecondsToAdd)


    override fun compareTo(other: Instant): Int {
        val secondsCompare = epochSeconds.compareTo(other.epochSeconds)
        if (secondsCompare != 0) {
            return secondsCompare
        }

        return nanosecondsOfSecond.compareTo(other.nanosecondsOfSecond)
    }

    override fun toString() = isoString

}
