package net.dankito.kotlin.datetime.serialization

import assertk.assertThat
import assertk.assertions.isEqualTo
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import net.dankito.kotlin.datetime.LocalTime
import kotlin.test.Test

class LocalTimeIso8601SerializerTest {

    @Test
    fun serializeToIsoDateString() {
        val result = Json.encodeToString(LocalTime(9, 8, 7, 654))

        assertThat(result).isEqualTo("\"09:08:07.000000654\"")
    }

    @Test
    fun deserializeFromIsoDateString() {
        val result = Json.decodeFromString<LocalTime>("\"09:08:07.654\"")

        assertThat(result).isEqualTo(LocalTime(9, 8, 7, 654000000))
    }

}