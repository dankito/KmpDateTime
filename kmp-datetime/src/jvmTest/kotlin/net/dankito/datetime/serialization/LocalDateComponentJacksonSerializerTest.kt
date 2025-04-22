package net.dankito.datetime.serialization

import assertk.assertThat
import assertk.assertions.isEqualTo
import com.fasterxml.jackson.module.kotlin.readValue
import net.dankito.datetime.LocalDate
import org.junit.Test
import kotlin.test.BeforeTest

class LocalDateComponentJacksonSerializerTest {

    // as we registered our Jackson module with all our serializers and deserializers in
    // `kmp-datetime/src/jvmMain/resources/META-INF/services/com.fasterxml.jackson.databind.Module`
    // ObjectMapper.findAndRegisterModules() registers our module and therefore serializers and deserializers automatically.
    private val objectMapper = Serializers.jacksonObjectMapper


    @BeforeTest
    fun setup() {
        SerializationConfig.LocalDateDefaultFormat = DateTimeSerializationFormat.Components
    }


    @Test
    fun serialize() {
        val result = objectMapper.writeValueAsString(LocalDate(2015, 10, 21))

        assertThat(result).isEqualTo("""{"year":2015,"month":10,"day":21}""")
    }

    @Test
    fun deserialize() {
        val result = objectMapper.readValue<LocalDate>("""{"year":2015,"month":10,"day":21}""")

        assertThat(result).isEqualTo(LocalDate(2015, 10, 21))
    }

}