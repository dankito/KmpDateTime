package net.dankito.datetime.serialization

import assertk.assertThat
import assertk.assertions.isEqualTo
import kotlinx.serialization.encodeToString
import net.dankito.datetime.LocalDate
import kotlin.test.Test

class LocalDateIso8601SerializerJacksonCompatibilityTest {

    private val kotlinxJson = Serializers.kotlinxJson

    private val jacksonObjectMapper = Serializers.jacksonObjectMapper


    @Test
    fun canDeserializeJavaLocalDateSerializedWithJackson() {
        val serialized = jacksonObjectMapper.writeValueAsString(java.time.LocalDate.of(2015, 10, 21))

        val result = kotlinxJson.decodeFromString<LocalDate>(serialized)

        assertThat(result).isEqualTo(LocalDate(2015, 10, 21))
    }

    @Test
    fun serializedDateCanBeDeserializedByJacksonToJavaLocalDate() {
        val serialized = kotlinxJson.encodeToString(LocalDate(2015, 10, 21))

        val result = jacksonObjectMapper.readValue(serialized, java.time.LocalDate::class.java)

        assertThat(result).isEqualTo(java.time.LocalDate.of(2015, 10, 21))
    }

}