package net.dankito.datetime.serialization

import assertk.assertThat
import assertk.assertions.isEqualTo
import com.fasterxml.jackson.module.kotlin.readValue
import net.dankito.datetime.OffsetDateTime
import net.dankito.datetime.UtcOffset
import org.junit.Test

@OptIn(ExperimentalMultiplatform::class)
class OffsetDateTimeIso8601JacksonSerializerTest {

    // as we registered our Jackson module with all our serializers and deserializers in
    // `kmp-datetime/src/jvmMain/resources/META-INF/services/com.fasterxml.jackson.databind.Module`
    // ObjectMapper.findAndRegisterModules() registers our module and therefore serializers and deserializers automatically.
    private val objectMapper = Serializers.jacksonObjectMapper


    @Test
    fun serialize() {
        val result = objectMapper.writeValueAsString(OffsetDateTime(2015, 10, 21, 9, 8, 7, 654, UtcOffset(-14, 20)))

        assertThat(result).isEqualTo("\"2015-10-21T09:08:07.000000654-14:20\"")
    }

    @Test
    fun deserialize() {
        val result = objectMapper.readValue<OffsetDateTime>("\"2015-10-21T09:08:07.654-14:20\"")

        assertThat(result).isEqualTo(OffsetDateTime(2015, 10, 21, 9, 8, 7, 654_000_000, UtcOffset(-14, 20)))
    }

}