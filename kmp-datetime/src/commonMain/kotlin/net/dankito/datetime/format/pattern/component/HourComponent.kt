package net.dankito.datetime.format.pattern.component

data class HourComponent(
    val style: HourStyle,
    override val minLength: Int
) : DateTimeFormatPatternComponentWithMinLength