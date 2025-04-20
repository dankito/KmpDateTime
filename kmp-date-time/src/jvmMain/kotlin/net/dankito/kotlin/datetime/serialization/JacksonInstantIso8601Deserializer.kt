package net.dankito.kotlin.datetime.serialization

import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.deser.std.StdDeserializer
import net.dankito.kotlin.datetime.Instant

open class JacksonInstantIso8601Deserializer : StdDeserializer<Instant>(Instant::class.java) {

    override fun deserialize(parser: JsonParser, context: DeserializationContext): Instant =
        Instant.parse(parser.valueAsString)

}