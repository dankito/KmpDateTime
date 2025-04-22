package net.dankito.datetime.serialization

import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.databind.SerializerProvider
import com.fasterxml.jackson.databind.ser.std.StdSerializer
import net.dankito.datetime.LocalTime

open class LocalTimeComponentJacksonSerializer : StdSerializer<LocalTime>(LocalTime::class.java) {

    companion object {
        val Instance = LocalTimeComponentJacksonSerializer()
    }


    override fun serialize(value: LocalTime, generator: JsonGenerator, provider: SerializerProvider) {
        generator.writeObject(LocalTimeSurrogate(value.hour, value.minute, value.second, value.nanosecondOfSecond))
    }

}