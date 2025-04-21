package net.dankito.datetime.serialization

import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.deser.std.StdDeserializer
import net.dankito.datetime.LocalTime

open class JacksonLocalTimeDelegatingDeserializer : StdDeserializer<LocalTime>(LocalTime::class.java) {

    override fun deserialize(parser: JsonParser, context: DeserializationContext): LocalTime =
        getDeserializer().deserialize(parser, context)


    private fun getDeserializer(): StdDeserializer<LocalTime> =when (SerializationConfig.LocalTimeDefaultFormat) {
        DateTimeSerializationFormat.Custom -> SerializationConfig.LocalTimeCustomJacksonDeserializer
        else -> JacksonLocalTimeIso8601Deserializer.Instance
    }

}