package net.dankito.datetime.format.pattern

import net.dankito.datetime.format.pattern.component.*

open class DateTimeFormatPatternParser {

    /**
     * All date time format fields are documented here:
     * [https://www.unicode.org/reports/tr35/tr35-73/tr35-dates.html#date-format-patterns](https://www.unicode.org/reports/tr35/tr35-73/tr35-dates.html#date-format-patterns)
     * The table with the field symbols is a bit below that:
     * [https://www.unicode.org/reports/tr35/tr35-73/tr35-dates.html#table-date-field-symbol-table](https://www.unicode.org/reports/tr35/tr35-73/tr35-dates.html#table-date-field-symbol-table)
     */
    open fun parsePattern(pattern: String): DateTimeFormatPattern {
        val components = mutableListOf<DateTimeFormatPatternComponent>()
        var index = 0
        val length = pattern.length

        while (index < length) {
            val char = pattern[index]

            if (char == '\'') { // literal string mask
                index = parseLiteral(pattern, index + 1, length, components)
            } else if (char.isLetter() == false) { // e.g. '.', '-', ...
                components.add(LiteralComponent(char.toString()))
                index++
            } else {
                index = parseComponentPattern(pattern, char, index, components)
            }
        }

        return DateTimeFormatPattern(components)
    }


    protected open fun parseComponentPattern(pattern: String, formatSymbol: Char, startIndex: Int, components: MutableList<DateTimeFormatPatternComponent>): Int {
        var symbolLength = 1

        var index = startIndex + 1
        while (index < pattern.length && pattern[index] == formatSymbol) {
            symbolLength++
            index++
        }

        components.add(createComponent(formatSymbol, symbolLength))

        return index
    }

    protected open fun createComponent(formatSymbol: Char, length: Int): DateTimeFormatPatternComponent = when (formatSymbol) {

        // date components
        'y' -> YearComponent(length) // TODO: also handle Y, u, U and r
        'M' -> MonthComponent(mapMonthStyle(length)) // TODO: also handle L
        'd' -> DayOfMonthComponent(length) // TODO: also handle D, F and g
        'E' -> DayOfWeekComponent(mapDayOfWeekStyle(length)) // TODO: also handle e and c

        // TODO: also handle day period b, a and B

        // TODO: also handle quarter (Q, q) and week (w, W)

        // TODO: also handle period (a, b, B)

        // time components
        'h', 'H', 'k', 'K' -> HourComponent(mapHourStyle(formatSymbol), length) // TODO: also handle hb and hB
        'm' -> MinuteComponent(length)
        's' -> SecondComponent(length)
        'S' -> FractionalSecondComponent(length)

        // TODO: there's also A (millisconds in a day), but don't know if i ever will support that

        // TODO: also parse zone (z, Z, O, v, V, X)

        else -> throw IllegalArgumentException("Unknown format pattern symbol '$formatSymbol' encountered. Implemented ISO 8601 format pattern symbols are: y, M, d, H, m, s, S")
    }

    protected open fun mapMonthStyle(length: Int): MonthStyle = when (length) {
        1 -> MonthStyle.NumericMinDigits
        2 -> MonthStyle.Numeric2Digits
        3 -> MonthStyle.Abbreviated
        4 -> MonthStyle.Wide
        5 -> MonthStyle.Narrow
        // TODO: that's not full correct, only valid for 0 (the only remaining valid value); not specified for count 'M' > 5
        else -> throw IllegalArgumentException("Illegal 'M' pattern length of $length for Month encountered. Valid values are in range 'M' - 'MMMMM'")
    }

    protected open fun mapDayOfWeekStyle(length: Int): DayOfWeekStyle = when (length) {
        1, 2, 3 -> DayOfWeekStyle.Abbreviated
        4 -> DayOfWeekStyle.Wide
        5 -> DayOfWeekStyle.Narrow
        6 -> DayOfWeekStyle.Short
        // TODO: that's not full correct, only valid for 0 (the only remaining valid value); not specified for count 'E' > 6
        else -> throw IllegalArgumentException("Illegal 'E' pattern length of $length for Day of Week encountered. Valid values are in range 'E' - 'EEEEEE'")
    }

    protected open fun mapHourStyle(formatSymbol: Char): HourStyle = when (formatSymbol) {
        'h' -> HourStyle.Hour_12_Start_1
        'H' -> HourStyle.Hour_24_Start_0
        'K' -> HourStyle.Hour_12_Start_0
        'k' -> HourStyle.Hour_24_Start_1
        else -> throw IllegalArgumentException("Illegal '$formatSymbol' pattern symbol for Hour encountered. Valid values are 'h', 'H', 'k' and 'K'")
    }


    protected open fun parseLiteral(pattern: String, literalStartIndex: Int, length: Int, components: MutableList<DateTimeFormatPatternComponent>): Int {
        var index = literalStartIndex

        if (index < length && pattern[index] == '\'') { // escaped quote
            components.add(LiteralComponent("'"))
            return index + 1
        }

        // skip to end of literal
        while (index < length && pattern[index] != '\'') {
            index++
        }

        if (index == length) {
            throw IllegalArgumentException("DateTime pattern string ended with an unclosed literal string mask: $pattern")
        }

        components.add(LiteralComponent(pattern.substring(literalStartIndex, index), true))

        index++

        return index
    }

}