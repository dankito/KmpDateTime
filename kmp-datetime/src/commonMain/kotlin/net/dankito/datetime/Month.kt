package net.dankito.datetime

import kotlinx.serialization.Serializable
import net.dankito.datetime.serialization.MonthNameSerializer

@Serializable(with = MonthNameSerializer::class) // don't know why, but without serializing fails in JS and native
enum class Month {
    January,
    February,
    March,
    April,
    May,
    June,
    July,
    August,
    September,
    October,
    November,
    December
    ;
    
    val number: Int = ordinal + 1


    companion object {
        private val byNumber = entries.associateBy { it.number }

        fun forNumber(number: Int): Month =
            byNumber[number]
                ?: throw IllegalArgumentException("Number for Month must be in range of 1 to 12, but was $number")
    }

}