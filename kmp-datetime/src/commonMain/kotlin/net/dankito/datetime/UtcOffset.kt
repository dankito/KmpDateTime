package net.dankito.datetime

import net.dankito.datetime.calculation.DateTimeCalculator
import net.dankito.datetime.format.DateTimeFormatter
import net.dankito.datetime.format.DateTimeParser
import kotlin.math.abs

/**
 * The UTC offset is the difference in hours and minutes between Coordinated Universal Time (UTC) and
 * the standard time at a particular place.
 *
 * This difference is expressed with respect to UTC and is generally shown in the format ±[hh]:[mm],
 * ±[hh][mm], or ±[hh]. E.g. "−06:00" for Guatemala City/Guatemala or "+05:30" for India and Sri Lanka.
 */
@ExperimentalMultiplatform
data class UtcOffset(
    val totalSeconds: Int
) {
    companion object {
        val UTC = UtcOffset(0)

        fun parse(utcOffsetString: String): UtcOffset = DateTimeParser.parseUtcOffsetString(utcOffsetString)

        fun parseOrNull(utcOffsetString: String): UtcOffset? = DateTimeParser.parseUtcOffsetStringOrNull(utcOffsetString)
    }


    constructor(hours: Int = 0, minutes: Int = 0, seconds: Int = 0) : this(DateTimeCalculator.totalSeconds(hours, minutes, seconds)) {
        require(hours in -18..18) { "UTC offset hours must be in range -18 to +18" }
        require(minutes in 0..59) { "UTC offset minutes must be in range 0 to 59" }
        require(seconds in 0..59) { "UTC offset seconds must be in range 0 to 59" }
    }

    init {
        // range copied from java.time.ZoneOffset.ofTotalSeconds(), but according to Wikipedia UTC offsets range from -12:00 to +14:00
        require(totalSeconds in -64800..64800) { "UTC offset not in valid range: -18:00 to +18:00" }
    }


    val hours: Int by lazy { totalSeconds / DateTimeCalculator.SecondsPerHour }

    val minutes: Int by lazy { (abs(totalSeconds) % DateTimeCalculator.SecondsPerHour) / DateTimeCalculator.SecondsPerMinute }

    val seconds: Int by lazy { abs(totalSeconds) % DateTimeCalculator.SecondsPerMinute }

    val isoString: String by lazy { DateTimeFormatter.toIsoString(this) }


    override fun toString() = isoString

}
