package net.dankito.datetime.serialization

import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.deser.std.StdDeserializer
import net.dankito.datetime.LocalDateTime

open class JacksonLocalDateTimeIso8601Deserializer : StdDeserializer<LocalDateTime>(LocalDateTime::class.java) {

    override fun deserialize(parser: JsonParser, context: DeserializationContext): LocalDateTime =
        LocalDateTime.parse(parser.valueAsString)

}