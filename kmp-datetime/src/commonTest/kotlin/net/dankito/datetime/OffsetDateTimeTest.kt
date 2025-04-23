package net.dankito.datetime

import assertk.assertThat
import assertk.assertions.isEqualTo
import kotlin.test.Test

@OptIn(ExperimentalMultiplatform::class)
class OffsetDateTimeTest {

    @Test
    fun parse_PositiveOffset() {
        val result = OffsetDateTime.parse("2015-10-21T09:08:07.000000654+05:30")

        assertOffsetDateTime(result, LocalDateTime(2015, 10, 21, 9, 8, 7, 654), UtcOffset(5, 30))
    }

    @Test
    fun parse_NegativeOffset() {
        val result = OffsetDateTime.parse("2015-10-21T09:08:07.000000654-14:20")

        assertOffsetDateTime(result, LocalDateTime(2015, 10, 21, 9, 8, 7, 654), UtcOffset(-14, 20))
    }

    @Test
    fun parse_Zulu() {
        val result = OffsetDateTime.parse("2015-10-21T09:08:07.000000654Z")

        assertOffsetDateTime(result, LocalDateTime(2015, 10, 21, 9, 8, 7, 654), UtcOffset.UTC)
    }


    @Test
    fun isoString_PositiveOffset() {
        val dateTime = OffsetDateTime(2015, 10, 21, 9, 8, 7, 654, UtcOffset(5, 30))

        val result = dateTime.isoString

        assertThat(result).isEqualTo("2015-10-21T09:08:07.000000654+05:30")
    }

    @Test
    fun isoString_NegativeOffset() {
        val dateTime = OffsetDateTime(2015, 10, 21, 9, 8, 7, 654, UtcOffset(-14, 20))

        val result = dateTime.isoString

        assertThat(result).isEqualTo("2015-10-21T09:08:07.000000654-14:20")
    }

    @Test
    fun isoString_Zulu() {
        val dateTime = OffsetDateTime(2015, 10, 21, 9, 8, 7, 654, UtcOffset.UTC)

        val result = dateTime.isoString

        assertThat(result).isEqualTo("2015-10-21T09:08:07.000000654Z")
    }


    private fun assertOffsetDateTime(dateTime: OffsetDateTime, expectedLocalDateTime: LocalDateTime, expectedUtcOffset: UtcOffset) {
        assertThat(dateTime.dateTime).isEqualTo(expectedLocalDateTime)
        assertThat(dateTime.offset).isEqualTo(expectedUtcOffset)
    }

}