package net.dankito.datetime.format.pattern.component

enum class HourStyle {

    /**
     * h: 1, 12
     * hh: 01, 12
     */
    Hour_12_Start_1,

    /**
     * H: 0, 23
     * HH: 00, 23
     */
    Hour_24_Start_0,

    /**
     * K: 0, 11
     * KK: 00, 11
     */
    Hour_12_Start_0,

    /**
     * k: 1, 24
     * kk: 01, 24
     *
     * No known Locale with that format, neither preferred nor allowed.
     */
    Hour_24_Start_1,

    /**
     * Uses additionally to AM and PM also Noon and Midnight like "May 6, noon" or “Nov 12, midnight”.
     *
     * May be upper or lowercase depending on the locale and other options.
     */
//    TwelveHourStart1_NoonAndMidnight("hb"),

    /**
     * Flexible day periods like “Nov 12, at night”
     *
     * May be upper or lowercase depending on the locale and other options.
     */
//    TwelveHourStart1_FlexibleDayPeriods("hB")

}