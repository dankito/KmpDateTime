package net.dankito.kotlin.datetime.format

import net.dankito.kotlin.datetime.LocalDate
import net.dankito.kotlin.datetime.LocalDateTime
import net.dankito.kotlin.datetime.LocalTime

object DateTimeParser {

    val LocalDatePattern = "yyyy-MM-dd"

    val LocalTimePattern = "HH:mm:ss(.SSSSSSSSS)"

    val LocalDateTimePattern = "${LocalDatePattern}'T'$LocalTimePattern"


    fun parseIsoDateStringOrNull(isoDate: String): LocalDate? = parseIsoDateStringOrError(isoDate).second

    fun parseIsoDateString(isoDate: String): LocalDate = parseIsoDateStringOrError(isoDate).let { (errorString, date) ->
        date ?: throw IllegalArgumentException(errorString)
    }

    /**
     * The parameters [parsedString], [typeName], [isoTypeName] and [pattern] are only there to provide correct error
     * messages if this method is used for parsing the date part of a larger string like for [parseIsoDateTimeStringOrError].
     */
    private fun parseIsoDateStringOrError(isoDate: String, parsedString: String = isoDate, typeName: String = "LocalDate",
                                          isoTypeName: String = "date", pattern: String = LocalDatePattern): Pair<String?, LocalDate?> {
        val isoDateTrimmed = isoDate.trim()
        val dashIndices = isoDateTrimmed.indicesOf('-')
        if (dashIndices.size != 2) {
            return Pair("$typeName string '$parsedString' must be in ISO 8601 $isoTypeName representation '$pattern' but contained ${dashIndices.size} dash(es) instead of 2.", null)
        }

        val yearPart = isoDateTrimmed.substring(0, dashIndices[0])
        if (yearPart.length < 4) {
            return Pair("$typeName string '$parsedString' must be in ISO 8601 $isoTypeName representation '$pattern' but the year part '$yearPart' had ${yearPart.length} instead of 4 digits.", null)
        } else if (yearPart.length > 10) {
            return Pair("$typeName string '$parsedString' must be in ISO 8601 $isoTypeName representation '$pattern' and the year part '$yearPart' may not exceed 10 decimal places but had ${yearPart.length}.", null)
        }
        val year = yearPart.toIntOrNull()
        if (year == null) {
            return Pair("$typeName string '$parsedString' must be in ISO 8601 $isoTypeName representation '$pattern' but the year part '$yearPart' could not be converted to an Int.", null)
        }

        val monthPart = isoDateTrimmed.substring(dashIndices[0] + 1, dashIndices[1])
        if (monthPart.length != 2) {
            return Pair("$typeName string '$parsedString' must be in ISO 8601 $isoTypeName representation '$pattern' but the month part '$monthPart' had ${monthPart.length} instead of 2 digits.", null)
        }
        val month = monthPart.toIntOrNull()
        if (month == null) {
            return Pair("$typeName string '$parsedString' must be in ISO 8601 $isoTypeName representation '$pattern' but the month part '$monthPart' could not be converted to an Int.", null)
        }

        val dayPart = isoDateTrimmed.substring(dashIndices[1] + 1)
        if (dayPart.length != 2) {
            return Pair("$typeName string '$parsedString' must be in ISO 8601 $isoTypeName representation '$pattern' but the day part '$dayPart' had ${dayPart.length} instead of 2 digits.", null)
        }
        val day = dayPart.toIntOrNull()
        if (day == null) {
            return Pair("$typeName string '$parsedString' must be in ISO 8601 $isoTypeName representation '$pattern' but the day part '$dayPart' could not be converted to an Int.", null)
        }

        return Pair(null, LocalDate(year, month, day))
    }


    fun parseIsoTimeStringOrNull(isoTime: String): LocalTime? = parseIsoTimeStringOrError(isoTime).second

    fun parseIsoTimeString(isoTime: String): LocalTime = parseIsoTimeStringOrError(isoTime).let { (errorString, time) ->
        time ?: throw IllegalArgumentException(errorString)
    }

    /**
     * The parameters [parsedString], [typeName], [isoTypeName] and [pattern] are only there to provide correct error
     * messages if this method is used for parsing the time part of a larger string like for [parseIsoDateTimeStringOrError].
     */
    private fun parseIsoTimeStringOrError(isoTime: String, parsedString: String = isoTime, typeName: String = "LocalTime", isoTypeName: String = "time", pattern: String = LocalTimePattern): Pair<String?, LocalTime?> {
        val isoTimeTrimmed = isoTime.trim()
        val colonIndices = isoTimeTrimmed.indicesOf(':')
        if (colonIndices.size != 2) {
            return Pair("$typeName string '$parsedString' must be in ISO 8601 $isoTypeName representation '$pattern' but contained ${colonIndices.size} colon(s) instead of 2.", null)
        }

        val hourPart = isoTimeTrimmed.substring(0, colonIndices[0])
        if (hourPart.length != 2) {
            return Pair("$typeName string '$parsedString' must be in ISO 8601 $isoTypeName representation '$pattern' but the hour part '$hourPart' had ${hourPart.length} instead of 2 digits.", null)
        }
        val hour = hourPart.toIntOrNull()
        if (hour == null) {
            return Pair("$typeName string '$parsedString' must be in ISO 8601 $isoTypeName representation '$pattern' but the hour part '$hourPart' could not be converted to an Int.", null)
        }

        val minutePart = isoTimeTrimmed.substring(colonIndices[0] + 1, colonIndices[1])
        if (minutePart.length != 2) {
            return Pair("$typeName string '$parsedString' must be in ISO 8601 $isoTypeName representation '$pattern' but the minute part '$minutePart' had ${minutePart.length} instead of 2 digits.", null)
        }
        val minute = minutePart.toIntOrNull()
        if (minute == null) {
            return Pair("$typeName string '$parsedString' must be in ISO 8601 $isoTypeName representation '$pattern' but the minute part '$minutePart' could not be converted to an Int.", null)
        }

        val secondAndMayNanosecondOfSecond = isoTimeTrimmed.substring(colonIndices[1] + 1)
        val (secondPart, nanosecondOfSecondPart) = secondAndMayNanosecondOfSecond.split('.', limit = 2)
        if (secondPart.length != 2) {
            return Pair("$typeName string '$parsedString' must be in ISO 8601 $isoTypeName representation '$pattern' but the second part '$secondPart' had ${secondPart.length} instead of 2 digits.", null)
        }
        val second = secondPart.toIntOrNull()
        if (second == null) {
            return Pair("$typeName string '$parsedString' must be in ISO 8601 $isoTypeName representation '$pattern' but the second part '$secondPart' could not be converted to an Int.", null)
        }

        val countPeriodsInSecondAndMayNanosecondOfSecond = secondAndMayNanosecondOfSecond.count { it == '.' }
        val nanosecondOfSecond = if (countPeriodsInSecondAndMayNanosecondOfSecond == 0) 0 else {
            if (nanosecondOfSecondPart.length < 1 || nanosecondOfSecondPart.length > 9) {
                return Pair("$typeName string '$parsedString' must be in ISO 8601 $isoTypeName representation '$pattern' but the nanosecond of second part '$nanosecondOfSecondPart' had ${nanosecondOfSecondPart.length} instead of 1-9 digits.", null)
            }
            val nanosecondOfSecond = nanosecondOfSecondPart.toIntOrNull()
            if (nanosecondOfSecond == null) {
                return Pair("$typeName string '$parsedString' must be in ISO 8601 $isoTypeName representation '$pattern' but the nanosecond of second part '$nanosecondOfSecondPart' could not be converted to an Int.", null)
            }

            nanosecondOfSecond
        }

        return Pair(null, LocalTime(hour, minute, second, nanosecondOfSecond))
    }


    fun parseIsoDateTimeStringOrNull(isoDateTime: String): LocalDateTime? = parseIsoDateTimeStringOrError(isoDateTime).second

    fun parseIsoDateTimeString(isoDateTime: String): LocalDateTime = parseIsoDateTimeStringOrError(isoDateTime).let { (errorString, dateTime) ->
        dateTime ?: throw IllegalArgumentException(errorString)
    }

    /**
     * The parameters [parsedString], [typeName], [isoTypeName] and [pattern] are only there to provide correct error
     * messages if this method is used for parsing the date time part of a larger string.
     */
    private fun parseIsoDateTimeStringOrError(isoDateTime: String, parsedString: String = isoDateTime, typeName: String = "LocalDateTime",
                                              isoTypeName: String = "date time", pattern: String = LocalDateTimePattern): Pair<String?, LocalDateTime?> {
        val isoDateTimeTrimmed = isoDateTime.trim()
        val indicesOfT = isoDateTimeTrimmed.indicesOf('T', ignoreCase = true)
        if (indicesOfT.size != 1) {
            return Pair("$typeName string '$parsedString' must be in ISO 8601 $isoTypeName representation '$pattern' but contained ${indicesOfT.size} 'T' instead of 1.", null)
        }

        val datePart = isoDateTimeTrimmed.substring(0, indicesOfT[0])
        val date = parseIsoDateStringOrError(datePart, parsedString, typeName, isoTypeName, LocalDateTimePattern)
        if (date.second == null) {
            return Pair(date.first, null)
        }

        val timePart = isoDateTimeTrimmed.substring(indicesOfT[0] + 1)
        val time = parseIsoTimeStringOrError(timePart, parsedString, typeName, isoTypeName, LocalDateTimePattern)
        if (time.second == null) {
            return Pair(time.first, null)
        }

        return Pair(null, LocalDateTime(date.second!!, time.second!!))
    }



    /**
     * Finds all indices of [char] in this CharSequence
     */
    fun CharSequence.indicesOf(char: Char, ignoreCase: Boolean = false): List<Int> {
        val indices = mutableListOf<Int>()
        var index = -1

        do {
            index = this.indexOf(char, index + 1, ignoreCase)

            if (index > -1) {
                indices.add(index)
            }
        } while (index > -1)

        return indices
    }

}