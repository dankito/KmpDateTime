package net.dankito.datetime.serialization

import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.deser.std.StdDeserializer
import net.dankito.datetime.LocalTime

open class LocalTimeComponentJacksonDeserializer : StdDeserializer<LocalTime>(LocalTime::class.java) {

    companion object {
        val Instance = LocalTimeComponentJacksonDeserializer()
    }


    override fun deserialize(parser: JsonParser, context: DeserializationContext): LocalTime {
        val surrogate = parser.readValueAs(LocalTimeSurrogate::class.java)
        return LocalTime(surrogate.hour, surrogate.minute, surrogate.second, surrogate.nanosecond)
    }

}