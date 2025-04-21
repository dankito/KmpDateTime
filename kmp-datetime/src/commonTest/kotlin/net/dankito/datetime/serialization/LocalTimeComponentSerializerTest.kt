package net.dankito.datetime.serialization

import assertk.assertThat
import assertk.assertions.isEqualTo
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import net.dankito.datetime.LocalTime
import kotlin.test.BeforeTest
import kotlin.test.Test

class LocalTimeComponentSerializerTest {

    @BeforeTest
    fun setup() {
        SerializationConfig.LocalTimeDefaultFormat = DateTimeSerializationFormat.Components
    }


    @Test
    fun serializeToComponentsObject() {
        val result = Json.encodeToString(LocalTime(9, 8, 7, 654))

        assertThat(result).isEqualTo("""{"hour":9,"minute":8,"second":7,"nanosecond":654}""")
    }

    @Test
    fun deserializeFromComponentsObject() {
        val result = Json.decodeFromString<LocalTime>("""{"hour":9,"minute":8,"second":7,"nanosecond":654}""")

        assertThat(result).isEqualTo(LocalTime(9, 8, 7, 654))
    }

}