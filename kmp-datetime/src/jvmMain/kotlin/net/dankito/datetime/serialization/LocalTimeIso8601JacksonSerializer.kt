package net.dankito.datetime.serialization

import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.databind.SerializerProvider
import com.fasterxml.jackson.databind.ser.std.StdSerializer
import net.dankito.datetime.LocalTime

open class LocalTimeIso8601JacksonSerializer : StdSerializer<LocalTime>(LocalTime::class.java) {

    companion object {
        val Instance = LocalTimeIso8601JacksonSerializer()
    }


    override fun serialize(value: LocalTime, generator: JsonGenerator, provider: SerializerProvider) {
        generator.writeString(value.isoString)
    }

}