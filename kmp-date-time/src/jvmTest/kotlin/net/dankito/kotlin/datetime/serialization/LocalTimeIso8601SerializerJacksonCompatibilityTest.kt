package net.dankito.kotlin.datetime.serialization

import assertk.assertThat
import assertk.assertions.isEqualTo
import kotlinx.serialization.encodeToString
import net.dankito.kotlin.datetime.LocalTime
import kotlin.test.Test

class LocalTimeIso8601SerializerJacksonCompatibilityTest {

    private val kotlinxJson = Serializers.kotlinxJson

    private val jacksonObjectMapper = Serializers.jacksonObjectMapper


    @Test
    fun canDeserializeJavaLocalTimeSerializedWithJackson() {
        val serialized = jacksonObjectMapper.writeValueAsString(java.time.LocalTime.of(9, 8, 7, 654))

        val result = kotlinxJson.decodeFromString<LocalTime>(serialized)

        assertThat(result).isEqualTo(LocalTime(9, 8, 7, 654))
    }

    @Test
    fun serializedTimeCanBeDeserializedByJacksonToJavaLocalTime() {
        val serialized = kotlinxJson.encodeToString(LocalTime(9, 8, 7, 654))

        val result = jacksonObjectMapper.readValue(serialized, java.time.LocalTime::class.java)

        assertThat(result).isEqualTo(java.time.LocalTime.of(9, 8, 7, 654))
    }

}