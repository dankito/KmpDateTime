package net.dankito.datetime.serialization

import assertk.assertThat
import assertk.assertions.isEqualTo
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import net.dankito.datetime.UtcOffset
import kotlin.test.Test

@OptIn(ExperimentalMultiplatform::class)
class UtcOffsetIso8601SerializerTest {

    @Test
    fun serializeToIsoOffsetString_HoursMinutesSeconds() {
        val result = Json.encodeToString(UtcOffset(9, 8, 7))

        assertThat(result).isEqualTo("\"+09:08:07\"")
    }

    @Test
    fun serializeToIsoOffsetString_HoursMinutes() {
        val result = Json.encodeToString(UtcOffset(9, 8, 0))

        assertThat(result).isEqualTo("\"+09:08\"")
    }

    @Test
    fun serializeToIsoOffsetString_Hours() {
        val result = Json.encodeToString(UtcOffset(9, 0, 0))

        assertThat(result).isEqualTo("\"+09:00\"")
    }

    @Test
    fun serializeToIsoOffsetString_Zulu() {
        val result = Json.encodeToString(UtcOffset(0, 0, 0))

        assertThat(result).isEqualTo("\"Z\"")
    }


    @Test
    fun deserializeFromIsoDateString_HoursMinutesSeconds() {
        val result = Json.decodeFromString<UtcOffset>("\"+09:08:07\"")

        assertThat(result).isEqualTo(UtcOffset(9, 8, 7))
    }

    @Test
    fun deserializeFromIsoDateString_Zulu() {
        val result = Json.decodeFromString<UtcOffset>("\"Z\"")

        assertThat(result).isEqualTo(UtcOffset.UTC)
    }

}