package net.dankito.datetime

import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.isNotEqualTo
import kotlin.test.Test

class LocalTimeTest {

    @Test
    fun now() {
        val result = LocalTime.now()

        assertThat(result).isNotEqualTo(LocalTime(0, 0, 0))
    }


    @Test
    fun compareTo_HourIsGreater() {
        val first = LocalTime(10, 0, 0)
        val second = LocalTime(9, 0, 0)

        val result = first.compareTo(second)

        assertThat(result).isEqualTo(1)
    }

    @Test
    fun compareTo_HourIsLess() {
        val first = LocalTime(10, 0, 0)
        val second = LocalTime(11, 0, 0)

        val result = first.compareTo(second)

        assertThat(result).isEqualTo(-1)
    }

    @Test
    fun compareTo_HourEqualsMinuteIsGreater() {
        val first = LocalTime(10, 1, 0)
        val second = LocalTime(10, 0, 0)

        val result = first.compareTo(second)

        assertThat(result).isEqualTo(1)
    }

    @Test
    fun compareTo_HourEqualsMinuteIsLess() {
        val first = LocalTime(10, 0, 0)
        val second = LocalTime(10, 2, 0)

        val result = first.compareTo(second)

        assertThat(result).isEqualTo(-1)
    }

    @Test
    fun compareTo_HourAndMinuteEqualSecondIsGreater() {
        val first = LocalTime(10, 0, 1)
        val second = LocalTime(10, 0, 0)

        val result = first.compareTo(second)

        assertThat(result).isEqualTo(1)
    }

    @Test
    fun compareTo_HourAndMinuteEqualSecondIsLess() {
        val first = LocalTime(10, 0, 0)
        val second = LocalTime(10, 0, 1)

        val result = first.compareTo(second)

        assertThat(result).isEqualTo(-1)
    }

    @Test
    fun compareTo_HourMinuteAndSecondEqualNanosecondOfSecondIsGreater() {
        val first = LocalTime(10, 0, 0, 1)
        val second = LocalTime(10, 0, 0, 0)

        val result = first.compareTo(second)

        assertThat(result).isEqualTo(1)
    }

    @Test
    fun compareTo_HourMinuteAndSecondEqualNanosecondOfSecondIsLess() {
        val first = LocalTime(10, 0, 0, 0)
        val second = LocalTime(10, 0, 0, 1)

        val result = first.compareTo(second)

        assertThat(result).isEqualTo(-1)
    }

    @Test
    fun compareTo_AllComponentsEqual() {
        val first = LocalTime(10, 0, 0, 0)
        val second = LocalTime(10, 0, 0, 0)

        val result = first.compareTo(second)

        assertThat(result).isEqualTo(0)
    }


    @Test
    fun toIsoString_millisecondsResolution() {
        val time = LocalTime(0, 0, 0, 123_000_000)

        assertThat(time.isoString).isEqualTo("00:00:00.123")
    }

    @Test
    fun toIsoString_microsecondsResolution() {
        val time = LocalTime(0, 0, 0, 123_456_000)

        assertThat(time.isoString).isEqualTo("00:00:00.123456")
    }

    @Test
    fun toIsoString_nanosecondsResolution() {
        val time = LocalTime(0, 0, 0, 123_456_789)

        assertThat(time.isoString).isEqualTo("00:00:00.123456789")
    }

}