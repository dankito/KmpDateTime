package net.dankito.datetime.serialization

import assertk.assertThat
import assertk.assertions.isEqualTo
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import net.dankito.datetime.LocalDateTime
import kotlin.test.Test

class LocalDateTimeIso8601SerializerTest {

    @Test
    fun serializeToIsoDateString() {
        val result = Json.encodeToString(LocalDateTime(2015, 10, 21, 9, 8, 7, 654))

        assertThat(result).isEqualTo("\"2015-10-21T09:08:07.000000654\"")
    }

    @Test
    fun deserializeFromIsoDateString() {
        val result = Json.decodeFromString<LocalDateTime>("\"2015-10-21T09:08:07.654\"")

        assertThat(result).isEqualTo(LocalDateTime(2015, 10, 21, 9, 8, 7, 654000000))
    }

}