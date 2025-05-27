package net.dankito.datetime.format.pattern

import net.dankito.datetime.format.pattern.component.DateTimeFormatPatternComponent

data class DateTimeFormatPattern(
    val components: List<DateTimeFormatPatternComponent>
) {

    companion object {
        const val IsoDatePattern = "yyyy-MM-dd"
        const val IsoDateDotSeparatedPattern = "yyyy.MM.dd"
        const val IsoLocalTimePattern = "HH:mm:ss.SSSSSSSSS"
        const val IsoTimePattern = "$IsoLocalTimePattern'Z'"
        const val IsoDateTimePattern = "$IsoDatePattern'T'$IsoLocalTimePattern"

        val IsoDateComponents by lazy { DateTimeFormatPatternParser.Default.parse(IsoDatePattern) }
        val IsoDateDotSeparatedComponents by lazy { DateTimeFormatPatternParser.Default.parse(IsoDateDotSeparatedPattern) }
        val IsoLocalTimeComponents by lazy { DateTimeFormatPatternParser.Default.parse(IsoLocalTimePattern) }
        val IsoTimeComponents by lazy { DateTimeFormatPatternParser.Default.parse(IsoTimePattern) }
        val IsoDateTimeComponents by lazy { DateTimeFormatPatternParser.Default.parse(IsoDateTimePattern) }
    }

}