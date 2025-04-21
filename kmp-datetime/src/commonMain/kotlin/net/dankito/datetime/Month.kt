package net.dankito.datetime

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