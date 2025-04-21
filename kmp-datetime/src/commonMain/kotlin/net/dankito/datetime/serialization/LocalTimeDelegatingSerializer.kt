package net.dankito.datetime.serialization

import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import net.dankito.datetime.LocalTime

/**
 * The default serializer for [LocalTime] that delegates serialization according to
 * [SerializationConfig.LocalTimeSerializationFormat]:
 * - [DateTimeSerializationFormat.Iso8601] -> delegates to [LocalTimeIso8601Serializer]
 */
object LocalTimeDelegatingSerializer: KSerializer<LocalTime> {

    override val descriptor: SerialDescriptor =
        PrimitiveSerialDescriptor("net.dankito.datetime.LocalTime", PrimitiveKind.STRING)

    override fun deserialize(decoder: Decoder): LocalTime =
        getSerializer().deserialize(decoder)

    override fun serialize(encoder: Encoder, value: LocalTime) {
        getSerializer().serialize(encoder, value)
    }


    private fun getSerializer(): KSerializer<LocalTime> = when (SerializationConfig.LocalTimeSerializationFormat) {
        DateTimeSerializationFormat.Iso8601 -> LocalTimeIso8601Serializer
    }

}