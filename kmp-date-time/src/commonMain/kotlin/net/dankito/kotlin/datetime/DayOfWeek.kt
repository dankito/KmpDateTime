package net.dankito.kotlin.datetime

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

}