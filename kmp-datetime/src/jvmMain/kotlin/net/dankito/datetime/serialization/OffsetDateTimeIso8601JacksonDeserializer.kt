package net.dankito.datetime.serialization

import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.deser.std.StdDeserializer
import net.dankito.datetime.OffsetDateTime

@OptIn(ExperimentalMultiplatform::class)
open class OffsetDateTimeIso8601JacksonDeserializer : StdDeserializer<OffsetDateTime>(OffsetDateTime::class.java) {

    companion object {
        val Instance = OffsetDateTimeIso8601JacksonDeserializer()
    }


    override fun deserialize(parser: JsonParser, context: DeserializationContext): OffsetDateTime =
        OffsetDateTime.parse(parser.valueAsString)

}