package net.dankito.datetime.serialization

import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.databind.SerializerProvider
import com.fasterxml.jackson.databind.ser.std.StdSerializer
import net.dankito.datetime.LocalDate

open class LocalDateDelegatingJacksonSerializer : StdSerializer<LocalDate>(LocalDate::class.java) {

    companion object {
        val Instance = LocalDateDelegatingJacksonSerializer()
    }


    override fun serialize(value: LocalDate, generator: JsonGenerator, provider: SerializerProvider) {
        getSerializer().serialize(value, generator, provider)
    }


    private fun getSerializer(): StdSerializer<LocalDate> = when (SerializationConfig.LocalDateDefaultFormat) {
        DateTimeSerializationFormat.Iso8601 -> LocalDateIso8601JacksonSerializer.Instance
        DateTimeSerializationFormat.Components -> LocalDateComponentJacksonSerializer.Instance
        DateTimeSerializationFormat.Custom -> SerializationConfig.LocalDateCustomJacksonSerializer
    }

}