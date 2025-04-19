package net.dankito.kotlin.datetime.serialization

import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import net.dankito.kotlin.datetime.LocalTime

/**
 * A serializer for [LocalTime] that uses the ISO 8601 representation.
 *
 * JSON example: `"12:01:03.999"`
 *
 * @see LocalTime.parse
 * @see LocalTime.isoString
 */
object LocalTimeIso8601Serializer : KSerializer<LocalTime> {

    override val descriptor: SerialDescriptor =
        PrimitiveSerialDescriptor("net.dankito.kotlin.datetime.LocalTime", PrimitiveKind.STRING)

    override fun deserialize(decoder: Decoder): LocalTime =
        LocalTime.parse(decoder.decodeString())

    override fun serialize(encoder: Encoder, value: LocalTime) {
        encoder.encodeString(value.isoString)
    }

}