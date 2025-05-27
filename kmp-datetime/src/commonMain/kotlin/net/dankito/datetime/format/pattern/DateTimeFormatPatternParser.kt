package net.dankito.datetime.format.pattern

import net.dankito.datetime.format.pattern.component.DateTimeFormatPatternComponent
import net.dankito.datetime.format.pattern.component.LiteralComponent

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
            }
        }

        return DateTimeFormatPattern(components)
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

        components.add(LiteralComponent(pattern.substring(literalStartIndex, index)))

        index++

        return index
    }

}