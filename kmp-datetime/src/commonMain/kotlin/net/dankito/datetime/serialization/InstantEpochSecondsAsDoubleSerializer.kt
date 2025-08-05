package net.dankito.datetime.serialization

import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import net.dankito.datetime.Instant

/**
 * A serializer for [Instant] that represents an `Instant` value as seconds since Unix Epoch time
 * as floating point in format `<seconds since epoch>.<nanoseconds of second>`.
 *
 * JSON example: `1607505416124`
 */
object InstantEpochSecondsAsDoubleSerializer: KSerializer<Instant> {

    override val descriptor: SerialDescriptor =
        PrimitiveSerialDescriptor("net.dankito.datetime.Instant", PrimitiveKind.DOUBLE)


    override fun serialize(encoder: Encoder, value: Instant) {
        encoder.encodeDouble(value.toEpochSecondsAsDouble())
    }

    override fun deserialize(decoder: Decoder): Instant =
        Instant.ofEpochSeconds(decoder.decodeDouble())

}