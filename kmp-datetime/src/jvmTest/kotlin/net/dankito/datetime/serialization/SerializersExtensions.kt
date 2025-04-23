package net.dankito.datetime.serialization

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature


private val _jacksonObjectMapper = ObjectMapper().apply {
    findAndRegisterModules() // registers Kotlin module and JSR 310 (Java DateTime) module

    // by default Jackson serializes date times as numbers like "[2015,10,21]", disable that
    disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)

    // by default Jackson e.g. for OffsetDateTimes takes the deserialized timezone and transfers
    // the time object to UTC before returning it.
    // E.g. "12:00+02:00" but Jackson returns OffsetDateTime(hour = 10, offset = UtcOffset(0))
    disable(DeserializationFeature.ADJUST_DATES_TO_CONTEXT_TIME_ZONE)
}

val Serializers.jacksonObjectMapper: ObjectMapper
    get() = _jacksonObjectMapper