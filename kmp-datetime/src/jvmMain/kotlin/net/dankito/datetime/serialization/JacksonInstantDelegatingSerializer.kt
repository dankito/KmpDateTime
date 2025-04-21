package net.dankito.datetime.serialization

import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.databind.SerializerProvider
import com.fasterxml.jackson.databind.ser.std.StdSerializer
import net.dankito.datetime.Instant

open class JacksonInstantDelegatingSerializer : StdSerializer<Instant>(Instant::class.java) {

    override fun serialize(value: Instant, generator: JsonGenerator, provider: SerializerProvider) {
        getSerializer().serialize(value, generator, provider)
    }


    private fun getSerializer(): StdSerializer<Instant> = when (SerializationConfig.InstantDefaultFormat) {
        InstantSerializationFormat.Custom -> SerializationConfig.InstantCustomJacksonSerializer
        else -> JacksonInstantIso8601Serializer.Instance
    }

}