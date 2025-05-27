package net.dankito.datetime

import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.isGreaterThan
import kotlin.test.Test

class InstantTest {

    @Test
    fun now() {
        val now = Instant.now()

        assertThat(now.epochSeconds).isGreaterThan(1_745_000_000)
    }


    @Test
    fun toEpochMilliseconds() {
        val instant = Instant(1_739_783_287, 654_321_098)

        val result = instant.toEpochMilliseconds()

        assertThat(result).isEqualTo(1_739_783_287_654)
    }


    @Test
    fun toLocalDateTimeAtUtc_WithoutDaylightSavingTime() {
        val instant = Instant(1_739_783_287, 654_000_000)

        val result = instant.toLocalDateTimeAtUtc()

        val expected = LocalDateTime(2025, 2, 17, 9, 8, 7, 654_000_000)
        assertThat(result).isEqualTo(expected)
    }

    @Test
    fun toLocalDateTimeAtUtc_WithDaylightSavingTime() {
        val instant = Instant(1_445_418_487, 654_000_000)

        val result = instant.toLocalDateTimeAtUtc()

        val expected = LocalDateTime(2015, 10, 21, 9, 8, 7, 654_000_000)
        assertThat(result).isEqualTo(expected)
    }


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