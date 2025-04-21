package net.dankito.datetime.serialization

import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.deser.std.StdDeserializer
import net.dankito.datetime.Instant

open class InstantIso8601JacksonDeserializer : StdDeserializer<Instant>(Instant::class.java) {

    companion object {
        val Instance = InstantIso8601JacksonDeserializer()
    }


    override fun deserialize(parser: JsonParser, context: DeserializationContext): Instant =
        Instant.parse(parser.valueAsString)

}