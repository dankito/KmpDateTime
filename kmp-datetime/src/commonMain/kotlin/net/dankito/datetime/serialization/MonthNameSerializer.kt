package net.dankito.datetime.serialization

import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import net.dankito.datetime.Month

/**
 * A serializer for [Month] that uses its name as representation.
 */
object MonthNameSerializer: KSerializer<Month> {

    override val descriptor: SerialDescriptor =
        PrimitiveSerialDescriptor("net.dankito.datetime.Month", PrimitiveKind.STRING)

    override fun deserialize(decoder: Decoder): Month =
        Month.valueOf(decoder.decodeString())

    override fun serialize(encoder: Encoder, value: Month) {
        encoder.encodeString(value.name)
    }

}