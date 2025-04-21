package net.dankito.datetime.serialization

import assertk.assertThat
import assertk.assertions.isEqualTo
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import net.dankito.datetime.Instant
import kotlin.test.BeforeTest
import kotlin.test.Test

class InstantComponentSerializerTest {

    @BeforeTest
    fun setup() {
        SerializationConfig.InstantDefaultFormat = InstantSerializationFormat.Components
    }


    @Test
    fun serializeToComponentsObject() {
        val result = Json.encodeToString(Instant(1445418487, 654321))

        assertThat(result).isEqualTo("""{"epochSeconds":1445418487,"nanosecondsOfSecond":654321}""")
    }

    @Test
    fun deserializeFromComponentsObject() {
        val result = Json.decodeFromString<Instant>("""{"epochSeconds":1445418487,"nanosecondsOfSecond":654321}""")

        assertThat(result).isEqualTo(Instant(1445418487, 654321))
    }

}