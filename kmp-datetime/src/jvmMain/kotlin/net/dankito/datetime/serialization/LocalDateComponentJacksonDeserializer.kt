package net.dankito.datetime.serialization

import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.deser.std.StdDeserializer
import net.dankito.datetime.LocalDate

open class LocalDateComponentJacksonDeserializer : StdDeserializer<LocalDate>(LocalDate::class.java) {

    companion object {
        val Instance = LocalDateComponentJacksonDeserializer()
    }


    override fun deserialize(parser: JsonParser, context: DeserializationContext): LocalDate {
        val surrogate = parser.readValueAs(LocalDateSurrogate::class.java)
        return LocalDate(surrogate.year, surrogate.month, surrogate.day)
    }

}