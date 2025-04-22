package net.dankito.datetime.serialization

import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.deser.std.StdDeserializer
import net.dankito.datetime.Instant

open class InstantComponentJacksonDeserializer : StdDeserializer<Instant>(Instant::class.java) {

    companion object {
        val Instance = InstantComponentJacksonDeserializer()
    }


    override fun deserialize(parser: JsonParser, context: DeserializationContext): Instant {
        val surrogate = parser.readValueAs(InstantSurrogate::class.java)
        return Instant(surrogate.epochSeconds, surrogate.nanosecondsOfSecond)
    }

}