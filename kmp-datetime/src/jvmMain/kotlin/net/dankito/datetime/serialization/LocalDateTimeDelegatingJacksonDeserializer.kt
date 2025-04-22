package net.dankito.datetime.serialization

import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.deser.std.StdDeserializer
import net.dankito.datetime.LocalDateTime

open class LocalDateTimeDelegatingJacksonDeserializer : StdDeserializer<LocalDateTime>(LocalDateTime::class.java) {

    companion object {
        val Instance = LocalDateTimeDelegatingJacksonDeserializer()
    }


    override fun deserialize(parser: JsonParser, context: DeserializationContext): LocalDateTime =
        getDeserializer().deserialize(parser, context)


    private fun getDeserializer(): StdDeserializer<LocalDateTime> = when (SerializationConfig.LocalDateTimeDefaultFormat) {
        DateTimeSerializationFormat.Iso8601 -> LocalDateTimeIso8601JacksonDeserializer.Instance
        DateTimeSerializationFormat.Components -> LocalDateTimeComponentJacksonDeserializer.Instance
        DateTimeSerializationFormat.Custom -> SerializationConfig.LocalDateTimeCustomJacksonDeserializer
    }

}