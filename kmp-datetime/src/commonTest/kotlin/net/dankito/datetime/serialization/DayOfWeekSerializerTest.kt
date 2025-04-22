package net.dankito.datetime.serialization

import assertk.assertThat
import assertk.assertions.isEqualTo
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import net.dankito.datetime.DayOfWeek
import kotlin.test.Test

class DayOfWeekSerializerTest {

    @Test
    fun serializeDayOfWeek() {
        val result = Json.encodeToString(DayOfWeek.Wednesday)

        assertThat(result).isEqualTo("\"Wednesday\"")
    }

    @Test
    fun deserializeDayOfWeek() {
        val result = Json.decodeFromString<DayOfWeek>("\"Wednesday\"")

        assertThat(result).isEqualTo(DayOfWeek.Wednesday)
    }

}