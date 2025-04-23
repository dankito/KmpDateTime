package net.dankito.datetime.serialization

import assertk.assertThat
import assertk.assertions.isEqualTo
import kotlinx.serialization.encodeToString
import net.dankito.datetime.OffsetDateTime
import net.dankito.datetime.UtcOffset
import java.time.ZoneOffset
import kotlin.test.Test

@OptIn(ExperimentalMultiplatform::class)
class OffsetDateTimeIso8601SerializerJacksonCompatibilityTest {

    private val kotlinxJson = Serializers.kotlinxJson

    private val jacksonObjectMapper = Serializers.jacksonObjectMapper


    @Test
    fun canDeserializeJavaLocalDateSerializedWithJackson() {
        val serialized = jacksonObjectMapper.writeValueAsString(java.time.OffsetDateTime.of(2015, 10, 21, 9, 8, 7, 654, ZoneOffset.ofHours(-6)))

        val result = kotlinxJson.decodeFromString<OffsetDateTime>(serialized)

        assertThat(result).isEqualTo(OffsetDateTime(2015, 10, 21, 9, 8, 7, 654, UtcOffset(hours = -6)))
    }

    @Test
    fun serializedDateCanBeDeserializedByJacksonToJavaLocalDate() {
        val serialized = kotlinxJson.encodeToString(OffsetDateTime(2015, 10, 21, 9, 8, 7, 654, UtcOffset(hours = -6)))

        val result = jacksonObjectMapper.readValue(serialized, java.time.OffsetDateTime::class.java)

        // by default Jackson transfers the returned object to UTC, in this case
        // java.time.OffsetDateTime.of(2015, 10, 21, 15, 8, 7, 654, ZoneOffset.ofHours(0)),
        // which we disabled with disable(DeserializationFeature.ADJUST_DATES_TO_CONTEXT_TIME_ZONE)
        assertThat(result).isEqualTo(java.time.OffsetDateTime.of(2015, 10, 21, 9, 8, 7, 654, ZoneOffset.ofHours(-6)))
    }

}