package net.dankito.datetime.serialization

import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.databind.SerializerProvider
import com.fasterxml.jackson.databind.ser.std.StdSerializer
import net.dankito.datetime.LocalDateTime

open class LocalDateTimeIso8601JacksonSerializer : StdSerializer<LocalDateTime>(LocalDateTime::class.java) {

    companion object {
        val Instance = LocalDateTimeIso8601JacksonSerializer()
    }


    override fun serialize(value: LocalDateTime, generator: JsonGenerator, provider: SerializerProvider) {
        generator.writeString(value.isoString)
    }

}