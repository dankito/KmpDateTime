package net.dankito.datetime.serialization

import assertk.assertThat
import assertk.assertions.isEqualTo
import com.fasterxml.jackson.module.kotlin.readValue
import net.dankito.datetime.LocalDateTime
import org.junit.Test
import kotlin.test.BeforeTest

class LocalDateTimeIso8601JacksonSerializerTest {

    // as we registered our Jackson module with all our serializers and deserializers in
    // `kmp-datetime/src/jvmMain/resources/META-INF/services/com.fasterxml.jackson.databind.Module`
    // ObjectMapper.findAndRegisterModules() registers our module and therefore serializers and deserializers automatically.
    private val objectMapper = Serializers.jacksonObjectMapper


    @BeforeTest
    fun setup() {
        SerializationConfig.LocalDateTimeDefaultFormat = DateTimeSerializationFormat.Iso8601
    }


    @Test
    fun serialize() {
        val result = objectMapper.writeValueAsString(LocalDateTime(2015, 10, 21, 9, 8, 7, 654))

        assertThat(result).isEqualTo("\"2015-10-21T09:08:07.000000654\"")
    }

    @Test
    fun deserialize() {
        val result = objectMapper.readValue<LocalDateTime>("\"2015-10-21T09:08:07.654\"")

        assertThat(result).isEqualTo(LocalDateTime(2015, 10, 21, 9, 8, 7, 654_000_000))
    }

}