package net.dankito.datetime

import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.isGreaterThanOrEqualTo
import assertk.assertions.isNotEqualTo
import kotlin.test.Test

class LocalDateTimeTest {

    @Test
    fun now() {
        val result = LocalDateTime.now()

        assertThat(result.year).isGreaterThanOrEqualTo(2025)
        assertThat(result.date).isNotEqualTo(LocalTime(0, 0, 0))
    }


    @Test
    fun toInstantAtUtc_WithoutDaylightSavingTime() {
        val dateTime = LocalDateTime(2025, 2, 17, 9, 8, 7, 654_000_000)

        val result = dateTime.toInstantAtUtc()

        assertThat(result.epochSeconds).isEqualTo(1_739_783_287)
        if (Instant.timeSinceEpochPrecision == TimeSinceEpochPrecision.Milliseconds) {
            assertThat(result.nanosecondsOfSecond).isEqualTo(654_000_000)
        }
    }

    @Test
    fun toInstantAtUtc_WithDaylightSavingTime() {
        val dateTime = LocalDateTime(2015, 10, 21, 9, 8, 7, 654_000_000)

        val result = dateTime.toInstantAtUtc()

        assertThat(result.epochSeconds).isEqualTo(1_445_418_487)
        if (Instant.timeSinceEpochPrecision == TimeSinceEpochPrecision.Milliseconds) {
            assertThat(result.nanosecondsOfSecond).isEqualTo(654_000_000)
        }
    }


    @Test
    fun toInstantAtSystemTimeZone_WithoutDaylightSavingTime() {
        val dateTime = LocalDateTime(2025, 2, 17, 9, 8, 7, 654_000_000)

        val result = dateTime.toInstantAtSystemTimeZone()

        assertThat(result.epochSeconds).isEqualTo(1_739_779_687)
        if (Instant.timeSinceEpochPrecision == TimeSinceEpochPrecision.Milliseconds) {
            assertThat(result.nanosecondsOfSecond).isEqualTo(654_000_000)
        }
    }

    @Test
    fun toInstantAtSystemTimeZone_WithDaylightSavingTime() {
        val dateTime = LocalDateTime(2015, 10, 21, 9, 8, 7, 654_000_000)

        val result = dateTime.toInstantAtSystemTimeZone()

        assertThat(result.epochSeconds).isEqualTo(1_445_411_287)
        if (Instant.timeSinceEpochPrecision == TimeSinceEpochPrecision.Milliseconds) {
            assertThat(result.nanosecondsOfSecond).isEqualTo(654_000_000)
        }
    }


    @Test
    fun compareTo_DateIsGreater() {
        val first = LocalDateTime(LocalDate(2025, 1, 2), LocalTime(10, 0, 0, 0))
        val second = LocalDateTime(LocalDate(2025, 1, 1), LocalTime(10, 0, 0, 0))

        val result = first.compareTo(second)

        assertThat(result).isEqualTo(1)
    }

    @Test
    fun compareTo_DateIsLess() {
        val first = LocalDateTime(LocalDate(2025, 1, 1), LocalTime(10, 0, 0, 0))
        val second = LocalDateTime(LocalDate(2025, 1, 2), LocalTime(10, 0, 0, 0))

        val result = first.compareTo(second)

        assertThat(result).isEqualTo(-1)
    }

    @Test
    fun compareTo_DateEqualsTimeIsGreater() {
        val first = LocalDateTime(LocalDate(2025, 1, 1), LocalTime(10, 0, 0, 1))
        val second = LocalDateTime(LocalDate(2025, 1, 1), LocalTime(10, 0, 0, 0))

        val result = first.compareTo(second)

        assertThat(result).isEqualTo(1)
    }

    @Test
    fun compareTo_DateEqualsTimeIsLess() {
        val first = LocalDateTime(LocalDate(2025, 1, 1), LocalTime(10, 0, 0, 0))
        val second = LocalDateTime(LocalDate(2025, 1, 1), LocalTime(10, 0, 0, 1))

        val result = first.compareTo(second)

        assertThat(result).isEqualTo(-1)
    }

    @Test
    fun compareTo_AllComponentsEqual() {
        val first = LocalDateTime(LocalDate(2025, 1, 1), LocalTime(10, 0, 0, 0))
        val second = LocalDateTime(LocalDate(2025, 1, 1), LocalTime(10, 0, 0, 0))

        val result = first.compareTo(second)

        assertThat(result).isEqualTo(0)
    }


    @Test
    fun toIsoString_millisecondsResolution() {
        val dateTime = LocalDateTime(2025, 1, 1, 0, 0, 0, 123_000_000)

        assertThat(dateTime.isoString).isEqualTo("2025-01-01T00:00:00.123")
    }

    @Test
    fun toIsoString_microsecondsResolution() {
        val dateTime = LocalDateTime(2025, 1, 1, 0, 0, 0, 123_456_000)

        assertThat(dateTime.isoString).isEqualTo("2025-01-01T00:00:00.123456")
    }

    @Test
    fun toIsoString_nanosecondsResolution() {
        val dateTime = LocalDateTime(2025, 1, 1, 0, 0, 0, 123_456_789)

        assertThat(dateTime.isoString).isEqualTo("2025-01-01T00:00:00.123456789")
    }

}