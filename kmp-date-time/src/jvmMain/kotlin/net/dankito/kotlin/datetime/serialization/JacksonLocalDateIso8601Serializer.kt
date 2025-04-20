package net.dankito.kotlin.datetime.serialization

import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.databind.SerializerProvider
import com.fasterxml.jackson.databind.ser.std.StdSerializer
import net.dankito.kotlin.datetime.LocalDate

open class JacksonLocalDateIso8601Serializer : StdSerializer<LocalDate>(LocalDate::class.java) {

    override fun serialize(value: LocalDate, generator: JsonGenerator, provider: SerializerProvider) {
        generator.writeString(value.isoString)
    }

}