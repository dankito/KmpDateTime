package net.dankito.datetime.serialization

import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.deser.std.StdDeserializer
import net.dankito.datetime.Instant

open class InstantEpochNanosecondsJacksonDeserializer : StdDeserializer<Instant>(Instant::class.java) {

    companion object {
        val Instance = InstantEpochNanosecondsJacksonDeserializer()
    }


    override fun deserialize(parser: JsonParser, context: DeserializationContext): Instant =
        Instant.ofEpochNanoseconds(parser.longValue)

}