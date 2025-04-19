package net.dankito.kotlin.datetime.format

import net.dankito.kotlin.datetime.LocalDate
import net.dankito.kotlin.datetime.LocalTime

object DateTimeParser {

    fun parseIsoDateStringOrNull(isoDate: String): LocalDate? = parseIsoDateStringOrError(isoDate).second

    fun parseIsoDateString(isoDate: String): LocalDate =
        parseIsoDateStringOrError(isoDate).second
            ?: throw IllegalArgumentException(parseIsoDateStringOrError(isoDate).first)

    private fun parseIsoDateStringOrError(isoDate: String): Pair<String?, LocalDate?> {
        val dashIndices = isoDate.trim().indicesOf('-')
        if (dashIndices.size != 2) {
            return Pair("Date string '$isoDate' must be in ISO 8601 date representation 'yyyy-MM-dd' but contained ${dashIndices.size} dash(es) instead of 2.", null)
        }

        val yearPart = isoDate.substring(0, dashIndices[0])
        if (yearPart.length < 4) {
            return Pair("Date string '$isoDate' must be in ISO 8601 date representation 'yyyy-MM-dd' but the year part '$yearPart' had ${yearPart.length} instead of 4 digits.", null)
        } else if (yearPart.length > 10) {
            return Pair("Date string '$isoDate' must be in ISO 8601 date representation 'yyyy-MM-dd' and the year part '$yearPart' may not exceed 10 decimal places but had ${yearPart.length}.", null)
        }
        val year = yearPart.toIntOrNull()
        if (year == null) {
            return Pair("Date string '$isoDate' must be in ISO 8601 date representation 'yyyy-MM-dd' but the year part '$yearPart' could not be converted to an Int.", null)
        }

        val monthPart = isoDate.substring(dashIndices[0] + 1, dashIndices[1])
        if (monthPart.length != 2) {
            return Pair("Date string '$isoDate' must be in ISO 8601 date representation 'yyyy-MM-dd' but the month part '$monthPart' had ${monthPart.length} instead of 2 digits.", null)
        }
        val month = monthPart.toIntOrNull()
        if (month == null) {
            return Pair("Date string '$isoDate' must be in ISO 8601 date representation 'yyyy-MM-dd' but the month part '$monthPart' could not be converted to an Int.", null)
        }

        val dayPart = isoDate.substring(dashIndices[1] + 1)
        if (dayPart.length != 2) {
            return Pair("Date string '$isoDate' must be in ISO 8601 date representation 'yyyy-MM-dd' but the day part '$dayPart' had ${dayPart.length} instead of 2 digits.", null)
        }
        val day = dayPart.toIntOrNull()
        if (day == null) {
            return Pair("Date string '$isoDate' must be in ISO 8601 date representation 'yyyy-MM-dd' but the day part '$dayPart' could not be converted to an Int.", null)
        }

        return Pair(null, LocalDate(year, month, day))
    }


    fun parseIsoTimeStringOrNull(isoTime: String): LocalTime? = parseIsoTimeStringOrError(isoTime).second

    fun parseIsoTimeString(isoTime: String): LocalTime =
        parseIsoTimeStringOrError(isoTime).second
            ?: throw IllegalArgumentException(parseIsoTimeStringOrError(isoTime).first)

    private fun parseIsoTimeStringOrError(isoTime: String): Pair<String?, LocalTime?> {
        val colonIndices = isoTime.trim().indicesOf(':')
        if (colonIndices.size != 2) {
            return Pair("Time string '$isoTime' must be in ISO 8601 time representation 'HH:mm:ss(.SSSSSSSSS)' but contained ${colonIndices.size} colon(s) instead of 2.", null)
        }

        val hourPart = isoTime.substring(0, colonIndices[0])
        if (hourPart.length != 2) {
            return Pair("Time string '$isoTime' must be in ISO 8601 time representation 'HH:mm:ss(.SSSSSSSSS)' but the hour part '$hourPart' had ${hourPart.length} instead of 2 digits.", null)
        }
        val hour = hourPart.toIntOrNull()
        if (hour == null) {
            return Pair("Time string '$isoTime' must be in ISO 8601 time representation 'HH:mm:ss(.SSSSSSSSS)' but the hour part '$hourPart' could not be converted to an Int.", null)
        }

        val minutePart = isoTime.substring(colonIndices[0] + 1, colonIndices[1])
        if (minutePart.length != 2) {
            return Pair("Time string '$isoTime' must be in ISO 8601 time representation 'HH:mm:ss(.SSSSSSSSS)' but the minute part '$minutePart' had ${minutePart.length} instead of 2 digits.", null)
        }
        val minute = minutePart.toIntOrNull()
        if (minute == null) {
            return Pair("Time string '$isoTime' must be in ISO 8601 time representation 'HH:mm:ss(.SSSSSSSSS)' but the minute part '$minutePart' could not be converted to an Int.", null)
        }

        val secondAndMayNanosecondOfSecond = isoTime.substring(colonIndices[1] + 1)
        val (secondPart, nanosecondOfSecondPart) = secondAndMayNanosecondOfSecond.split('.', limit = 2)
        if (secondPart.length != 2) {
            return Pair("Time string '$isoTime' must be in ISO 8601 time representation 'HH:mm:ss(.SSSSSSSSS)' but the second part '$secondPart' had ${secondPart.length} instead of 2 digits.", null)
        }
        val second = secondPart.toIntOrNull()
        if (second == null) {
            return Pair("Time string '$isoTime' must be in ISO 8601 time representation 'HH:mm:ss(.SSSSSSSSS)' but the second part '$secondPart' could not be converted to an Int.", null)
        }

        val countPeriodsInSecondAndMayNanosecondOfSecond = secondAndMayNanosecondOfSecond.count { it == '.' }
        val nanosecondOfSecond = if (countPeriodsInSecondAndMayNanosecondOfSecond == 0) 0 else {
            if (nanosecondOfSecondPart.length < 1 || nanosecondOfSecondPart.length > 9) {
                return Pair("Time string '$isoTime' must be in ISO 8601 time representation 'HH:mm:ss(.SSSSSSSSS)' but the nanosecond of second part '$nanosecondOfSecondPart' had ${nanosecondOfSecondPart.length} instead of 1-9 digits.", null)
            }
            val nanosecondOfSecond = nanosecondOfSecondPart.toIntOrNull()
            if (nanosecondOfSecond == null) {
                return Pair("Time string '$isoTime' must be in ISO 8601 time representation 'HH:mm:ss(.SSSSSSSSS)' but the nanosecond of second part '$nanosecondOfSecondPart' could not be converted to an Int.", null)
            }

            nanosecondOfSecond
        }

        return Pair(null, LocalTime(hour, minute, second, nanosecondOfSecond))
    }



    /**
     * Finds all indices of [char] in this CharSequence
     */
    fun CharSequence.indicesOf(char: Char): List<Int> {
        val indices = mutableListOf<Int>()
        var index = -1

        do {
            index = this.indexOf(char, index + 1)

            if (index > -1) {
                indices.add(index)
            }
        } while (index > -1)

        return indices
    }

}