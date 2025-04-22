package net.dankito.datetime.serialization

import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.deser.std.StdDeserializer
import net.dankito.datetime.LocalDate

open class LocalDateDelegatingJacksonDeserializer : StdDeserializer<LocalDate>(LocalDate::class.java) {

    override fun deserialize(parser: JsonParser, context: DeserializationContext): LocalDate =
        getDeserializer().deserialize(parser, context)


    private fun getDeserializer(): StdDeserializer<LocalDate> = when (SerializationConfig.LocalDateDefaultFormat) {
        DateTimeSerializationFormat.Iso8601 -> LocalDateIso8601JacksonDeserializer.Instance
        DateTimeSerializationFormat.Components -> LocalDateComponentJacksonDeserializer.Instance
        DateTimeSerializationFormat.Custom -> SerializationConfig.LocalDateCustomJacksonDeserializer
    }

}