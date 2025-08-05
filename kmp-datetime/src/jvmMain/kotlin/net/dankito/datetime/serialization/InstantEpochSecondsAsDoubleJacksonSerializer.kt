package net.dankito.datetime.serialization

import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.databind.SerializerProvider
import com.fasterxml.jackson.databind.ser.std.StdSerializer
import net.dankito.datetime.Instant

open class InstantEpochSecondsAsDoubleJacksonSerializer : StdSerializer<Instant>(Instant::class.java) {

    companion object {
        val Instance = InstantEpochSecondsAsDoubleJacksonSerializer()
    }


    override fun serialize(value: Instant, generator: JsonGenerator, provider: SerializerProvider) {
        generator.writeNumber(value.toEpochSecondsAsDouble())
    }

}