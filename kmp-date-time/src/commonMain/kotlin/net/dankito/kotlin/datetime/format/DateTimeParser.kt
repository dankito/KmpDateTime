package net.dankito.kotlin.datetime.format

import net.dankito.kotlin.datetime.LocalDate

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