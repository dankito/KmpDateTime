package net.dankito.kotlin.datetime.serialization

import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.deser.std.StdDeserializer
import net.dankito.kotlin.datetime.LocalDate

open class JacksonLocalDateIso8601Deserializer : StdDeserializer<LocalDate>(LocalDate::class.java) {

    override fun deserialize(parser: JsonParser, context: DeserializationContext): LocalDate =
        LocalDate.parse(parser.valueAsString)

}