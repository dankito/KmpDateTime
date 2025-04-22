package net.dankito.datetime.serialization

import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.deser.std.StdDeserializer
import net.dankito.datetime.LocalDateTime

open class LocalDateTimeComponentJacksonDeserializer : StdDeserializer<LocalDateTime>(LocalDateTime::class.java) {

    companion object {
        val Instance = LocalDateTimeComponentJacksonDeserializer()
    }


    override fun deserialize(parser: JsonParser, context: DeserializationContext): LocalDateTime {
        val surrogate = parser.readValueAs(LocalDateTimeSurrogate::class.java)
        return LocalDateTime(surrogate.year, surrogate.month, surrogate.day,
            surrogate.hour, surrogate.minute, surrogate.second, surrogate.nanosecond)
    }

}