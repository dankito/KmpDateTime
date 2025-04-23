package net.dankito.datetime.serialization

import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.databind.SerializerProvider
import com.fasterxml.jackson.databind.ser.std.StdSerializer
import net.dankito.datetime.OffsetDateTime

open class OffsetDateTimeIso8601JacksonSerializer : StdSerializer<OffsetDateTime>(OffsetDateTime::class.java) {

    companion object {
        val Instance = OffsetDateTimeIso8601JacksonSerializer()
    }


    override fun serialize(value: OffsetDateTime, generator: JsonGenerator, provider: SerializerProvider) {
        generator.writeString(value.isoString)
    }

}