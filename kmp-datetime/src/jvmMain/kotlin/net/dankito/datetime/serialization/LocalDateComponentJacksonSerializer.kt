package net.dankito.datetime.serialization

import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.databind.SerializerProvider
import com.fasterxml.jackson.databind.ser.std.StdSerializer
import net.dankito.datetime.LocalDate

open class LocalDateComponentJacksonSerializer : StdSerializer<LocalDate>(LocalDate::class.java) {

    companion object {
        val Instance = LocalDateComponentJacksonSerializer()
    }


    override fun serialize(value: LocalDate, generator: JsonGenerator, provider: SerializerProvider) {
        generator.writeObject(LocalDateSurrogate(value.year, value.monthNumber, value.day))
    }

}