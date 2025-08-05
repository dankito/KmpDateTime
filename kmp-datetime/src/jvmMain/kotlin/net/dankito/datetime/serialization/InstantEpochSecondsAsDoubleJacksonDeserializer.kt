package net.dankito.datetime.serialization

import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.deser.std.StdDeserializer
import net.dankito.datetime.Instant

open class InstantEpochSecondsAsDoubleJacksonDeserializer : StdDeserializer<Instant>(Instant::class.java) {

    companion object {
        val Instance = InstantEpochSecondsAsDoubleJacksonDeserializer()
    }


    override fun deserialize(parser: JsonParser, context: DeserializationContext): Instant =
        Instant.ofEpochSeconds(parser.doubleValue)

}