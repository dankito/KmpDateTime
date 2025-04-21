package net.dankito.datetime.serialization

import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.deser.std.StdDeserializer
import net.dankito.datetime.LocalDateTime

open class LocalDateTimeIso8601JacksonDeserializer : StdDeserializer<LocalDateTime>(LocalDateTime::class.java) {

    companion object {
        val Instance = LocalDateTimeIso8601JacksonDeserializer()
    }


    override fun deserialize(parser: JsonParser, context: DeserializationContext): LocalDateTime =
        LocalDateTime.parse(parser.valueAsString)

}