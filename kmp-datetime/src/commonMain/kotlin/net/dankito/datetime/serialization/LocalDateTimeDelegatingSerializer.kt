package net.dankito.datetime.serialization

import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import net.dankito.datetime.LocalDateTime

/**
 * The default serializer for [LocalDateTime] that delegates serialization according to
 * [SerializationConfig.LocalDateTimeSerializationFormat]:
 * - [DateTimeSerializationFormat.Iso8601] -> delegates to [LocalDateTimeIso8601Serializer]
 * - [DateTimeSerializationFormat.Components] -> delegates to [LocalDateTimeComponentSerializer]
 */
object LocalDateTimeDelegatingSerializer: KSerializer<LocalDateTime> {

    override val descriptor: SerialDescriptor =
        PrimitiveSerialDescriptor("net.dankito.datetime.LocalDateTime", PrimitiveKind.STRING)

    override fun deserialize(decoder: Decoder): LocalDateTime =
        getSerializer().deserialize(decoder)

    override fun serialize(encoder: Encoder, value: LocalDateTime) {
        getSerializer().serialize(encoder, value)
    }


    private fun getSerializer(): KSerializer<LocalDateTime> = when (SerializationConfig.LocalDateTimeSerializationFormat) {
        DateTimeSerializationFormat.Iso8601 -> LocalDateTimeIso8601Serializer
        DateTimeSerializationFormat.Components -> LocalDateTimeComponentSerializer
    }

}