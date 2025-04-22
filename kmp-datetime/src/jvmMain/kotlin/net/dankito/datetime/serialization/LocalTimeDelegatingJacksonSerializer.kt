package net.dankito.datetime.serialization

import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.databind.SerializerProvider
import com.fasterxml.jackson.databind.ser.std.StdSerializer
import net.dankito.datetime.LocalTime

open class LocalTimeDelegatingJacksonSerializer : StdSerializer<LocalTime>(LocalTime::class.java) {

    override fun serialize(value: LocalTime, generator: JsonGenerator, provider: SerializerProvider) {
        getSerializer().serialize(value, generator, provider)
    }


    private fun getSerializer(): StdSerializer<LocalTime> = when (SerializationConfig.LocalTimeDefaultFormat) {
        DateTimeSerializationFormat.Iso8601 -> LocalTimeIso8601JacksonSerializer.Instance
        DateTimeSerializationFormat.Components -> LocalTimeComponentJacksonSerializer.Instance
        DateTimeSerializationFormat.Custom -> SerializationConfig.LocalTimeCustomJacksonSerializer
    }

}