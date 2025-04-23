package net.dankito.datetime.serialization

import assertk.assertThat
import assertk.assertions.isEqualTo
import kotlinx.serialization.encodeToString
import net.dankito.datetime.UtcOffset
import kotlin.test.Test

@OptIn(ExperimentalMultiplatform::class)
class UtcOffsetIso8601SerializerJacksonCompatibilityTest {

    private val kotlinxJson = Serializers.kotlinxJson

    private val jacksonObjectMapper = Serializers.jacksonObjectMapper


    @Test
    fun canDeserializeJavaLocalTimeSerializedWithJackson() {
        val serialized = jacksonObjectMapper.writeValueAsString(java.time.ZoneOffset.ofHoursMinutesSeconds(9, 8, 7))

        val result = kotlinxJson.decodeFromString<UtcOffset>(serialized)

        assertThat(result).isEqualTo(UtcOffset(9, 8, 7))
    }

    @Test
    fun serializedTimeCanBeDeserializedByJacksonToJavaLocalTime() {
        val serialized = kotlinxJson.encodeToString(UtcOffset(9, 8, 7))

        val result = jacksonObjectMapper.readValue(serialized, java.time.ZoneOffset::class.java)

        assertThat(result).isEqualTo(java.time.ZoneOffset.ofHoursMinutesSeconds(9, 8, 7))
    }


    @Test
    fun canDeserializeJavaLocalTimeSerializedWithJackson_Zulu() {
        val serialized = jacksonObjectMapper.writeValueAsString(java.time.ZoneOffset.UTC)

        val result = kotlinxJson.decodeFromString<UtcOffset>(serialized)

        assertThat(result).isEqualTo(UtcOffset.UTC)
    }

    @Test
    fun serializedTimeCanBeDeserializedByJacksonToJavaLocalTime_Zulu() {
        val serialized = kotlinxJson.encodeToString(UtcOffset.UTC)

        val result = jacksonObjectMapper.readValue(serialized, java.time.ZoneOffset::class.java)

        assertThat(result).isEqualTo(java.time.ZoneOffset.UTC)
    }

}