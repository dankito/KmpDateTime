package net.dankito.datetime.serialization

import kotlinx.serialization.KSerializer
import net.dankito.datetime.Instant
import net.dankito.datetime.LocalDate
import net.dankito.datetime.LocalDateTime
import net.dankito.datetime.LocalTime

/**
 * Configure the default serializers for datetime classes.
 *
 * These settings of course can be overwritten on property level like:
 * ```kotlin
 * @Serializable(with = InstantComponentSerializer::class)
 * val instant: Instant
 * ```
 */
object SerializationConfig {

    var LocalDateSerializationFormat = DateTimeSerializationFormat.Iso8601

    /**
     * If you like to apply a custom serializer for [LocalDate],
     * set [LocalDateSerializationFormat] to [DateTimeSerializationFormat.Custom]
     * and set your custom implementation here.
     *
     * Defaults to [LocalDateIso8601Serializer].
     */
    var LocalDateCustomSerializer: KSerializer<LocalDate> = LocalDateIso8601Serializer


    var LocalTimeSerializationFormat = DateTimeSerializationFormat.Iso8601

    /**
     * If you like to apply a custom serializer for [LocalTime],
     * set [LocalTimeSerializationFormat] to [DateTimeSerializationFormat.Custom]
     * and set your custom implementation here.
     *
     * Defaults to [LocalTimeIso8601Serializer].
     */
    var LocalTimeCustomSerializer: KSerializer<LocalTime> = LocalTimeIso8601Serializer


    var LocalDateTimeSerializationFormat = DateTimeSerializationFormat.Iso8601

    /**
     * If you like to apply a custom serializer for [LocalDateTime],
     * set [LocalDateTimeSerializationFormat] to [DateTimeSerializationFormat.Custom]
     * and set your custom implementation here.
     *
     * Defaults to [LocalDateIso8601Serializer].
     */
    var LocalDateTimeCustomSerializer: KSerializer<LocalDateTime> = LocalDateTimeIso8601Serializer


    var InstantSerializationFormat = DateTimeSerializationFormat.Iso8601

    /**
     * If you like to apply a custom serializer for [Instant],
     * set [InstantSerializationFormat] to [DateTimeSerializationFormat.Custom]
     * and set your custom implementation here.
     *
     * Defaults to [InstantIso8601Serializer].
     */
    var InstantCustomSerializer: KSerializer<Instant> = InstantIso8601Serializer

}