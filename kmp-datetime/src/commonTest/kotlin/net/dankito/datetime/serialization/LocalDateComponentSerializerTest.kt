package net.dankito.datetime.serialization

import assertk.assertThat
import assertk.assertions.isEqualTo
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import net.dankito.datetime.LocalDate
import kotlin.test.BeforeTest
import kotlin.test.Test

class LocalDateComponentSerializerTest {

    @BeforeTest
    fun setup() {
        SerializationConfig.LocalDateSerializationFormat = DateTimeSerializationFormat.Components
    }


    @Test
    fun serializeToComponentsObject() {
        val result = Json.encodeToString(LocalDate(2015, 10, 21))

        assertThat(result).isEqualTo("""{"year":2015,"month":10,"day":21}""")
    }

    @Test
    fun deserializeFromComponentsObject() {
        val result = Json.decodeFromString<LocalDate>("""{"year":2015,"month":10,"day":21}""")

        assertThat(result).isEqualTo(LocalDate(2015, 10, 21))
    }

}