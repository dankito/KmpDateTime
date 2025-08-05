package net.dankito.datetime

import kotlinx.serialization.Serializable
import net.dankito.datetime.format.DateTimeFormatter
import net.dankito.datetime.format.DateTimeParser
import net.dankito.datetime.platform.DateTimePlatform
import net.dankito.datetime.calculation.DateTimeCalculator
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

    fun toEpochMilliseconds(): Long = DateTimeCalculator.toEpochMilliseconds(this)

    fun toLocalDateTimeAtUtc(): LocalDateTime = DateTimePlatform.toLocalDateTimeAtUtc(this)

    fun toLocalDateTimeAtSystemTimeZone(): LocalDateTime = DateTimePlatform.toLocalDateTimeAtSystemTimeZone(this)

    val isoString: String by lazy { DateTimeFormatter.Default.toIsoString(this) }

    val isoStringAtSystemTimeZone: String by lazy { DateTimeFormatter.Default.toIsoStringAtSystemTimeZone(this) }


    override fun compareTo(other: Instant): Int {
        val secondsCompare = epochSeconds.compareTo(other.epochSeconds)
        if (secondsCompare != 0) {
            return secondsCompare
        }

        return nanosecondsOfSecond.compareTo(other.nanosecondsOfSecond)
    }

    override fun toString() = isoString

}
