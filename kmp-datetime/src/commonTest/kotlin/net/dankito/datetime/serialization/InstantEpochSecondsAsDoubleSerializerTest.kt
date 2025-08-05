package net.dankito.datetime.serialization

import assertk.assertThat
import assertk.assertions.isEqualTo
import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import net.dankito.datetime.Instant
import kotlin.test.BeforeTest
import kotlin.test.Test

class InstantEpochSecondsAsDoubleSerializerTest {

    companion object {
        private const val ExpectedJson = """{"timestamp":1445418487.654321}"""
    }


    @BeforeTest
    fun setup() {
        SerializationConfig.InstantDefaultFormat = InstantSerializationFormat.EpochSecondsAsDouble
    }


    @Test
    fun serializeToComponentsObject() {
        val result = Json.encodeToString(TestObject(Instant(1445418487, 654_321_000)))

        assertThat(result).isEqualTo(ExpectedJson)
    }

    @Test
    fun deserializeFromComponentsObject() {
        val result = Json.decodeFromString<TestObject>(ExpectedJson)

        assertThat(result.timestamp).isEqualTo(Instant(1445418487, 654_321_000))
    }


    @Serializable
    data class TestObject(val timestamp: Instant)

}