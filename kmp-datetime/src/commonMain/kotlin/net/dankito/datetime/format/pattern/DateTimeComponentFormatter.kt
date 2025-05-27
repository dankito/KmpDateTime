package net.dankito.datetime.format.pattern

import net.dankito.datetime.LocalDate
import net.dankito.datetime.LocalTime
import net.dankito.datetime.Month
import net.dankito.datetime.format.pattern.component.*

open class DateTimeComponentFormatter(
    protected val parser: DateTimeFormatPatternParser = DateTimeFormatPatternParser.Default
) {


    open fun format(date: LocalDate, pattern: String): String = format(date, parser.parse(pattern))

    open fun format(date: LocalDate, pattern: DateTimeFormatPattern): String = buildString {
        pattern.components.forEach { component ->
            when (component) {
                is YearComponent -> append(formatYear(date.year, component))
                is MonthComponent -> append(formatMonth(date.month, component))
                is DayOfMonthComponent -> append(ensureMinLength(date.day, component))
                is DayOfWeekComponent -> throw UnsupportedOperationException("Day of week component is not supported yet as determining a LocalDate's day of week is not implemented yet.")
                is LiteralComponent -> append(component.literal)
                else -> throw IllegalArgumentException("${component::class} is not a component of LocalDate")
            }
        }
    }


    protected open fun ensureMinLength(value: Int, component: DateTimeFormatPatternComponentWithMinLength): String =
        ensureMinLength(value, component.minLength)

    protected open fun ensureMinLength(value: Int, minLength: Int): String =
        value.toString().padStart(minLength, '0')

    protected open fun formatYear(year: Int, component: YearComponent): String {
        val formatted = year.toString().padStart(component.minLength, '0')

        return if (component.maxLength != null) {
            formatted.substring(formatted.length - component.maxLength)
        } else {
            formatted
        }
    }

    protected open fun formatMonth(month: Month, component: MonthComponent): String = when (component.style) {
        MonthStyle.NumericMinDigits -> month.number.toString()
        MonthStyle.Numeric2Digits -> ensureMinLength(month.number, 2)
        MonthStyle.Abbreviated -> month.name.take(3) // TODO: localize
        MonthStyle.Wide -> month.name // TODO: localize
        MonthStyle.Narrow -> month.name.take(1) // TODO: localize
    }

}