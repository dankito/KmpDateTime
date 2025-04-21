package net.dankito.datetime.serialization

import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import net.dankito.datetime.LocalDate

/**
 * The default serializer for [LocalDate] that delegates serialization according to
 * [SerializationConfig.LocalDateSerializationFormat]:
 * - [DateTimeSerializationFormat.Iso8601] -> delegates to [LocalDateIso8601Serializer]
 */
object LocalDateDelegatingSerializer: KSerializer<LocalDate> {

    override val descriptor: SerialDescriptor =
        PrimitiveSerialDescriptor("net.dankito.datetime.LocalDate", PrimitiveKind.STRING)

    override fun deserialize(decoder: Decoder): LocalDate =
        getSerializer().deserialize(decoder)

    override fun serialize(encoder: Encoder, value: LocalDate) {
        getSerializer().serialize(encoder, value)
    }


    private fun getSerializer(): KSerializer<LocalDate> = when (SerializationConfig.LocalDateSerializationFormat) {
        DateTimeSerializationFormat.Iso8601 -> LocalDateIso8601Serializer
    }

}