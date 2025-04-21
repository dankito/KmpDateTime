package net.dankito.datetime.serialization

import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.databind.SerializerProvider
import com.fasterxml.jackson.databind.ser.std.StdSerializer
import net.dankito.datetime.LocalTime

open class JacksonLocalTimeIso8601Serializer : StdSerializer<LocalTime>(LocalTime::class.java) {

    override fun serialize(value: LocalTime, generator: JsonGenerator, provider: SerializerProvider) {
        generator.writeString(value.isoString)
    }

}