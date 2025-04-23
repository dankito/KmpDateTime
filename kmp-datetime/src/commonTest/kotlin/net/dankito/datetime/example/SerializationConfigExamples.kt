package net.dankito.datetime.example

import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import net.dankito.datetime.LocalTime
import net.dankito.datetime.serialization.DateTimeSerializationFormat
import net.dankito.datetime.serialization.InstantSerializationFormat
import net.dankito.datetime.serialization.SerializationConfig

class SerializationConfigExamples {

    fun configureSerializers() {
        SerializationConfig.LocalDateDefaultFormat = DateTimeSerializationFormat.Components

        SerializationConfig.InstantDefaultFormat = InstantSerializationFormat.EpochMilliseconds

        // you can also configure your custom serializer
        SerializationConfig.LocalTimeDefaultFormat = DateTimeSerializationFormat.Custom // to enable that `SerializationConfig.LocalTimeCustomSerializer` is used
        SerializationConfig.LocalTimeCustomSerializer = MyLocalTimeSerializer
    }


    object MyLocalTimeSerializer : KSerializer<LocalTime> {

        override val descriptor: SerialDescriptor =
            PrimitiveSerialDescriptor("net.dankito.datetime.LocalDate", PrimitiveKind.STRING)

        override fun serialize(encoder: Encoder, value: LocalTime) {
            encoder.encodeString(value.isoString) // add your custom serialization here
        }

        override fun deserialize(decoder: Decoder): LocalTime =
            LocalTime.parse(decoder.decodeString()) // add your custom deserialization here

    }
}