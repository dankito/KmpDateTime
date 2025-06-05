package net.dankito.datetime.format.pattern

import net.dankito.datetime.*
import net.dankito.datetime.format.pattern.component.*

open class DateTimeComponentFormatter(
    protected val parser: DateTimeFormatPatternParser = DateTimeFormatPatternParser.Default
) {

    companion object {
        val Default = DateTimeComponentFormatter()
    }


    open fun format(date: LocalDate, pattern: String): String = format(date, parser.parse(pattern))

    open fun format(date: LocalDate, pattern: DateTimeFormatPattern): String = buildString {
        pattern.components.forEach { component ->
            when (component) {
                is YearComponent -> append(formatYear(date.year, component))
                is MonthComponent -> append(formatMonth(date.month, component))
                is DayOfMonthComponent -> append(ensureMinLength(date.day, component))
                is DayOfYearComponent -> append(ensureMinLength(date.dayOfYear, component))
                is DayOfWeekComponent -> append(formatDayOfWeek(date.dayOfWeek, component))
                is LiteralComponent -> append(component.literal)
                else -> throw IllegalArgumentException("${component::class} is not a component of LocalDate")
            }
        }
    }


    open fun format(time: LocalTime, pattern: String): String = format(time, parser.parse(pattern))

    open fun format(time: LocalTime, pattern: DateTimeFormatPattern): String = buildString {
        pattern.components.forEach { component ->
            when (component) {
                is HourComponent -> append(formatHour(time.hour, component))
                is MinuteComponent -> append(ensureMinLength(time.minute, component))
                is SecondComponent -> append(ensureMinLength(time.second, component))
                is FractionalSecondComponent -> append(ensureLength(time.nanosecondOfSecond, component))
                is LiteralComponent -> append(component.literal)
                else -> throw IllegalArgumentException("${component::class} is not a component of LocalDate")
            }
        }
    }


    open fun format(dateTime: LocalDateTime, pattern: String): String = format(dateTime, parser.parse(pattern))

    open fun format(dateTime: LocalDateTime, pattern: DateTimeFormatPattern): String = buildString {
        pattern.components.forEach { component ->
            when (component) {
                // date
                is YearComponent -> append(formatYear(dateTime.year, component))
                is MonthComponent -> append(formatMonth(dateTime.month, component))
                is DayOfMonthComponent -> append(ensureMinLength(dateTime.day, component))
                is DayOfYearComponent -> append(ensureMinLength(dateTime.dayOfYear, component))
                is DayOfWeekComponent -> append(formatDayOfWeek(dateTime.dayOfWeek, component))

                // time
                is HourComponent -> append(formatHour(dateTime.hour, component))
                is MinuteComponent -> append(ensureMinLength(dateTime.minute, component))
                is SecondComponent -> append(ensureMinLength(dateTime.second, component))
                is FractionalSecondComponent -> append(ensureLength(dateTime.nanosecondOfSecond, component))

                is LiteralComponent -> append(component.literal)
                else -> throw IllegalArgumentException("${component::class} is not a component of LocalDate")
            }
        }
    }


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

    protected open fun formatDayOfWeek(dayOfWeek: DayOfWeek?, component: DayOfWeekComponent): String {
        if (dayOfWeek == null) {
            return "<day_of_week_not_available"
        }

        val englishName = dayOfWeek.name // TODO: localize

        return when (component.style) {
            DayOfWeekStyle.Abbreviated -> englishName.take(3)
            DayOfWeekStyle.Wide -> englishName
            DayOfWeekStyle.Narrow -> englishName.take(1)
            DayOfWeekStyle.Short -> englishName.take(2)
        }
    }

    protected open fun formatHour(hour: Int, component: HourComponent): String = when (component.style) {
        HourStyle.Hour_12_Start_1 -> ensureMinLength(if (hour == 0 || hour == 12) 12 else hour % 12, component)
        HourStyle.Hour_24_Start_0 -> ensureMinLength(hour, component)
        HourStyle.Hour_12_Start_0 -> ensureMinLength(hour % 12, component)
        HourStyle.Hour_24_Start_1 -> ensureMinLength(if (hour == 0) 24 else hour, component)
    }


    protected open fun ensureMinLength(value: Int?, component: DateTimeFormatPatternComponentWithMinLength): String =
        ensureMinLength(value, component.minLength)

    protected open fun ensureMinLength(value: Int?, minLength: Int): String =
        value?.toString()?.padStart(minLength, '0')
            ?: ""

    protected open fun ensureLength(value: Int, component: DateTimeFormatPatternComponentWithFixedLength): String =
        ensureLength(value, component.length)

    protected open fun ensureLength(value: Int, length: Int): String =
        ensureMinLength(value, length).take(length)

}