package net.dankito.datetime.serialization

import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import net.dankito.datetime.Instant

/**
 * A serializer for [Instant] that represents an `Instant` value as nanoseconds since Unix Epoch time.
 *
 * JSON example: `1607505416124000000`
 */
object InstantEpochNanosecondsSerializer: KSerializer<Instant> {

    override val descriptor: SerialDescriptor =
        PrimitiveSerialDescriptor("net.dankito.datetime.Instant", PrimitiveKind.LONG)


    override fun serialize(encoder: Encoder, value: Instant) {
        encoder.encodeLong(value.toEpochNanoseconds())
    }

    override fun deserialize(decoder: Decoder): Instant =
        Instant.ofEpochNanoseconds(decoder.decodeLong())

}