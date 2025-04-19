package net.dankito.kotlin.datetime.platform

import kotlinx.cinterop.*
import net.dankito.kotlin.datetime.Instant
import net.dankito.kotlin.datetime.LocalDate
import net.dankito.kotlin.datetime.LocalDateTime
import net.dankito.kotlin.datetime.LocalTime
import platform.posix.*

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


    actual fun getLocalDateNow(): LocalDate =
        toLocalDate(getLocalTime())

    private fun toLocalDate(localTime: tm?): LocalDate = localTime?.let {
        // Extract components
        val year = localTime.tm_year + 1900  // tm_year is years since 1900
        val month = localTime.tm_mon + 1   // tm_mon is 0-based
        val day = localTime.tm_mday

        LocalDate(year, month, day)
    } ?: LocalDate(0)

    actual fun getLocalTimeNow(): LocalTime =
        toLocalTime(getLocalTime())

    private fun toLocalTime(localTime: tm?): LocalTime = localTime?.let {
        // Extract components
        val hour = localTime.tm_hour
        val minute = localTime.tm_min
        val second = localTime.tm_sec

        LocalTime(hour, minute, second)
    } ?: LocalTime(0)

    actual fun getLocalDateTimeNow(): LocalDateTime {
        val localTime = getLocalTime()

        return LocalDateTime(toLocalDate(localTime), toLocalTime(localTime))
    }


    private fun getLocalTime() = memScoped {
        // Get the current time as seconds since Unix epoch
        val currentTime = alloc<time_tVar>()
        time(currentTime.ptr) // Get current time and store it in currentTime

        // Convert to local time
        localtime(currentTime.ptr)
    }?.pointed

}