package net.dankito.kotlin.datetime.platform

import kotlinx.cinterop.UnsafeNumber
import net.dankito.kotlin.datetime.Instant
import platform.Foundation.*

@OptIn(UnsafeNumber::class)
internal actual object Platform {

    actual fun getInstantNow(): Instant {
        val secondsSinceEpoch = NSDate().timeIntervalSince1970

        val nanosString = secondsSinceEpoch.toString().substringAfter('.').let {
            if (it.length > 9) it.substring(0, 9)
            else it.padEnd(9, '0')
        }

        return Instant(secondsSinceEpoch.toLong(), nanosString.toInt())
    }

}