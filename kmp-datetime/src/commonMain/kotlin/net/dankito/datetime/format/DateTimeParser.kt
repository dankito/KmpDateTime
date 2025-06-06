package net.dankito.datetime.format

import net.dankito.datetime.*

object DateTimeParser {

    val LocalDatePattern = "yyyy-MM-dd"

    val LocalTimePattern = "HH:mm:ss(.SSSSSSSSS)"

    val LocalDateTimePattern = "${LocalDatePattern}'T'$LocalTimePattern"

    val InstantPattern = "${LocalDateTimePattern}Z"

    val UtcOffsetPattern = "Z|±[hh]:[mm](:[ss])|±[hh]([mm]([ss]))|±[hh][mm]"

    val OffsetDateTimePattern = "${LocalDateTimePattern}$UtcOffsetPattern"


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
        val secondAndMayNanosecondOfSecondParts = secondAndMayNanosecondOfSecond.split('.', limit = 2)
        val secondPart = secondAndMayNanosecondOfSecondParts.first()
        if (secondPart.length != 2) {
            return Pair("$typeName string '$parsedString' must be in ISO 8601 $isoTypeName representation '$pattern' but the second part '$secondPart' had ${secondPart.length} instead of 2 digits.", null)
        }
        val second = secondPart.toIntOrNull()
        if (second == null) {
            return Pair("$typeName string '$parsedString' must be in ISO 8601 $isoTypeName representation '$pattern' but the second part '$secondPart' could not be converted to an Int.", null)
        }

        val nanosecondOfSecond = if (secondAndMayNanosecondOfSecondParts.size == 1) 0 else {
            val nanosecondOfSecondPart = secondAndMayNanosecondOfSecondParts[1]
            if (nanosecondOfSecondPart.length < 1 || nanosecondOfSecondPart.length > 9) {
                return Pair("$typeName string '$parsedString' must be in ISO 8601 $isoTypeName representation '$pattern' but the nanosecond of second part '$nanosecondOfSecondPart' had ${nanosecondOfSecondPart.length} instead of 1-9 digits.", null)
            }
            val nanosecondOfSecond = nanosecondOfSecondPart.padEnd(9, '0').toIntOrNull()
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
        val date = parseIsoDateStringOrError(datePart, parsedString, typeName, isoTypeName, pattern)
        if (date.second == null) {
            return Pair(date.first, null)
        }

        val timePart = isoDateTimeTrimmed.substring(indicesOfT[0] + 1)
        val time = parseIsoTimeStringOrError(timePart, parsedString, typeName, isoTypeName, pattern)
        if (time.second == null) {
            return Pair(time.first, null)
        }

        return Pair(null, LocalDateTime(date.second!!, time.second!!))
    }


    fun parseIsoInstantStringOrNull(isoInstant: String): Instant? = parseIsoInstantStringOrError(isoInstant).second

    fun parseIsoInstantString(isoInstant: String): Instant = parseIsoInstantStringOrError(isoInstant).let { (errorString, instant) ->
        instant ?: throw IllegalArgumentException(errorString)
    }

    /**
     * The parameters [parsedString], [typeName], [isoTypeName] and [pattern] are only there to provide correct error
     * messages if this method is used for parsing the date time part of a larger string.
     */
    private fun parseIsoInstantStringOrError(isoInstant: String, parsedString: String = isoInstant, typeName: String = "Instant",
                                             isoTypeName: String = "instant", pattern: String = InstantPattern): Pair<String?, Instant?> {
        val isoInstantTrimmed = isoInstant.trim()
        if (isoInstantTrimmed.endsWith('Z', ignoreCase = true) == false) {
            return Pair("$typeName string '$parsedString' must be in ISO 8601 $isoTypeName representation '$pattern' but did not end with 'Z'.", null)
        }
        val indicesOfZ = isoInstantTrimmed.indicesOf('Z', ignoreCase = true)
        if (indicesOfZ.size != 1) {
            return Pair("$typeName string '$parsedString' must be in ISO 8601 $isoTypeName representation '$pattern' but contained ${indicesOfZ.size} 'Z' instead of 1.", null)
        }

        val dateTimePart = isoInstantTrimmed.substring(0, isoInstantTrimmed.length - 1)
        val dateTime = parseIsoDateTimeStringOrError(dateTimePart, parsedString, typeName, isoTypeName, pattern)
        if (dateTime.second == null) {
            return Pair(dateTime.first, null)
        }

        return Pair(null, dateTime.second!!.toInstantAtUtc())
    }


    fun parseUtcOffsetStringOrNull(utcOffset: String): UtcOffset? = parseUtcOffsetStringOrError(utcOffset).second

    fun parseUtcOffsetString(utcOffset: String): UtcOffset = parseUtcOffsetStringOrError(utcOffset).let { (errorString, parsedOffset) ->
        parsedOffset ?: throw IllegalArgumentException(errorString)
    }

    /**
     * The parameters [parsedString], [typeName], [isoTypeName] and [pattern] are only there to provide correct error
     * messages if this method is used for parsing the date time part of a larger string.
     */
    private fun parseUtcOffsetStringOrError(utcOffset: String, parsedString: String = utcOffset, typeName: String = "UtcOffset",
                                            isoTypeName: String = "UTC offset", pattern: String = UtcOffsetPattern): Pair<String?, UtcOffset?> {
        val utcOffsetTrimmed = utcOffset.trim()
        if ("Z".equals(utcOffsetTrimmed, ignoreCase = true)) {
            return Pair(null, UtcOffset.UTC)
        }

        if (utcOffsetTrimmed.startsWith('+') == false && utcOffsetTrimmed.startsWith('-') == false) {
            return Pair("$typeName string '$parsedString' must be in ISO 8601 $isoTypeName representation '$pattern' but the UTC offset part '$utcOffsetTrimmed' did not start either with '+', '-', 'Z' or 'z'.", null)
        }

        val isNegative = utcOffsetTrimmed.startsWith('-')
        val timePart = utcOffsetTrimmed.substring(1)
        val timeParts = timePart.split(':')

        if (timeParts.size == 1) { // time parts not separated by ':'
            if (timePart.toIntOrNull() == null) {
                return Pair("$typeName string '$parsedString' must be in ISO 8601 $isoTypeName representation '$pattern' " +
                        "but the time part of the UTC offset part '$utcOffsetTrimmed' does not consist only of digits or colons.", null)
            } else if (timePart.length !in listOf(2, 4, 6)) {
                return Pair("$typeName string '$parsedString' must be in ISO 8601 $isoTypeName representation '$pattern' " +
                        "but the time part without colons of the UTC offset part '$utcOffsetTrimmed' must either have " +
                        "two (hours only), four (hours and minutes) or six (hours, minutes and seconds) digits.", null)
            }

            val hours = timePart.substring(0, 2).toInt().let { if (isNegative) -it else it }
            val minutes = if (timePart.length > 2) timePart.substring(2, 4).toInt() else 0
            val seconds = if (timePart.length > 4) timePart.substring(4, 6).toInt() else 0
            return Pair(null, UtcOffset(hours, minutes, seconds))
        }

        // time parts separated by ':'
        if (timeParts.size > 3) {
            return Pair("$typeName string '$parsedString' must be in ISO 8601 $isoTypeName representation '$pattern' " +
                    "but the colon separated time part of the UTC offset part '$utcOffsetTrimmed' has more then 3 parts (at maximum hours, minutes and seconds are allowed).", null)
        }
        if (timeParts.any { it.toIntOrNull() == null }) {
            return Pair("$typeName string '$parsedString' must be in ISO 8601 $isoTypeName representation '$pattern' " +
                    "but the time part of the UTC offset part '$utcOffsetTrimmed' does not consist only of digits or colons.", null)
        }
        if (timeParts.any { it.length != 2 }) {
            return Pair("$typeName string '$parsedString' must be in ISO 8601 $isoTypeName representation '$pattern' " +
                    "but all colon separated time part of the UTC offset part '$utcOffsetTrimmed' must consist of two digits (like '02', '14', '00').", null)
        }

        val hours = timeParts[0].toInt().let { if (isNegative) -it else it }
        val minutes = if (timeParts.size > 1) timeParts[1].toInt() else 0
        val seconds = if (timeParts.size > 2) timeParts[2].toInt() else 0

        return Pair(null, UtcOffset(hours, minutes, seconds))
    }


    fun parseOffsetDateTimeStringOrNull(utcOffset: String): OffsetDateTime? = parseOffsetDateTimeStringOrError(utcOffset).second

    fun parseOffsetDateTimeString(utcOffset: String): OffsetDateTime = parseOffsetDateTimeStringOrError(utcOffset).let { (errorString, dateTime) ->
        dateTime ?: throw IllegalArgumentException(errorString)
    }

    /**
     * The parameters [parsedString], [typeName], [isoTypeName] and [pattern] are only there to provide correct error
     * messages if this method is used for parsing the date time part of a larger string.
     */
    private fun parseOffsetDateTimeStringOrError(offsetDateTime: String, parsedString: String = offsetDateTime, typeName: String = "OffsetDateTime",
                                                 isoTypeName: String = "offset datetime", pattern: String = OffsetDateTimePattern): Pair<String?, OffsetDateTime?> {
        val trimmed = offsetDateTime.trim()

        return if (trimmed.endsWith('Z', ignoreCase = true)) {
            val localDateTimeOrError = parseIsoDateTimeStringOrError(trimmed.substring(0, trimmed.length - 1), parsedString, typeName, isoTypeName, pattern)
            if (localDateTimeOrError.second != null) {
                Pair(null, OffsetDateTime(localDateTimeOrError.second!!, UtcOffset.UTC))
            } else {
                Pair(localDateTimeOrError.first, null)
            }
        } else {
            val indexOfPlusOrMinus = trimmed.lastIndexOf('+').takeUnless { it < 0 }
                ?: trimmed.lastIndexOf('-').takeUnless { it < 0 }
            if (indexOfPlusOrMinus == null) {
                Pair("$typeName string '$parsedString' must be in ISO 8601 $isoTypeName representation '$pattern' " +
                        "but does neither end with 'Z' or 'z', nor with a zone offset that starts with '+' or '-'.", null)
            } else {
                val localTimeOrError = parseIsoDateTimeStringOrError(trimmed.substring(0, indexOfPlusOrMinus), parsedString, typeName, isoTypeName, pattern)
                val offsetOrError = parseUtcOffsetStringOrError(trimmed.substring(indexOfPlusOrMinus), parsedString, typeName, isoTypeName, pattern)
                if (localTimeOrError.second == null) {
                    Pair(localTimeOrError.first, null)
                } else if (offsetOrError.second == null) {
                    Pair(offsetOrError.first, null)
                } else {
                    Pair(null, OffsetDateTime(localTimeOrError.second!!, offsetOrError.second!!))
                }
            }
        }
    }



    /**
     * Finds all indices of [char] in this CharSequence
     */
    private fun CharSequence.indicesOf(char: Char, ignoreCase: Boolean = false): List<Int> {
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