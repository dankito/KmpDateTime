package net.dankito.datetime.format.pattern.component

enum class MonthStyle {

    /**
     * Example: 9, 12
     */
    NumericMinDigits,

    /**
     * Example: 09, 12
     */
    Numeric2Digits,

    /**
     * Example: Sep
     */
    Abbreviated,

    /**
     * Example: September
     */
    Wide,

    /**
     * Example: S
     */
    Narrow

}