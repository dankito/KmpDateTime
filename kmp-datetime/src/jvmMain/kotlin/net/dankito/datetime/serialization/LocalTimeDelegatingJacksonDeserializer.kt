package net.dankito.datetime.serialization

import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.deser.std.StdDeserializer
import net.dankito.datetime.LocalTime

open class LocalTimeDelegatingJacksonDeserializer : StdDeserializer<LocalTime>(LocalTime::class.java) {

    override fun deserialize(parser: JsonParser, context: DeserializationContext): LocalTime =
        getDeserializer().deserialize(parser, context)


    private fun getDeserializer(): StdDeserializer<LocalTime> = when (SerializationConfig.LocalTimeDefaultFormat) {
        DateTimeSerializationFormat.Iso8601 -> LocalTimeIso8601JacksonDeserializer.Instance
        DateTimeSerializationFormat.Components -> LocalTimeComponentJacksonDeserializer.Instance
        DateTimeSerializationFormat.Custom -> SerializationConfig.LocalTimeCustomJacksonDeserializer
    }

}