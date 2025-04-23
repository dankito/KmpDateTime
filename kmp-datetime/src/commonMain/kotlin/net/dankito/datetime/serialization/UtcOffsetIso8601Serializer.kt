package net.dankito.datetime.serialization

import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import net.dankito.datetime.UtcOffset

/**
 * A serializer for [UtcOffset] that uses the ISO 8601 representation.
 *
 * JSON example: `"+05:30"`, `"-12:00:45"`, `"Z"`
 *
 * @see UtcOffset.parse
 * @see UtcOffset.isoString
 */
@OptIn(ExperimentalMultiplatform::class)
object UtcOffsetIso8601Serializer : KSerializer<UtcOffset> {

    override val descriptor: SerialDescriptor =
        PrimitiveSerialDescriptor("net.dankito.datetime.UtcOffset", PrimitiveKind.STRING)


    override fun deserialize(decoder: Decoder): UtcOffset =
        UtcOffset.parse(decoder.decodeString())

    override fun serialize(encoder: Encoder, value: UtcOffset) {
        encoder.encodeString(value.isoString)
    }

}