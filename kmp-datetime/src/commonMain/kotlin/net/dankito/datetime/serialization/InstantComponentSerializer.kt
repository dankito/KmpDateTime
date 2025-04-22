package net.dankito.datetime.serialization

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import net.dankito.datetime.Instant

/**
 * A serializer for [Instant] that represents an `Instant` value as second and nanosecond components of the Unix time.
 *
 * JSON example: `{"epochSeconds":1607505416,"nanosecondsOfSecond":124000}`
 */
object InstantComponentSerializer: KSerializer<Instant> {

    @OptIn(ExperimentalSerializationApi::class)
    override val descriptor: SerialDescriptor =
        SerialDescriptor("net.dankito.datetime.Instant", InstantSurrogate.serializer().descriptor)


    override fun serialize(encoder: Encoder, value: Instant) {
        val surrogate = InstantSurrogate(value.epochSeconds, value.nanosecondsOfSecond)
        encoder.encodeSerializableValue(InstantSurrogate.serializer(), surrogate)
    }

    override fun deserialize(decoder: Decoder): Instant {
        val surrogate = decoder.decodeSerializableValue(InstantSurrogate.serializer())
        return Instant(surrogate.epochSeconds, surrogate.nanosecondsOfSecond)
    }

}