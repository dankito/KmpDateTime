package net.dankito.datetime

import assertk.assertThat
import assertk.assertions.isEqualByComparingTo
import assertk.assertions.isEqualTo
import assertk.assertions.isGreaterThanOrEqualTo
import assertk.assertions.isTrue
import net.codinux.kotlin.platform.Platform
import net.codinux.kotlin.platform.isJavaScript
import kotlin.test.Test

class LocalDateTest {

    @Test
    fun today() {
        val result = LocalDate.today()

        assertThat(result.year).isGreaterThanOrEqualTo(2025)
    }


    @Test
    fun dayOfWeek_Monday() {
        val result = LocalDate(2025, 4, 21).dayOfWeek

        assertThat(result).isEqualTo(DayOfWeek.Monday)
    }

    @Test
    fun dayOfWeek_Wednesday() {
        val result = LocalDate(2015, 10, 21).dayOfWeek

        assertThat(result).isEqualTo(DayOfWeek.Wednesday)
    }

    @Test
    fun dayOfWeek_Sunday() {
        val result = LocalDate(2025, 4, 20).dayOfWeek

        assertThat(result).isEqualTo(DayOfWeek.Sunday)
    }


    @Test
    fun dayOfYear_January1st() {
        val result = LocalDate(2025, 1, 1).dayOfYear

        assertThat(result).isEqualTo(1)
    }

    @Test
    fun dayOfYear_December1st_NonLeapYear() {
        val result = LocalDate(2025, 12, 31).dayOfYear

        assertThat(result).isEqualTo(365)
    }

    @Test
    fun dayOfYear_December1st_LeapYear() {
        val result = LocalDate(2024, 12, 31).dayOfYear

        assertThat(result).isEqualTo(366)
    }

    @Test
    fun dayOfYear_DayWithDaylightSavingTime() { // JS needs special handling when day has daylight-saving time
        val result = LocalDate(2015, 10, 21).dayOfYear

        assertThat(result).isEqualTo(294)
    }


    @Test
    fun quarter_January() {
        val date = LocalDate(2025, Month.January, 1)

        assertThat(date.quarter).isEqualByComparingTo(Quarter.Q1)
    }

    @Test
    fun quarter_February() {
        val date = LocalDate(2025, Month.February, 1)

        assertThat(date.quarter).isEqualByComparingTo(Quarter.Q1)
    }

    @Test
    fun quarter_March() {
        val date = LocalDate(2025, Month.March, 1)

        assertThat(date.quarter).isEqualByComparingTo(Quarter.Q1)
    }

    @Test
    fun quarter_April() {
        val date = LocalDate(2025, Month.April, 1)

        assertThat(date.quarter).isEqualByComparingTo(Quarter.Q2)
    }

    @Test
    fun quarter_May() {
        val date = LocalDate(2025, Month.May, 1)

        assertThat(date.quarter).isEqualByComparingTo(Quarter.Q2)
    }

    @Test
    fun quarter_June() {
        val date = LocalDate(2025, Month.June, 1)

        assertThat(date.quarter).isEqualByComparingTo(Quarter.Q2)
    }

    @Test
    fun quarter_July() {
        val date = LocalDate(2025, Month.July, 1)

        assertThat(date.quarter).isEqualByComparingTo(Quarter.Q3)
    }

    @Test
    fun quarter_August() {
        val date = LocalDate(2025, Month.August, 1)

        assertThat(date.quarter).isEqualByComparingTo(Quarter.Q3)
    }

    @Test
    fun quarter_September() {
        val date = LocalDate(2025, Month.September, 1)

        assertThat(date.quarter).isEqualByComparingTo(Quarter.Q3)
    }

    @Test
    fun quarter_October() {
        val date = LocalDate(2025, Month.October, 1)

        assertThat(date.quarter).isEqualByComparingTo(Quarter.Q4)
    }

    @Test
    fun quarter_November() {
        val date = LocalDate(2025, Month.November, 1)

        assertThat(date.quarter).isEqualByComparingTo(Quarter.Q4)
    }

    @Test
    fun quarter_December() {
        val date = LocalDate(2025, Month.December, 1)

        assertThat(date.quarter).isEqualByComparingTo(Quarter.Q4)
    }


    @Test
    fun weekOfYear_FirstWeek() {
        if (Platform.isJavaScript) {
            return // TODO: does not work on JS Browser and NodeJS yet
        }

        val date = LocalDate(2025, 1, 4)

        assertThat(date.weekOfYear).isEqualTo(1)
    }

    @Test
    fun weekOfYear_LastWeek() {
        if (Platform.isJavaScript) {
            return // TODO: does not work on JS Browser and NodeJS yet
        }

        val date = LocalDate(2024, 12, 29)

        assertThat(date.weekOfYear).isEqualTo(52)
    }

    @Test
    fun weekOfYear_LastDayBelongsToNextYearsFirstWeek() {
        if (Platform.isJavaScript) {
            return // TODO: does not work on JS Browser and NodeJS yet
        }

        val date = LocalDate(2024, 12, 31)

        assertThat(date.weekOfYear).isEqualTo(1)
    }

    @Test
    fun weekOfYear_LastDay_Week53() {
        if (Platform.isJavaScript) {
            return // TODO: does not work on JS Browser and NodeJS yet
        }

        val date = LocalDate(2020, 12, 31)

        assertThat(date.weekOfYear).isEqualTo(53)
    }

    @Test
    fun weekOfYear_WeekInYear() {
        if (Platform.isJavaScript) {
            return // TODO: does not work on JS Browser and NodeJS yet
        }

        val date = LocalDate(2015, 10, 21)

        assertThat(date.weekOfYear).isEqualTo(43)
    }


    @Test
    fun isDaylightSavingTime() { // TODO: works only on machines with clocks in timezones with daylight-saving time
        val dayWithDaylightSavingTime = LocalDate(2015, 10, 21)

        assertThat(dayWithDaylightSavingTime.isInDaylightSavingTime).isTrue()
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