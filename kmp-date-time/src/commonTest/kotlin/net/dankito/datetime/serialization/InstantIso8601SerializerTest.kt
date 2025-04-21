package net.dankito.datetime.serialization

import assertk.assertThat
import assertk.assertions.isEqualTo
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import net.dankito.datetime.Instant
import net.dankito.datetime.LocalDateTime
import kotlin.test.Test

class InstantIso8601SerializerTest {

    // i left away the nanoseconds part as not all systems support it

    @Test
    fun serializeToIsoDateString() {
        val result = Json.encodeToString(LocalDateTime(2015, 10, 21, 9, 8, 7).toInstantAtUtc())

        assertThat(result).isEqualTo("\"2015-10-21T09:08:07Z\"")
    }

    @Test
    fun deserializeFromIsoDateString() {
        val result = Json.decodeFromString<Instant>("\"2015-10-21T09:08:07Z\"")

        assertThat(result).isEqualTo(LocalDateTime(2015, 10, 21, 9, 8, 7).toInstantAtUtc())
    }

}