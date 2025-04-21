package net.dankito.datetime.serialization

import assertk.assertThat
import assertk.assertions.isEqualTo
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import net.dankito.datetime.LocalTime
import kotlin.test.BeforeTest
import kotlin.test.Test

class LocalTimeIso8601SerializerTest {

    @BeforeTest
    fun setup() {
        SerializationConfig.LocalTimeSerializationFormat = DateTimeSerializationFormat.Iso8601
    }


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