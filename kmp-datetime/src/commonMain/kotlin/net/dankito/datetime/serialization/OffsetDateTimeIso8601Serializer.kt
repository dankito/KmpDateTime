package net.dankito.datetime.serialization

import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import net.dankito.datetime.OffsetDateTime

/**
 * A serializer for [OffsetDateTime] that uses the ISO 8601 representation.
 *
 * JSON example: `"2007-12-31T23:59:01+05:30"`
 *
 * @see OffsetDateTime.parse
 * @see OffsetDateTime.isoString
 */
@OptIn(ExperimentalMultiplatform::class)
object OffsetDateTimeIso8601Serializer: KSerializer<OffsetDateTime> {

    override val descriptor: SerialDescriptor =
        PrimitiveSerialDescriptor("net.dankito.datetime.OffsetDateTime", PrimitiveKind.STRING)


    override fun deserialize(decoder: Decoder): OffsetDateTime =
        OffsetDateTime.parse(decoder.decodeString())

    override fun serialize(encoder: Encoder, value: OffsetDateTime) {
        encoder.encodeString(value.isoString)
    }

}