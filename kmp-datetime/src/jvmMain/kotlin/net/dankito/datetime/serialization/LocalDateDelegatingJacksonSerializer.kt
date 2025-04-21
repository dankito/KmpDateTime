package net.dankito.datetime.serialization

import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.databind.SerializerProvider
import com.fasterxml.jackson.databind.ser.std.StdSerializer
import net.dankito.datetime.LocalDate

open class LocalDateDelegatingJacksonSerializer : StdSerializer<LocalDate>(LocalDate::class.java) {

    override fun serialize(value: LocalDate, generator: JsonGenerator, provider: SerializerProvider) {
        getSerializer().serialize(value, generator, provider)
    }


    private fun getSerializer(): StdSerializer<LocalDate> = when (SerializationConfig.LocalDateDefaultFormat) {
        DateTimeSerializationFormat.Custom -> SerializationConfig.LocalDateCustomJacksonSerializer
        else -> LocalDateIso8601JacksonSerializer.Instance
    }

}