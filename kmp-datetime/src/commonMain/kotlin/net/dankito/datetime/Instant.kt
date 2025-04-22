package net.dankito.datetime

import kotlinx.serialization.Serializable
import net.dankito.datetime.format.DateTimeFormatter
import net.dankito.datetime.format.DateTimeParser
import net.dankito.datetime.platform.Platform
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
        val timeSinceEpochPrecision: TimeSinceEpochPrecision = Platform.timeSinceEpochPrecision

        fun now(): Instant = Platform.getInstantNow()

        val Epoch = Instant(0)

        fun ofEpochMilli(epochMilli: Long): Instant = DateTimeCalculator.instantFromEpochMilli(epochMilli)

        fun parse(isoInstant: String): Instant = DateTimeParser.parseIsoInstantString(isoInstant)

        fun parseOrNull(isoInstant: String): Instant? = DateTimeParser.parseIsoInstantStringOrNull(isoInstant)
    }


    fun toEpochMilliseconds(): Long = DateTimeCalculator.toEpochMilliseconds(this)

    fun toLocalDateTimeAtUtc(): LocalDateTime = Platform.toLocalDateTimeAtUtc(this)

    fun toLocalDateTimeAtSystemTimeZone(): LocalDateTime = Platform.toLocalDateTimeAtSystemTimeZone(this)

    val isoString: String by lazy { DateTimeFormatter.toIsoString(this) }

    val isoStringAtSystemTimeZone: String by lazy { DateTimeFormatter.toIsoStringAtSystemTimeZone(this) }


    override fun compareTo(other: Instant): Int {
        val secondsCompare = epochSeconds.compareTo(other.epochSeconds)
        if (secondsCompare != 0) {
            return secondsCompare
        }

        return nanosecondsOfSecond.compareTo(other.nanosecondsOfSecond)
    }

    override fun toString() = isoString

}
