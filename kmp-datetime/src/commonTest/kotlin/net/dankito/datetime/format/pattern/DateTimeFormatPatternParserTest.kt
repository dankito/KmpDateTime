package net.dankito.datetime.format.pattern

import assertk.assertThat
import assertk.assertions.hasSize
import assertk.assertions.isEqualTo
import assertk.assertions.isInstanceOf
import net.dankito.datetime.format.pattern.component.LiteralComponent
import kotlin.test.Test
import kotlin.test.assertFailsWith

class DateTimeFormatPatternParserTest {

    private val underTest = DateTimeFormatPatternParser()


    @Test
    fun literalString() {
        val result = underTest.parsePattern("'T'")

        assertThat(result.components).hasSize(1)

        val component = result.components.first()
        assertThat(component).isInstanceOf<LiteralComponent>()
        assertThat((component as LiteralComponent).literal).isEqualTo("T")
    }

    @Test
    fun unclosedLiteralString() {
        assertFailsWith<IllegalArgumentException> {
            underTest.parsePattern("'T")
        }
    }

}