package net.dankito.datetime.format.pattern.component

enum class QuarterStyle {

    /**
     * Example: 1, 4
     */
    NumericMinDigits,

    /**
     * Example: 01, 04
     */
    Numeric2Digits,

    /**
     * Example: Q1, Q4
     */
    Abbreviated,

    /**
     * Example: 2nd quarter
     */
    Wide,

    /**
     * Example: 2
     */
    Narrow

}