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
    fun toEpochMicroseconds() {
        val instant = Instant(1_739_783_287, 654_321_098)

        val result = instant.toEpochMicroseconds()

        assertThat(result).isEqualTo(1_739_783_287_654_321)
    }

    @Test
    fun toEpochMicrosecondsString() {
        val instant = Instant(1_739_783_287, 654_321_098)

        val result = instant.toEpochMicrosecondsString()

        assertThat(result).isEqualTo(instant.toEpochMicroseconds().toString())
    }

    @Test
    fun toEpochNanoseconds() {
        val instant = Instant(1_739_783_287, 654_321_098)

        val result = instant.toEpochNanoseconds()

        assertThat(result).isEqualTo(1_739_783_287_654_321_098)
    }

    @Test
    fun toEpochNanosecondsString() {
        val instant = Instant(1_739_783_287, 654_321_098)

        val result = instant.toEpochNanosecondsString()

        assertThat(result).isEqualTo(instant.toEpochNanoseconds().toString())
    }


    @Test
    fun plusNanoseconds() {
        val instant = Instant(1_739_783_287, 654_321_098)

        val result = instant.plusNanoseconds(300)

        assertThat(result).isEqualTo(Instant(1_739_783_287, 654_321_398))
    }

    @Test
    fun plusMicroseconds() {
        val instant = Instant(1_739_783_287, 654_321_098)

        val result = instant.plusMicroseconds(700)

        assertThat(result).isEqualTo(Instant(1_739_783_287, 655_021_098))
    }

    @Test
    fun plusMilliseconds() {
        val instant = Instant(1_739_783_287, 654_321_098)

        val result = instant.plusMilliseconds(450)

        assertThat(result).isEqualTo(Instant(1_739_783_288, 104_321_098))
    }

    @Test
    fun plusSeconds() {
        val instant = Instant(1_739_783_287, 654_321_098)

        val result = instant.plusSeconds(824)

        assertThat(result).isEqualTo(Instant(1_739_784_111, 654_321_098))
    }

    @Test
    fun plusMinutes() {
        val instant = Instant(1_739_783_287, 654_321_098)

        val result = instant.plusMinutes(6188800)

        assertThat(result).isEqualTo(Instant(2_111_111_287, 654_321_098))
    }

    @Test
    fun plusHours() {
        val instant = LocalDateTime(2015, 10, 22, 22, 15, 47).toInstantAtUtc()

        val result = instant.plusHours(4)

        assertThat(result).isEqualTo(LocalDateTime(2015, 10, 23, 2, 15, 47).toInstantAtUtc())
    }

    @Test
    fun plusDays() {
        val instant = LocalDateTime(2015, 10, 22, 22, 15, 47).toInstantAtUtc()

        val result = instant.plusDays(21)

        assertThat(result).isEqualTo(LocalDateTime(2015, 11, 12, 22, 15, 47).toInstantAtUtc())
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


    @Test
    fun toIsoString_millisecondsResolution() {
        val instant = LocalDateTime(2025, 1, 1, 0, 0, 0, 123_000_000).toInstantAtUtc()

        assertThat(instant.isoString).isEqualTo("2025-01-01T00:00:00.123Z")
    }

    @Test
    fun toIsoString_microsecondsResolution() {
        val instant = LocalDateTime(2025, 1, 1, 0, 0, 0, 123_456_000).toInstantAtUtc()

        assertThat(instant.isoString).isEqualTo("2025-01-01T00:00:00.123456Z")
    }

    @Test
    fun toIsoString_nanosecondsResolution() {
        val instant = LocalDateTime(2025, 1, 1, 0, 0, 0, 123_456_789).toInstantAtUtc()

        assertThat(instant.isoString).isEqualTo("2025-01-01T00:00:00.123456789Z")
    }

}