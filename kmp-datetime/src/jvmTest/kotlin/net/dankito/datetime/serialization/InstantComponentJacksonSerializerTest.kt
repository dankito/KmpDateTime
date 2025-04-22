package net.dankito.datetime.serialization

import assertk.assertThat
import assertk.assertions.isEqualTo
import com.fasterxml.jackson.module.kotlin.readValue
import net.dankito.datetime.Instant
import org.junit.Test
import kotlin.test.BeforeTest

class InstantComponentJacksonSerializerTest {

    // as we registered our Jackson module with all our serializers and deserializers in
    // `kmp-datetime/src/jvmMain/resources/META-INF/services/com.fasterxml.jackson.databind.Module`
    // ObjectMapper.findAndRegisterModules() registers our module and therefore serializers and deserializers automatically.
    private val objectMapper = Serializers.jacksonObjectMapper


    @BeforeTest
    fun setup() {
        SerializationConfig.InstantDefaultFormat = InstantSerializationFormat.Components
    }


    @Test
    fun serialize() {
        val result = objectMapper.writeValueAsString(Instant(1_739_783_287, 654_321_098))

        assertThat(result).isEqualTo("""{"epochSeconds":1739783287,"nanosecondsOfSecond":654321098}""")
    }

    @Test
    fun deserialize() {
        val result = objectMapper.readValue<Instant>("""{"epochSeconds":1739783287,"nanosecondsOfSecond":654321098}""")

        assertThat(result).isEqualTo(Instant(1_739_783_287, 654_321_098))
    }

}