package net.dankito.datetime.serialization

import assertk.assertThat
import assertk.assertions.isNotNull
import assertk.assertions.isNull
import com.fasterxml.jackson.databind.JsonMappingException
import com.fasterxml.jackson.module.kotlin.readValue
import kotlinx.serialization.Serializable
import net.dankito.datetime.LocalDate
import kotlin.test.Test
import kotlin.test.assertFailsWith

class NullableSerializationTest {

    @Serializable
    data class LocalDateContainer(val date: LocalDate? = null)


    private val kotlinxJson = Serializers.kotlinxJson

    private val jacksonObjectMapper = Serializers.jacksonObjectMapper


    @Test
    fun nullableLocalDate_PropertyIsSetToNull_kotlinx() {
        val result = kotlinxJson.decodeFromString<LocalDateContainer>("""{"date": null}""")

        assertThat(result).isNotNull()
        assertThat(result.date).isNull()
    }

    @Test
    fun nullableLocalDate_WithoutProperty_kotlinx() {
        val result = kotlinxJson.decodeFromString<LocalDateContainer>("""{ }""")

        assertThat(result).isNotNull()
        assertThat(result.date).isNull()
    }

    @Test
    fun nullableLocalDate_WrongDateFormat_kotlinx() {
        // TODO: is there any way to check if property is nullable and if so make deserialization lenient? Should
        //  return null instead of failing
        assertFailsWith(IllegalArgumentException::class) {
            val result = kotlinxJson.decodeFromString<LocalDateContainer>("""{"date": "21.10.2015"}""")

            assertThat(result).isNotNull()
            assertThat(result.date).isNull()
        }
    }


    @Test
    fun nullableLocalDate_PropertyIsSetToNull_Jackson() {
        val result = jacksonObjectMapper.readValue<LocalDateContainer>("""{"date": null}""")

        assertThat(result).isNotNull()
        assertThat(result.date).isNull()
    }

    @Test
    fun nullableLocalDate_WithoutProperty_Jackson() {
        val result = jacksonObjectMapper.readValue<LocalDateContainer>("""{ }""")

        assertThat(result).isNotNull()
        assertThat(result.date).isNull()
    }

    @Test
    fun nullableLocalDate_WrongDateFormat_Jackson() {
        // TODO: is there any way to check if property is nullable and if so make deserialization lenient? Should
        //  return null instead of failing
        assertFailsWith(JsonMappingException::class) {
            val result = jacksonObjectMapper.readValue<LocalDateContainer>("""{"date": "21.10.2015"}""")

            assertThat(result).isNotNull()
            assertThat(result.date).isNull()
        }
    }

}