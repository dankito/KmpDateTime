package net.dankito.datetime.serialization

import assertk.assertThat
import assertk.assertions.isEqualTo
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import net.dankito.datetime.LocalDateTime
import kotlin.test.BeforeTest
import kotlin.test.Test

class LocalDateTimeComponentSerializerTest {

    @BeforeTest
    fun setup() {
        SerializationConfig.LocalDateTimeSerializationFormat = DateTimeSerializationFormat.Components
    }


    @Test
    fun serializeToComponentsObject() {
        val result = Json.encodeToString(LocalDateTime(2015, 10, 21, 9, 8, 7, 654))

        assertThat(result).isEqualTo("""{"year":2015,"month":10,"day":21,"hour":9,"minute":8,"second":7,"nanosecond":654}""")
    }

    @Test
    fun deserializeFromComponentsObject() {
        val result = Json.decodeFromString<LocalDateTime>("""{"year":2015,"month":10,"day":21,"hour":9,"minute":8,"second":7,"nanosecond":654}""")

        assertThat(result).isEqualTo(LocalDateTime(2015, 10, 21, 9, 8, 7, 654))
    }

}