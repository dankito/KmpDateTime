package net.dankito.kotlin.datetime.serialization

import assertk.assertThat
import assertk.assertions.isEqualTo
import com.fasterxml.jackson.module.kotlin.readValue
import net.dankito.kotlin.datetime.LocalDate
import org.junit.Test

class JacksonLocalDateIso8601SerializerTest {

    // as we registered our Jackson module with all our serializers and deserializers in
    // `kmp-date-time/src/jvmMain/resources/META-INF/services/com.fasterxml.jackson.databind.Module`
    // ObjectMapper.findAndRegisterModules() registers our module and therefore serializers and deserializers automatically.
    private val objectMapper = Serializers.jacksonObjectMapper


    @Test
    fun serialize() {
        val result = objectMapper.writeValueAsString(LocalDate(2015, 10, 21))

        assertThat(result).isEqualTo("\"2015-10-21\"")
    }

    @Test
    fun deserialize() {
        val result = objectMapper.readValue<LocalDate>("\"2015-10-21\"")

        assertThat(result).isEqualTo(LocalDate(2015, 10, 21))
    }

}