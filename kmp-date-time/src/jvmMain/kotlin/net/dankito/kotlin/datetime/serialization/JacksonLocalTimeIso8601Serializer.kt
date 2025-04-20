package net.dankito.kotlin.datetime.serialization

import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.databind.SerializerProvider
import com.fasterxml.jackson.databind.ser.std.StdSerializer
import net.dankito.kotlin.datetime.LocalTime

open class JacksonLocalTimeIso8601Serializer : StdSerializer<LocalTime>(LocalTime::class.java) {

    override fun serialize(value: LocalTime, generator: JsonGenerator, provider: SerializerProvider) {
        generator.writeString(value.isoString)
    }

}