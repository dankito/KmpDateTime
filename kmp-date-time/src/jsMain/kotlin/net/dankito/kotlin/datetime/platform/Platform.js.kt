package net.dankito.kotlin.datetime.platform

import net.dankito.kotlin.datetime.Instant
import kotlin.js.Date

internal actual object Platform {

    actual fun getInstantNow(): Instant {
        val millisSinceEpoch = Date.now()

        return Instant.ofEpochMilli(millisSinceEpoch.toLong())
    }

}