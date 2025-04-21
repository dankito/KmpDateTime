package net.dankito.datetime.serialization

import assertk.assertThat
import assertk.assertions.isEqualTo
import kotlinx.serialization.encodeToString
import net.dankito.datetime.LocalDateTime
import kotlin.test.BeforeTest
import kotlin.test.Test

class LocalDateTimeIso8601SerializerJacksonCompatibilityTest {

    private val kotlinxJson = Serializers.kotlinxJson

    private val jacksonObjectMapper = Serializers.jacksonObjectMapper


    @BeforeTest
    fun setup() {
        SerializationConfig.InstantDefaultFormat = InstantSerializationFormat.Iso8601
    }


    @Test
    fun canDeserializeJavaLocalDateSerializedWithJackson() {
        val serialized = jacksonObjectMapper.writeValueAsString(java.time.LocalDateTime.of(2015, 10, 21, 9, 8, 7, 654))

        val result = kotlinxJson.decodeFromString<LocalDateTime>(serialized)

        assertThat(result).isEqualTo(LocalDateTime(2015, 10, 21, 9, 8, 7, 654))
    }

    @Test
    fun serializedDateCanBeDeserializedByJacksonToJavaLocalDate() {
        val serialized = kotlinxJson.encodeToString(LocalDateTime(2015, 10, 21, 9, 8, 7, 654))

        val result = jacksonObjectMapper.readValue(serialized, java.time.LocalDateTime::class.java)

        assertThat(result).isEqualTo(java.time.LocalDateTime.of(2015, 10, 21, 9, 8, 7, 654))
    }

}