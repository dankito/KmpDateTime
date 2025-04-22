package net.dankito.datetime.serialization

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import net.dankito.datetime.LocalDate

/**
 * A serializer for [LocalDate] that represents a value as its components.
 *
 * JSON example: `{"year":2015,"month":10,"day":21}`
 */
object LocalDateComponentSerializer: KSerializer<LocalDate> {

    @OptIn(ExperimentalSerializationApi::class)
    override val descriptor: SerialDescriptor =
        SerialDescriptor("net.dankito.datetime.LocalDate", LocalDateSurrogate.serializer().descriptor)


    override fun serialize(encoder: Encoder, value: LocalDate) {
        val surrogate = LocalDateSurrogate(value.year, value.monthNumber, value.day)
        encoder.encodeSerializableValue(LocalDateSurrogate.serializer(), surrogate)
    }

    override fun deserialize(decoder: Decoder): LocalDate {
        val surrogate = decoder.decodeSerializableValue(LocalDateSurrogate.serializer())
        return LocalDate(surrogate.year, surrogate.month, surrogate.day)
    }

}