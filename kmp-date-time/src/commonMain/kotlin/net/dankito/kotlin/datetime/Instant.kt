package net.dankito.kotlin.datetime

import net.dankito.kotlin.datetime.format.DateTimeParser

data class Instant(
    val epochSeconds: Long,
    val nanosecondsOfSecond: Int = 0
) : Comparable<Instant> {

    companion object {
        val Epoch = Instant(0)

        fun ofEpochMilli(epochMilli: Long): Instant = Instant(
            epochMilli / 1000, // java.time.Instant uses Math.floorDiv(epochMilli, 1000)
            (epochMilli % 1000 * 1_000_000).toInt() // java.time.Instant uses Math.floorMod(epochMilli, 1000)
        )

        fun parse(isoInstant: String): Instant = DateTimeParser.parseIsoInstantString(isoInstant)

        fun parseOrNull(isoInstant: String): Instant? = DateTimeParser.parseIsoInstantStringOrNull(isoInstant)
    }


    // TODO: implement isoString and toString()


    override fun compareTo(other: Instant): Int {
        val secondsCompare = epochSeconds.compareTo(other.epochSeconds)
        if (secondsCompare != 0) {
            return secondsCompare
        }

        return nanosecondsOfSecond.compareTo(other.nanosecondsOfSecond)
    }

}
