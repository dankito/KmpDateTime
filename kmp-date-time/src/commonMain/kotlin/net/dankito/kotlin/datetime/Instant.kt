package net.dankito.kotlin.datetime

data class Instant(
    val epochSeconds: Long,
    val nanosecondsOfSecond: Int = 0
) : Comparable<Instant> {

    companion object {
        fun ofEpochMilli(epochMilli: Long): Instant = Instant(
            epochMilli / 1000, // java.time.Instant uses Math.floorDiv(epochMilli, 1000)
            (epochMilli % 1000 * 1_000_000).toInt() // java.time.Instant uses Math.floorMod(epochMilli, 1000)
        )
    }


    override fun compareTo(other: Instant): Int {
        val secondsCompare = epochSeconds.compareTo(other.epochSeconds)
        if (secondsCompare != 0) {
            return secondsCompare
        }

        return nanosecondsOfSecond.compareTo(other.nanosecondsOfSecond)
    }

}
