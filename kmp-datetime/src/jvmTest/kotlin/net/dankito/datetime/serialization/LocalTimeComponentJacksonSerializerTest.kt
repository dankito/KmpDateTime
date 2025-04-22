package net.dankito.datetime.serialization

import assertk.assertThat
import assertk.assertions.isEqualTo
import com.fasterxml.jackson.module.kotlin.readValue
import net.dankito.datetime.LocalTime
import org.junit.Test
import kotlin.test.BeforeTest

class LocalTimeComponentJacksonSerializerTest {

    // as we registered our Jackson module with all our serializers and deserializers in
    // `kmp-datetime/src/jvmMain/resources/META-INF/services/com.fasterxml.jackson.databind.Module`
    // ObjectMapper.findAndRegisterModules() registers our module and therefore serializers and deserializers automatically.
    private val objectMapper = Serializers.jacksonObjectMapper


    @BeforeTest
    fun setup() {
        SerializationConfig.LocalTimeDefaultFormat = DateTimeSerializationFormat.Components
    }


    @Test
    fun serialize() {
        val result = objectMapper.writeValueAsString(LocalTime(9, 8, 7, 654))

        assertThat(result).isEqualTo("""{"hour":9,"minute":8,"second":7,"nanosecond":654}""")
    }

    @Test
    fun deserialize() {
        val result = objectMapper.readValue<LocalTime>("""{"hour":9,"minute":8,"second":7,"nanosecond":654}""")

        assertThat(result).isEqualTo(LocalTime(9, 8, 7, 654))
    }

}