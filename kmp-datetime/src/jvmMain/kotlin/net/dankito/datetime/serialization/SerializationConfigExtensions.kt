package net.dankito.datetime.serialization

import com.fasterxml.jackson.databind.deser.std.StdDeserializer
import com.fasterxml.jackson.databind.ser.std.StdSerializer
import net.dankito.datetime.Instant
import net.dankito.datetime.LocalDate
import net.dankito.datetime.LocalDateTime
import net.dankito.datetime.LocalTime


private var _localDateCustomJacksonSerializer: StdSerializer<LocalDate> = LocalDateIso8601JacksonSerializer.Instance

var SerializationConfig.LocalDateCustomJacksonSerializer: StdSerializer<LocalDate>
    get() = _localDateCustomJacksonSerializer
    set(value) { _localDateCustomJacksonSerializer = value }

private var _localDateCustomJacksonDeserializer: StdDeserializer<LocalDate> = LocalDateIso8601JacksonDeserializer.Instance

var SerializationConfig.LocalDateCustomJacksonDeserializer: StdDeserializer<LocalDate>
    get() = _localDateCustomJacksonDeserializer
    set(value) { _localDateCustomJacksonDeserializer = value }


private var _localTimeCustomJacksonSerializer: StdSerializer<LocalTime> = LocalTimeIso8601JacksonSerializer.Instance

var SerializationConfig.LocalTimeCustomJacksonSerializer: StdSerializer<LocalTime>
    get() = _localTimeCustomJacksonSerializer
    set(value) { _localTimeCustomJacksonSerializer = value }

private var _localTimeCustomJacksonDeserializer: StdDeserializer<LocalTime> = LocalTimeIso8601JacksonDeserializer.Instance

var SerializationConfig.LocalTimeCustomJacksonDeserializer: StdDeserializer<LocalTime>
    get() = _localTimeCustomJacksonDeserializer
    set(value) { _localTimeCustomJacksonDeserializer = value }


private var _localDateTimeCustomJacksonSerializer: StdSerializer<LocalDateTime> = LocalDateTimeIso8601JacksonSerializer.Instance

var SerializationConfig.LocalDateTimeCustomJacksonSerializer: StdSerializer<LocalDateTime>
    get() = _localDateTimeCustomJacksonSerializer
    set(value) { _localDateTimeCustomJacksonSerializer = value }

private var _localDateTimeCustomJacksonDeserializer: StdDeserializer<LocalDateTime> = LocalDateTimeIso8601JacksonDeserializer.Instance

var SerializationConfig.LocalDateTimeCustomJacksonDeserializer: StdDeserializer<LocalDateTime>
    get() = _localDateTimeCustomJacksonDeserializer
    set(value) { _localDateTimeCustomJacksonDeserializer = value }


private var _instantCustomJacksonSerializer: StdSerializer<Instant> = InstantIso8601JacksonSerializer.Instance

var SerializationConfig.InstantCustomJacksonSerializer: StdSerializer<Instant>
    get() = _instantCustomJacksonSerializer
    set(value) { _instantCustomJacksonSerializer = value }

private var _instantCustomJacksonDeserializer: StdDeserializer<Instant> = InstantIso8601JacksonDeserializer.Instance

var SerializationConfig.InstantCustomJacksonDeserializer: StdDeserializer<Instant>
    get() = _instantCustomJacksonDeserializer
    set(value) { _instantCustomJacksonDeserializer = value }