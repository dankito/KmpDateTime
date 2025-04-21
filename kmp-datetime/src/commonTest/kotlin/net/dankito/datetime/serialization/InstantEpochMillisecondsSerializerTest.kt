package net.dankito.datetime.serialization

import assertk.assertThat
import assertk.assertions.isEqualTo
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import net.dankito.datetime.Instant
import kotlin.test.BeforeTest
import kotlin.test.Test

class InstantEpochMillisecondsSerializerTest {

    @BeforeTest
    fun setup() {
        SerializationConfig.InstantDefaultFormat = InstantSerializationFormat.EpochMilliseconds
    }


    @Test
    fun serializeToComponentsObject() {
        val result = Json.encodeToString(Instant(1445418487, 654_321_000))

        assertThat(result).isEqualTo("1445418487654")
    }

    @Test
    fun deserializeFromComponentsObject() {
        val result = Json.decodeFromString<Instant>("1445418487654")

        assertThat(result).isEqualTo(Instant(1445418487, 654_000_000))
    }

}