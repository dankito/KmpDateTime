package net.dankito.datetime.serialization

import assertk.assertThat
import assertk.assertions.isEqualTo
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import net.dankito.datetime.OffsetDateTime
import net.dankito.datetime.UtcOffset
import kotlin.test.Test

class OffsetDateTimeIso8601SerializerTest {

    @Test
    fun serializeToIsoDateString() {
        val result = Json.encodeToString(OffsetDateTime(2015, 10, 21, 9, 8, 7, 654, UtcOffset(-14, 20)))

        assertThat(result).isEqualTo("\"2015-10-21T09:08:07.000000654-14:20\"")
    }

    @Test
    fun deserializeFromIsoDateString() {
        val result = Json.decodeFromString<OffsetDateTime>("\"2015-10-21T09:08:07.654-14:20\"")

        assertThat(result).isEqualTo(OffsetDateTime(2015, 10, 21, 9, 8, 7, 654000000, UtcOffset(-14, 20)))
    }

}