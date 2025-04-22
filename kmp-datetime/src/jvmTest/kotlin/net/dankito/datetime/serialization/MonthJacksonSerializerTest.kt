package net.dankito.datetime.serialization

import assertk.assertThat
import assertk.assertions.isEqualTo
import com.fasterxml.jackson.module.kotlin.readValue
import net.dankito.datetime.Month
import org.junit.Test

class MonthJacksonSerializerTest {

    private val objectMapper = Serializers.jacksonObjectMapper


    @Test
    fun serialize() {
        val result = objectMapper.writeValueAsString(Month.October)

        assertThat(result).isEqualTo("\"October\"")
    }

    @Test
    fun deserialize() {
        val result = objectMapper.readValue<Month>("\"October\"")

        assertThat(result).isEqualTo(Month.October)
    }

}