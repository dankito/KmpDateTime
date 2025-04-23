package net.dankito.datetime.serialization

import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.deser.std.StdDeserializer
import net.dankito.datetime.UtcOffset

open class UtcOffsetIso8601JacksonDeserializer : StdDeserializer<UtcOffset>(UtcOffset::class.java) {

    companion object {
        val Instance = UtcOffsetIso8601JacksonDeserializer()
    }


    override fun deserialize(parser: JsonParser, context: DeserializationContext): UtcOffset =
        UtcOffset.parse(parser.valueAsString)

}