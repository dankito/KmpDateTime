package net.dankito.datetime.serialization

import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.deser.std.StdDeserializer
import net.dankito.datetime.LocalDate

open class LocalDateIso8601JacksonDeserializer : StdDeserializer<LocalDate>(LocalDate::class.java) {

    companion object {
        val Instance = LocalDateIso8601JacksonDeserializer()
    }


    override fun deserialize(parser: JsonParser, context: DeserializationContext): LocalDate =
        LocalDate.parse(parser.valueAsString)

}