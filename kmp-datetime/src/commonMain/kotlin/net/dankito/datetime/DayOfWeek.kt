package net.dankito.datetime

import kotlinx.serialization.Serializable
import net.dankito.datetime.serialization.DayOfWeekNameSerializer

@Serializable(with = DayOfWeekNameSerializer::class) // don't know why, but without serializing fails in JS and native
enum class DayOfWeek {
    Monday,
    Tuesday,
    Wednesday,
    Thursday,
    Friday,
    Saturday,
    Sunday
    ;


    /**
     * The ISO 8601 number of the given day of the week. Monday is 1, Sunday is 7.
     */
    val isoDayNumber: Int = ordinal + 1


    companion object {
        val byDayNumber = entries.associateBy { it.isoDayNumber }

        fun forDayNumber(dayNumber: Int): DayOfWeek? = byDayNumber[dayNumber]
    }

}