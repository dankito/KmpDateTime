package net.dankito.datetime.serialization

import assertk.assertThat
import assertk.assertions.isEqualTo
import com.fasterxml.jackson.module.kotlin.readValue
import net.dankito.datetime.DayOfWeek
import org.junit.Test

class DayOfWeekJacksonSerializerTest {

    private val objectMapper = Serializers.jacksonObjectMapper


    @Test
    fun serialize() {
        val result = objectMapper.writeValueAsString(DayOfWeek.Wednesday)

        assertThat(result).isEqualTo("\"Wednesday\"")
    }

    @Test
    fun deserialize() {
        val result = objectMapper.readValue<DayOfWeek>("\"Wednesday\"")

        assertThat(result).isEqualTo(DayOfWeek.Wednesday)
    }

}