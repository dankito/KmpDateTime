package net.dankito.datetime.serialization

import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.deser.std.StdDeserializer
import net.dankito.datetime.LocalDateTime

open class JacksonLocalDateTimeDelegatingDeserializer : StdDeserializer<LocalDateTime>(LocalDateTime::class.java) {

    override fun deserialize(parser: JsonParser, context: DeserializationContext): LocalDateTime =
        getDeserializer().deserialize(parser, context)


    private fun getDeserializer(): StdDeserializer<LocalDateTime> =when (SerializationConfig.LocalDateTimeDefaultFormat) {
        DateTimeSerializationFormat.Custom -> SerializationConfig.LocalDateTimeCustomJacksonDeserializer
        else -> JacksonLocalDateTimeIso8601Deserializer.Instance
    }

}