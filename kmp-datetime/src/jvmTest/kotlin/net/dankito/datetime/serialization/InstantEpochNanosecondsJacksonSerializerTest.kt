package net.dankito.datetime.serialization

import assertk.assertThat
import assertk.assertions.isEqualTo
import com.fasterxml.jackson.module.kotlin.readValue
import net.dankito.datetime.Instant
import org.junit.Test
import kotlin.test.BeforeTest

class InstantEpochNanosecondsJacksonSerializerTest {

    companion object {
        private val TestInstant = Instant(1445418487, 987_654_321)

        private val ExpectedJson = "1445418487987654321"
    }


    // as we registered our Jackson module with all our serializers and deserializers in
    // `kmp-datetime/src/jvmMain/resources/META-INF/services/com.fasterxml.jackson.databind.Module`
    // ObjectMapper.findAndRegisterModules() registers our module and therefore serializers and deserializers automatically.
    private val objectMapper = Serializers.jacksonObjectMapper


    @BeforeTest
    fun setup() {
        SerializationConfig.InstantDefaultFormat = InstantSerializationFormat.EpochNanoseconds
    }


    @Test
    fun serialize() {
        val result = objectMapper.writeValueAsString(TestInstant)

        assertThat(result).isEqualTo(ExpectedJson)
    }

    @Test
    fun deserialize() {
        val result = objectMapper.readValue<Instant>(ExpectedJson)

        assertThat(result).isEqualTo(TestInstant)
    }

}