package net.dankito.datetime.serialization

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import net.dankito.datetime.LocalTime

/**
 * A serializer for [LocalTime] that represents a value as its components.
 *
 * JSON example: `{"hour":12,"minute":1,"second":3,"nanosecond":999}`
 */
object LocalTimeComponentSerializer: KSerializer<LocalTime> {

    @OptIn(ExperimentalSerializationApi::class)
    override val descriptor: SerialDescriptor =
        SerialDescriptor("net.dankito.datetime.LocalTime", LocalTimeSurrogate.serializer().descriptor)


    override fun serialize(encoder: Encoder, value: LocalTime) {
        val surrogate = LocalTimeSurrogate(value.hour, value.minute, value.second, value.nanosecondOfSecond)
        encoder.encodeSerializableValue(LocalTimeSurrogate.serializer(), surrogate)
    }

    override fun deserialize(decoder: Decoder): LocalTime {
        val surrogate = decoder.decodeSerializableValue(LocalTimeSurrogate.serializer())
        return LocalTime(surrogate.hour, surrogate.minute, surrogate.second, surrogate.nanosecond)
    }


    @Serializable
    class LocalTimeSurrogate(val hour: Int, val minute: Int, val second: Int = 0, val nanosecond: Int = 0)

}