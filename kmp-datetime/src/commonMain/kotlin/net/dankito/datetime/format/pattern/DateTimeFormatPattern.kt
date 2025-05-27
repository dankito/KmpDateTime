package net.dankito.datetime.format.pattern

import net.dankito.datetime.format.pattern.component.DateTimeFormatPatternComponent

data class DateTimeFormatPattern(
    val components: List<DateTimeFormatPatternComponent>
)