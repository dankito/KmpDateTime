package net.dankito.kotlin.datetime

import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.isGreaterThanOrEqualTo
import kotlin.test.Test

class LocalDateTest {

    @Test
    fun today() {
        val result = LocalDate.today()

        assertThat(result.year).isGreaterThanOrEqualTo(2025)
    }


    @Test
    fun compareTo_YearIsGreater() {
        val first = LocalDate(2025, 1, 1)
        val second = LocalDate(2024, 1, 1)

        val result = first.compareTo(second)

        assertThat(result).isEqualTo(1)
    }

    @Test
    fun compareTo_YearIsLess() {
        val first = LocalDate(2023, 1, 1)
        val second = LocalDate(2024, 1, 1)

        val result = first.compareTo(second)

        assertThat(result).isEqualTo(-1)
    }

    @Test
    fun compareTo_YearEqualsMonthIsGreater() {
        val first = LocalDate(2025, 2, 1)
        val second = LocalDate(2025, 1, 1)

        val result = first.compareTo(second)

        assertThat(result).isEqualTo(1)
    }

    @Test
    fun compareTo_YearEqualsMonthIsLess() {
        val first = LocalDate(2025, 1, 1)
        val second = LocalDate(2025, 2, 1)

        val result = first.compareTo(second)

        assertThat(result).isEqualTo(-1)
    }

    @Test
    fun compareTo_YearAndMonthEqualDayIsGreater() {
        val first = LocalDate(2025, 1, 2)
        val second = LocalDate(2025, 1, 1)

        val result = first.compareTo(second)

        assertThat(result).isEqualTo(1)
    }

    @Test
    fun compareTo_YearAndMonthEqualDayIsLess() {
        val first = LocalDate(2025, 1, 1)
        val second = LocalDate(2025, 1, 2)

        val result = first.compareTo(second)

        assertThat(result).isEqualTo(-1)
    }

    @Test
    fun compareTo_AllComponentsEqual() {
        val first = LocalDate(2025, 1, 1)
        val second = LocalDate(2025, 1, 1)

        val result = first.compareTo(second)

        assertThat(result).isEqualTo(0)
    }

}