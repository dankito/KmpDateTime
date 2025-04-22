package net.dankito.datetime.serialization

import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import net.dankito.datetime.DayOfWeek

/**
 * A serializer for [DayOfWeek] that uses its name as representation.
 */
object DayOfWeekNameSerializer: KSerializer<DayOfWeek> {

    override val descriptor: SerialDescriptor =
        PrimitiveSerialDescriptor("net.dankito.datetime.DayOfWeek", PrimitiveKind.STRING)

    override fun deserialize(decoder: Decoder): DayOfWeek =
        DayOfWeek.valueOf(decoder.decodeString())

    override fun serialize(encoder: Encoder, value: DayOfWeek) {
        encoder.encodeString(value.name)
    }

}