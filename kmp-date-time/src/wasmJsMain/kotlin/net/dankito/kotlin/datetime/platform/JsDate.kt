package net.dankito.kotlin.datetime.platform

// copied from kotlin.js
external class JsDate() {
    
    constructor(milliseconds: Long)

    constructor(dateString: String)

    constructor(year: Int, month: Int)

    constructor(year: Int, month: Int, day: Int)

    constructor(year: Int, month: Int, day: Int, hour: Int)

    constructor(year: Int, month: Int, day: Int, hour: Int, minute: Int)

    constructor(year: Int, month: Int, day: Int, hour: Int, minute: Int, second: Int)

    constructor(year: Int, month: Int, day: Int, hour: Int, minute: Int, second: Int, millisecond: Long)

    fun getDate(): Int

    fun getDay(): Int

    fun getFullYear(): Int

    fun getHours(): Int

    fun getMilliseconds(): Int

    fun getMinutes(): Int

    fun getMonth(): Int

    fun getSeconds(): Int

    fun getTime(): Double

    fun getTimezoneOffset(): Int

    fun getUTCDate(): Int

    fun getUTCDay(): Int

    fun getUTCFullYear(): Int

    fun getUTCHours(): Int

    fun getUTCMilliseconds(): Int

    fun getUTCMinutes(): Int

    fun getUTCMonth(): Int

    fun getUTCSeconds(): Int

    fun toDateString(): String

    fun toISOString(): String

//    fun toJSON(): Json
//
//    fun toLocaleDateString(locales: Array<String> = definedExternally, options: LocaleOptions = definedExternally): String

    fun toLocaleDateString(locales: String, options: LocaleOptions = definedExternally): String

//    fun toLocaleString(locales: Array<String> = definedExternally, options: LocaleOptions = definedExternally): String

    fun toLocaleString(locales: String, options: LocaleOptions = definedExternally): String

//    fun toLocaleTimeString(locales: Array<String> = definedExternally, options: LocaleOptions = definedExternally): String

    fun toLocaleTimeString(locales: String, options: LocaleOptions = definedExternally): String

    fun toTimeString(): String

    fun toUTCString(): String

    companion object {
        fun now(): Double

        fun parse(dateString: String): Double

        fun UTC(year: Int, month: Int): Double

        fun UTC(year: Int, month: Int, day: Int): Double

        fun UTC(year: Int, month: Int, day: Int, hour: Int): Double

        fun UTC(year: Int, month: Int, day: Int, hour: Int, minute: Int): Double

        fun UTC(year: Int, month: Int, day: Int, hour: Int, minute: Int, second: Int): Double

        fun UTC(year: Int, month: Int, day: Int, hour: Int, minute: Int, second: Int, millisecond: Long): Double
    }

    interface LocaleOptions {
        var localeMatcher: String?

        var timeZone: String?

        var hour12: Boolean?

        var formatMatcher: String?

        var weekday: String?

        var era: String?

        var year: String?

        var month: String?

        var day: String?

        var hour: String?

        var minute: String?

        var second: String?

        var timeZoneName: String?
    }
}

//inline fun dateLocaleOptions(init: Date.LocaleOptions.() -> Unit): Date.LocaleOptions {
//    val result = js("new Object()").unsafeCast<Date.LocaleOptions>()
//    init(result)
//    return result
//}