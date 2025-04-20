package net.dankito.kotlin.datetime

import kotlinx.serialization.Serializable
import net.dankito.kotlin.datetime.format.DateTimeFormatter
import net.dankito.kotlin.datetime.format.DateTimeParser
import net.dankito.kotlin.datetime.platform.Platform
import net.dankito.kotlin.datetime.serialization.InstantIso8601Serializer
import net.dankito.kotlin.datetime.util.DateTimeCalculator

@Serializable(with = InstantIso8601Serializer::class)
data class Instant(
    val epochSeconds: Long,
    val nanosecondsOfSecond: Int = 0
) : Comparable<Instant> {

    companion object {
        val timeSinceEpochPrecision: TimeSinceEpochPrecision = Platform.timeSinceEpochPrecision

        fun now(): Instant = Platform.getInstantNow()

        val Epoch = Instant(0)

        fun ofEpochMilli(epochMilli: Long): Instant = Instant(
            epochMilli / 1000, // java.time.Instant uses Math.floorDiv(epochMilli, 1000)
            (epochMilli % 1000 * 1_000_000).toInt() // java.time.Instant uses Math.floorMod(epochMilli, 1000)
        )

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
