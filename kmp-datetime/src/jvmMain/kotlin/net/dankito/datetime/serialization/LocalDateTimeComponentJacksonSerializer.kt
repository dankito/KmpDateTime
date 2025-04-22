package net.dankito.datetime.serialization

import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.databind.SerializerProvider
import com.fasterxml.jackson.databind.ser.std.StdSerializer
import net.dankito.datetime.LocalDateTime

open class LocalDateTimeComponentJacksonSerializer : StdSerializer<LocalDateTime>(LocalDateTime::class.java) {

    companion object {
        val Instance = LocalDateTimeComponentJacksonSerializer()
    }


    override fun serialize(value: LocalDateTime, generator: JsonGenerator, provider: SerializerProvider) {
        generator.writeObject(LocalDateTimeSurrogate(value.year, value.monthNumber, value.day, value.hour, value.minute, value.second, value.nanosecondOfSecond))
    }

}