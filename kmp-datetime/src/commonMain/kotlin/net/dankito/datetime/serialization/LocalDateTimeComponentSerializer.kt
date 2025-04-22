package net.dankito.datetime.serialization

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import net.dankito.datetime.LocalDateTime

/**
 * A serializer for [LocalDateTime] that represents a value as its components.
 *
 * JSON example: `{"year":2008,"month":7,"day":5,"hour":2,"minute":1}`
 */
object LocalDateTimeComponentSerializer: KSerializer<LocalDateTime> {

    @OptIn(ExperimentalSerializationApi::class)
    override val descriptor: SerialDescriptor =
        SerialDescriptor("net.dankito.datetime.LocalDateTime", LocalDateTimeSurrogate.serializer().descriptor)


    override fun serialize(encoder: Encoder, value: LocalDateTime) {
        val surrogate = LocalDateTimeSurrogate(value.year, value.monthNumber, value.day, value.hour, value.minute, value.second, value.nanosecondOfSecond)
        encoder.encodeSerializableValue(LocalDateTimeSurrogate.serializer(), surrogate)
    }

    override fun deserialize(decoder: Decoder): LocalDateTime {
        val surrogate = decoder.decodeSerializableValue(LocalDateTimeSurrogate.serializer())
        return LocalDateTime(surrogate.year, surrogate.month, surrogate.day, surrogate.hour, surrogate.minute, surrogate.second, surrogate.nanosecond)
    }

}