package net.dankito.datetime.serialization

import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.databind.SerializerProvider
import com.fasterxml.jackson.databind.ser.std.StdSerializer
import net.dankito.datetime.UtcOffset

open class UtcOffsetIso8601JacksonSerializer : StdSerializer<UtcOffset>(UtcOffset::class.java) {

    companion object {
        val Instance = UtcOffsetIso8601JacksonSerializer()
    }


    override fun serialize(value: UtcOffset, generator: JsonGenerator, provider: SerializerProvider) {
        generator.writeString(value.isoString)
    }

}