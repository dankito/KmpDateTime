package net.dankito.kotlin.datetime.serialization

import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.databind.SerializerProvider
import com.fasterxml.jackson.databind.ser.std.StdSerializer
import net.dankito.kotlin.datetime.Instant

open class JacksonInstantIso8601Serializer : StdSerializer<Instant>(Instant::class.java) {

    override fun serialize(value: Instant, generator: JsonGenerator, provider: SerializerProvider) {
        generator.writeString(value.isoString)
    }

}