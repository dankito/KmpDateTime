package net.dankito.kotlin.datetime.platform

import kotlinx.cinterop.*
import net.dankito.kotlin.datetime.Instant
import platform.posix.CLOCK_REALTIME
import platform.posix.clock_gettime
import platform.posix.timespec

@OptIn(ExperimentalForeignApi::class)
internal actual object Platform {

    @OptIn(UnsafeNumber::class)
    actual fun getInstantNow(): Instant {
        val (seconds, nanos) = memScoped {
            val time = alloc<timespec>()
            clock_gettime(CLOCK_REALTIME, time.ptr)

            time.tv_sec to time.tv_nsec.toInt()
        }

        return Instant(seconds, nanos)
    }

}