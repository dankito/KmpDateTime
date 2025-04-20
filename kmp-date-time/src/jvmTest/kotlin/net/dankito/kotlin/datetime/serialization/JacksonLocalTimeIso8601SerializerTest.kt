package net.dankito.kotlin.datetime.serialization

import assertk.assertThat
import assertk.assertions.isEqualTo
import com.fasterxml.jackson.module.kotlin.readValue
import net.dankito.kotlin.datetime.LocalTime
import org.junit.Test

class JacksonLocalTimeIso8601SerializerTest {

    // as we registered our Jackson module with all our serializers and deserializers in
    // `kmp-date-time/src/jvmMain/resources/META-INF/services/com.fasterxml.jackson.databind.Module`
    // ObjectMapper.findAndRegisterModules() registers our module and therefore serializers and deserializers automatically.
    private val objectMapper = Serializers.jacksonObjectMapper


    @Test
    fun serialize() {
        val result = objectMapper.writeValueAsString(LocalTime(9, 8, 7, 654))

        assertThat(result).isEqualTo("\"09:08:07.000000654\"")
    }

    @Test
    fun deserialize() {
        val result = objectMapper.readValue<LocalTime>("\"09:08:07.654\"")

        assertThat(result).isEqualTo(LocalTime(9, 8, 7, 654_000_000))
    }

}