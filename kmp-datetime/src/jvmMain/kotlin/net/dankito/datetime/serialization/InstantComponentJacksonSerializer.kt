package net.dankito.datetime.serialization

import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.databind.SerializerProvider
import com.fasterxml.jackson.databind.ser.std.StdSerializer
import net.dankito.datetime.Instant

open class InstantComponentJacksonSerializer : StdSerializer<Instant>(Instant::class.java) {

    companion object {
        val Instance = InstantComponentJacksonSerializer()
    }


    override fun serialize(value: Instant, generator: JsonGenerator, provider: SerializerProvider) {
        generator.writeObject(InstantSurrogate(value.epochSeconds, value.nanosecondsOfSecond))
    }

}