package net.dankito.datetime.serialization

import assertk.assertThat
import assertk.assertions.isEqualTo
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.SerializationFeature
import kotlinx.serialization.encodeToString
import net.dankito.datetime.Instant
import kotlin.test.BeforeTest
import kotlin.test.Test

class InstantEpochMillisecondsSerializerJacksonCompatibilityTest {

    private val kotlinxJson = Serializers.kotlinxJson

    private val jacksonObjectMapper = Serializers.jacksonObjectMapper.copy().apply {
        enable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS) // write timestamps as Long instead of (ISO) String
        disable(SerializationFeature.WRITE_DATE_TIMESTAMPS_AS_NANOSECONDS) // for millis since epoch disable nanoseconds resolution (enabled by default)

        disable(DeserializationFeature.READ_DATE_TIMESTAMPS_AS_NANOSECONDS) // for millis since epoch disable nanoseconds resolution (enabled by default)
    }


    @BeforeTest
    fun setup() {
        SerializationConfig.InstantDefaultFormat = InstantSerializationFormat.EpochMilliseconds
    }


    @Test
    fun canDeserializeJavaLocalDateSerializedWithJackson() {
        val serialized = jacksonObjectMapper.writeValueAsString(java.time.Instant.ofEpochSecond(1_739_783_287, 654_321_098))

        val result = kotlinxJson.decodeFromString<Instant>(serialized)

        assertThat(result).isEqualTo(Instant(1_739_783_287, 654_000_000))
    }

    @Test
    fun serializedDateCanBeDeserializedByJacksonToJavaLocalDate() {
        val serialized = kotlinxJson.encodeToString(Instant(1_739_783_287, 654_321_098))

        val result = jacksonObjectMapper.readValue(serialized, java.time.Instant::class.java)

        assertThat(result).isEqualTo(java.time.Instant.ofEpochSecond(1_739_783_287, 654_000_000))
    }

}