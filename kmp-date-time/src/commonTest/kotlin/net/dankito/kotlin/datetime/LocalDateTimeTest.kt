package net.dankito.kotlin.datetime

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

        assertThat(result.epochSeconds).isEqualTo(1739783287)
        assertThat(result.nanosecondsOfSecond).isEqualTo(654_000_000)
    }

    @Test
    fun toInstantAtUtc_WithDaylightSavingTime() {
        val dateTime = LocalDateTime(2015, 10, 21, 9, 8, 7, 654_000_000)

        val result = dateTime.toInstantAtUtc()

        assertThat(result.epochSeconds).isEqualTo(1_445_418_487)
        assertThat(result.nanosecondsOfSecond).isEqualTo(654_000_000)
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

}