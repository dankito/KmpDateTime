package net.dankito.datetime.serialization

import assertk.assertThat
import assertk.assertions.isEqualTo
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import net.dankito.datetime.LocalDate
import kotlin.test.BeforeTest
import kotlin.test.Test

class LocalDateIso8601SerializerTest {

    @BeforeTest
    fun setup() {
        SerializationConfig.LocalDateDefaultFormat = DateTimeSerializationFormat.Iso8601
    }


    @Test
    fun serializeToIsoDateString() {
        val result = Json.encodeToString(LocalDate(2015, 10, 21))

        assertThat(result).isEqualTo("\"2015-10-21\"")
    }

    @Test
    fun deserializeFromIsoDateString() {
        val result = Json.decodeFromString<LocalDate>("\"2015-10-21\"")

        assertThat(result).isEqualTo(LocalDate(2015, 10, 21))
    }

}