package net.dankito.kotlin.datetime.serialization

import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import net.dankito.kotlin.datetime.LocalDateTime

/**
 * A serializer for [LocalDateTime] that uses the ISO 8601 representation.
 *
 * JSON example: `"2007-12-31T23:59:01"`
 *
 * @see LocalDateTime.parse
 * @see LocalDateTime.isoString
 */
object LocalDateTimeIso8601Serializer: KSerializer<LocalDateTime> {

    override val descriptor: SerialDescriptor =
        PrimitiveSerialDescriptor("net.dankito.kotlin.datetime.LocalDateTime", PrimitiveKind.STRING)

    override fun deserialize(decoder: Decoder): LocalDateTime =
        LocalDateTime.parse(decoder.decodeString())

    override fun serialize(encoder: Encoder, value: LocalDateTime) {
        encoder.encodeString(value.isoString)
    }

}