package net.dankito.datetime.format.pattern.component

data class YearComponent(
    override val minLength: Int
) : DateTimeFormatPatternComponentWithMinLength {

    val maxLength: Int? = if (minLength == 2) 2 else null

}