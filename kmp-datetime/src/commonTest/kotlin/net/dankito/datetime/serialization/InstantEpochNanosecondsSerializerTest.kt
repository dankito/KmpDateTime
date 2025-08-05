package net.dankito.datetime.serialization

import assertk.assertThat
import assertk.assertions.isEqualTo
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import net.dankito.datetime.Instant
import kotlin.test.BeforeTest
import kotlin.test.Test

class InstantEpochNanosecondsSerializerTest {

    companion object {
        private val TestInstant = Instant(1445418487, 987_654_321)

        private val ExpectedJson = "1445418487987654321"
    }


    @BeforeTest
    fun setup() {
        SerializationConfig.InstantDefaultFormat = InstantSerializationFormat.EpochNanoseconds
    }


    @Test
    fun serializeToComponentsObject() {
        val result = Json.encodeToString(TestInstant)

        assertThat(result).isEqualTo(ExpectedJson)
    }

    @Test
    fun deserializeFromComponentsObject() {
        val result = Json.decodeFromString<Instant>(ExpectedJson)

        assertThat(result).isEqualTo(TestInstant)
    }

}