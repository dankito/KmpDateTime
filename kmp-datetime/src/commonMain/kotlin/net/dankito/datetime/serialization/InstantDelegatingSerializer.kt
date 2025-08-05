package net.dankito.datetime.serialization

import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import net.dankito.datetime.Instant

/**
 * The default serializer for [Instant] that delegates serialization according to
 * [SerializationConfig.InstantDefaultFormat]:
 * - [InstantSerializationFormat.Iso8601] -> delegates to [InstantIso8601Serializer]
 * - [InstantSerializationFormat.EpochMilliseconds] -> delegates to [InstantEpochMillisecondsSerializer]
 * - [InstantSerializationFormat.EpochSecondsAsDouble] -> delegates to [InstantEpochSecondsAsDoubleSerializer]
 * - [InstantSerializationFormat.Components] -> delegates to [InstantComponentSerializer]
 * - [InstantSerializationFormat.Custom] -> delegates to serializer set in [SerializationConfig.InstantCustomSerializer]
 */
object InstantDelegatingSerializer: KSerializer<Instant> {

    override val descriptor: SerialDescriptor =
        PrimitiveSerialDescriptor("net.dankito.datetime.Instant", PrimitiveKind.STRING)

    override fun deserialize(decoder: Decoder): Instant =
        getSerializer().deserialize(decoder)

    override fun serialize(encoder: Encoder, value: Instant) {
        getSerializer().serialize(encoder, value)
    }


    private fun getSerializer(): KSerializer<Instant> = when (SerializationConfig.InstantDefaultFormat) {
        InstantSerializationFormat.Iso8601 -> InstantIso8601Serializer
        InstantSerializationFormat.EpochMilliseconds -> InstantEpochMillisecondsSerializer
        InstantSerializationFormat.EpochSecondsAsDouble -> InstantEpochSecondsAsDoubleSerializer
        InstantSerializationFormat.Components -> InstantComponentSerializer
        InstantSerializationFormat.Custom -> SerializationConfig.InstantCustomSerializer
    }

}