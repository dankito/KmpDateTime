package net.dankito.datetime.serialization

import assertk.assertThat
import assertk.assertions.isEqualTo
import com.fasterxml.jackson.module.kotlin.readValue
import net.dankito.datetime.LocalDateTime
import org.junit.Test
import kotlin.test.BeforeTest

class LocalDateTimeComponentJacksonSerializerTest {

    // as we registered our Jackson module with all our serializers and deserializers in
    // `kmp-datetime/src/jvmMain/resources/META-INF/services/com.fasterxml.jackson.databind.Module`
    // ObjectMapper.findAndRegisterModules() registers our module and therefore serializers and deserializers automatically.
    private val objectMapper = Serializers.jacksonObjectMapper


    @BeforeTest
    fun setup() {
        SerializationConfig.LocalDateTimeDefaultFormat = DateTimeSerializationFormat.Components
    }


    @Test
    fun serialize() {
        val result = objectMapper.writeValueAsString(LocalDateTime(2015, 10, 21, 9, 8, 7, 654))

        assertThat(result).isEqualTo("""{"year":2015,"month":10,"day":21,"hour":9,"minute":8,"second":7,"nanosecond":654}""")
    }

    @Test
    fun deserialize() {
        val result = objectMapper.readValue<LocalDateTime>("""{"year":2015,"month":10,"day":21,"hour":9,"minute":8,"second":7,"nanosecond":654}""")

        assertThat(result).isEqualTo(LocalDateTime(2015, 10, 21, 9, 8, 7, 654))
    }

}