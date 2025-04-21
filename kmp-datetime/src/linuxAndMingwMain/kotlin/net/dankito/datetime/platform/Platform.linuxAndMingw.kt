package net.dankito.datetime.platform

import kotlinx.cinterop.*
import net.dankito.datetime.*
import platform.posix.*

@OptIn(ExperimentalForeignApi::class)
internal actual object Platform {

    actual val timeSinceEpochPrecision = TimeSinceEpochPrecision.Seconds


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
        getLocalTime()?.toLocalDate()
            ?: LocalDate(0) // TODO: what to do in case of error?


    actual fun getLocalTimeNow(): LocalTime =
        getLocalTime()?.toLocalTime()
            ?: LocalTime.StartOfDay // TODO: what to do in case of error?

    actual fun getLocalDateTimeNow(): LocalDateTime =
        getLocalTime()?.toLocalDateTime()
            ?: LocalDateTime(0) // TODO: what to do in case of error?


    actual fun getDayOfWeekDayNumber(date: LocalDate): Int? {
        val weekDay = memScoped {
            val unixDate = alloc<tm>().apply {
                tm_year = date.year - 1900
                tm_mon = date.monthNumber - 1
                tm_mday = date.day
                tm_hour = 12 // Set a safe time (no DST issues)
                tm_min = 0
                tm_sec = 0
            }

            // convert to time_t and normalize
            val time = mktime(unixDate.ptr)
            if (time == -1L) {
                null // Invalid date
            } else {
                unixDate.tm_wday
            }
        }

        return weekDay?.let {
            // 0 = Sunday, ..., 6 = Saturday
            if (it == 0) 7
            else it
        }
    }


    actual fun toInstantAtUtc(dateTime: LocalDateTime): Instant {
        val secondsSinceEpochInSystemTimeZone = getSecondsSinceEpochInSystemTimeZone(dateTime)

        // the problem is: the returned timestamp is in system's timezone, not in UTC. E.g. if system's timezone is
        // CEST, then two hours are missing to correct UTC value.
        // When we now convert this timestamp to UTC, then we can calculate the difference between system timezone
        // and UTC. By adding this difference to timestamp in system's timezone, we finally get the timestamp in UTC.
        val secondsSinceEpochUtc = memScoped {
            val time = alloc<time_tVar>()
            time.value = secondsSinceEpochInSystemTimeZone

            val tmUtc = gmtime(time.ptr)

            tmUtc?.pointed?.tm_isdst = -1 // let system determine if date time is in daylight saving time or not

            mktime(tmUtc)
        }

        val differenceToUtc = secondsSinceEpochInSystemTimeZone - secondsSinceEpochUtc

        return Instant(secondsSinceEpochInSystemTimeZone + differenceToUtc)
    }

    actual fun toInstantAtSystemTimeZone(dateTime: LocalDateTime): Instant {
        val secondsSinceEpoch = getSecondsSinceEpochInSystemTimeZone(dateTime)

        return Instant(secondsSinceEpoch)
    }

    private fun getSecondsSinceEpochInSystemTimeZone(dateTime: LocalDateTime): Long = memScoped {
        val unixDate = alloc<tm>().apply {
            tm_year = dateTime.year - 1900
            tm_mon = dateTime.monthNumber - 1
            tm_mday = dateTime.day
            tm_hour = dateTime.hour
            tm_min = dateTime.minute
            tm_sec = dateTime.second
            tm_isdst = -1 // let system determine if date time is in daylight saving time or not
        }

        mktime(unixDate.ptr)
    }

    actual fun toLocalDateTimeAtUtc(instant: Instant): LocalDateTime = memScoped {
        val time = alloc<time_tVar>()
        time.value = instant.epochSeconds

        gmtime(time.ptr)
    }?.pointed?.toLocalDateTime()
        ?: LocalDateTime(0) // TODO: what to do in case of error?

    actual fun toLocalDateTimeAtSystemTimeZone(instant: Instant): LocalDateTime = memScoped {
        val time = alloc<time_tVar>()
        time.value = instant.epochSeconds

        localtime(time.ptr)
    }?.pointed?.toLocalDateTime()
        ?: LocalDateTime(0) // TODO: what to do in case of error?


    fun getSystemTimeZoneShortname(): String? {
        tzset() // sets tzname variable

        return tzname[0]?.toKString()
    }


    private fun getLocalTime() = memScoped {
        // Get the current time as seconds since Unix epoch
        val currentTime = alloc<time_tVar>()
        time(currentTime.ptr) // Get current time and store it in currentTime

        // Convert to local time
        localtime(currentTime.ptr)
    }?.pointed

    private fun getCurrentTimeAtUTC() = memScoped {
        val currentTime = alloc<time_tVar>()
        time(currentTime.ptr)

        // Convert to local time
        gmtime(currentTime.ptr)
    }?.pointed

}