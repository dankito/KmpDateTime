package net.dankito.kotlin.datetime

import assertk.assertThat
import assertk.assertions.isEqualTo
import kotlin.test.Test

class InstantTest {

    @Test
    fun compareTo_EpochSecondsIsGreater() {
        val first = Instant(1, 0)
        val second = Instant(0, 0)

        val result = first.compareTo(second)

        assertThat(result).isEqualTo(1)
    }

    @Test
    fun compareTo_EpochSecondsIsLess() {
        val first = Instant(0, 0)
        val second = Instant(1, 0)

        val result = first.compareTo(second)

        assertThat(result).isEqualTo(-1)
    }

    @Test
    fun compareTo_EpochSecondsEqualsMonthIsGreater() {
        val first = Instant(0, 1)
        val second = Instant(0, 0)

        val result = first.compareTo(second)

        assertThat(result).isEqualTo(1)
    }

    @Test
    fun compareTo_EpochSecondsEqualsMonthIsLess() {
        val first = Instant(0, 0)
        val second = Instant(0, 1)

        val result = first.compareTo(second)

        assertThat(result).isEqualTo(-1)
    }

    @Test
    fun compareTo_AllComponentsEqual() {
        val first = Instant(0, 0)
        val second = Instant(0, 0)

        val result = first.compareTo(second)

        assertThat(result).isEqualTo(0)
    }

}