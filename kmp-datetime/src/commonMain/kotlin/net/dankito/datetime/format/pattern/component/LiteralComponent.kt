package net.dankito.datetime.format.pattern.component

data class LiteralComponent(
    val literal: String,
    /**
     * If the literal is enclosed in quotes or not like 'T'.
     */
    val masked: Boolean = false
) : DateTimeFormatPatternComponent