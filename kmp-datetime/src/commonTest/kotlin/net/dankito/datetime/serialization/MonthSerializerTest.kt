package net.dankito.datetime.serialization

import assertk.assertThat
import assertk.assertions.isEqualTo
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import net.dankito.datetime.Month
import kotlin.test.Test

class MonthSerializerTest {

    @Test
    fun serializeMonth() {
        val result = Json.encodeToString(Month.October)

        assertThat(result).isEqualTo("\"October\"")
    }

    @Test
    fun deserializeMonth() {
        val result = Json.decodeFromString<Month>("\"October\"")

        assertThat(result).isEqualTo(Month.October)
    }

}