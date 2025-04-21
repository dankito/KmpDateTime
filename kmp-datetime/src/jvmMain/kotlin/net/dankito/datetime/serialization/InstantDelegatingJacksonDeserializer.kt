package net.dankito.datetime.serialization

import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.deser.std.StdDeserializer
import net.dankito.datetime.Instant

open class InstantDelegatingJacksonDeserializer : StdDeserializer<Instant>(Instant::class.java) {

    override fun deserialize(parser: JsonParser, context: DeserializationContext): Instant =
        getDeserializer().deserialize(parser, context)


    private fun getDeserializer(): StdDeserializer<Instant> =when (SerializationConfig.InstantDefaultFormat) {
        InstantSerializationFormat.EpochMilliseconds -> InstantEpochMillisecondsJacksonDeserializer.Instance
        InstantSerializationFormat.Custom -> SerializationConfig.InstantCustomJacksonDeserializer
        else -> InstantIso8601JacksonDeserializer.Instance
    }

}