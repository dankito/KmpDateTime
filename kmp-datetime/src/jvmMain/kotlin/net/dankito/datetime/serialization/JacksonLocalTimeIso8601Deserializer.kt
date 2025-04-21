package net.dankito.datetime.serialization

import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.deser.std.StdDeserializer
import net.dankito.datetime.LocalTime

open class JacksonLocalTimeIso8601Deserializer : StdDeserializer<LocalTime>(LocalTime::class.java) {

    override fun deserialize(parser: JsonParser, context: DeserializationContext): LocalTime =
        LocalTime.parse(parser.valueAsString)

}