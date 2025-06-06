package net.dankito.datetime.serialization

import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.databind.SerializerProvider
import com.fasterxml.jackson.databind.ser.std.StdSerializer
import net.dankito.datetime.Instant

open class InstantIso8601JacksonSerializer : StdSerializer<Instant>(Instant::class.java) {

    companion object {
        val Instance = InstantIso8601JacksonSerializer()
    }


    override fun serialize(value: Instant, generator: JsonGenerator, provider: SerializerProvider) {
        generator.writeString(value.isoString)
    }

}