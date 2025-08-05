package net.dankito.datetime.serialization

import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.databind.SerializerProvider
import com.fasterxml.jackson.databind.ser.std.StdSerializer
import net.dankito.datetime.Instant

open class InstantDelegatingJacksonSerializer : StdSerializer<Instant>(Instant::class.java) {

    companion object {
        val Instance = InstantDelegatingJacksonSerializer()
    }


    override fun serialize(value: Instant, generator: JsonGenerator, provider: SerializerProvider) {
        getSerializer().serialize(value, generator, provider)
    }


    private fun getSerializer(): StdSerializer<Instant> = when (SerializationConfig.InstantDefaultFormat) {
        InstantSerializationFormat.Iso8601 -> InstantIso8601JacksonSerializer.Instance
        InstantSerializationFormat.EpochMilliseconds -> InstantEpochMillisecondsJacksonSerializer.Instance
        InstantSerializationFormat.EpochSecondsAsDouble -> InstantEpochSecondsAsDoubleJacksonSerializer.Instance
        InstantSerializationFormat.Components -> InstantComponentJacksonSerializer.Instance
        InstantSerializationFormat.Custom -> SerializationConfig.InstantCustomJacksonSerializer
    }

}