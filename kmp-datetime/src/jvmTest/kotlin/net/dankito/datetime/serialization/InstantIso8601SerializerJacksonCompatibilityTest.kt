package net.dankito.datetime.serialization

import assertk.assertThat
import assertk.assertions.isEqualTo
import kotlinx.serialization.encodeToString
import net.dankito.datetime.Instant
import kotlin.test.BeforeTest
import kotlin.test.Test

class InstantIso8601SerializerJacksonCompatibilityTest {

    private val kotlinxJson = Serializers.kotlinxJson

    private val jacksonObjectMapper = Serializers.jacksonObjectMapper


    @BeforeTest
    fun setup() {
        SerializationConfig.InstantDefaultFormat = InstantSerializationFormat.Iso8601
    }


    @Test
    fun canDeserializeJavaLocalDateSerializedWithJackson() {
        val serialized = jacksonObjectMapper.writeValueAsString(java.time.Instant.ofEpochSecond(1_739_783_287, 654_321_098))

        val result = kotlinxJson.decodeFromString<Instant>(serialized)

        assertThat(result).isEqualTo(Instant(1_739_783_287, 654_321_098))
    }

    @Test
    fun serializedDateCanBeDeserializedByJacksonToJavaLocalDate() {
        val serialized = kotlinxJson.encodeToString(Instant(1_739_783_287, 654_321_098))

        val result = jacksonObjectMapper.readValue(serialized, java.time.Instant::class.java)

        assertThat(result).isEqualTo(java.time.Instant.ofEpochSecond(1_739_783_287, 654_321_098))
    }

}