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