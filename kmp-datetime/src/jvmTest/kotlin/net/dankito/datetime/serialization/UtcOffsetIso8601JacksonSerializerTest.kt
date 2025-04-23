package net.dankito.datetime.serialization

import assertk.assertThat
import assertk.assertions.isEqualTo
import com.fasterxml.jackson.module.kotlin.readValue
import net.dankito.datetime.UtcOffset
import org.junit.Test

@OptIn(ExperimentalMultiplatform::class)
class UtcOffsetIso8601JacksonSerializerTest {

    // as we registered our Jackson module with all our serializers and deserializers in
    // `kmp-datetime/src/jvmMain/resources/META-INF/services/com.fasterxml.jackson.databind.Module`
    // ObjectMapper.findAndRegisterModules() registers our module and therefore serializers and deserializers automatically.
    private val objectMapper = Serializers.jacksonObjectMapper


    @Test
    fun serializeToIsoOffsetString_HoursMinutesSeconds() {
        val result = objectMapper.writeValueAsString(UtcOffset(9, 8, 7))

        assertThat(result).isEqualTo("\"+09:08:07\"")
    }

    @Test
    fun serializeToIsoOffsetString_HoursMinutes() {
        val result = objectMapper.writeValueAsString(UtcOffset(9, 8, 0))

        assertThat(result).isEqualTo("\"+09:08\"")
    }

    @Test
    fun serializeToIsoOffsetString_Hours() {
        val result = objectMapper.writeValueAsString(UtcOffset(9, 0, 0))

        assertThat(result).isEqualTo("\"+09:00\"")
    }

    @Test
    fun serializeToIsoOffsetString_Zulu() {
        val result = objectMapper.writeValueAsString(UtcOffset(0, 0, 0))

        assertThat(result).isEqualTo("\"Z\"")
    }


    @Test
    fun deserializeFromIsoDateString_HoursMinutesSeconds() {
        val result = objectMapper.readValue<UtcOffset>("\"+09:08:07\"")

        assertThat(result).isEqualTo(UtcOffset(9, 8, 7))
    }

    @Test
    fun deserializeFromIsoDateString_Zulu() {
        val result = objectMapper.readValue<UtcOffset>("\"Z\"")

        assertThat(result).isEqualTo(UtcOffset.UTC)
    }

}