package net.dankito.datetime.serialization

import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.databind.SerializerProvider
import com.fasterxml.jackson.databind.ser.std.StdSerializer
import net.dankito.datetime.LocalDateTime

open class LocalDateTimeDelegatingJacksonSerializer : StdSerializer<LocalDateTime>(LocalDateTime::class.java) {

    override fun serialize(value: LocalDateTime, generator: JsonGenerator, provider: SerializerProvider) {
        getSerializer().serialize(value, generator, provider)
    }


    private fun getSerializer(): StdSerializer<LocalDateTime> = when (SerializationConfig.LocalDateTimeDefaultFormat) {
        DateTimeSerializationFormat.Custom -> SerializationConfig.LocalDateTimeCustomJacksonSerializer
        else -> LocalDateTimeIso8601JacksonSerializer.Instance
    }

}