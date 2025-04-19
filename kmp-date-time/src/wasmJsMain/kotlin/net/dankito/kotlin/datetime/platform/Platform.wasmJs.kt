package net.dankito.kotlin.datetime.platform

import net.dankito.kotlin.datetime.Instant


internal fun getMillisSinceEpoch(): Double =
    js("Date.now()")


internal actual object Platform {

    actual fun getInstantNow(): Instant {
        val millisSinceEpoch = getMillisSinceEpoch()

        return Instant.ofEpochMilli(millisSinceEpoch.toLong())
    }

}