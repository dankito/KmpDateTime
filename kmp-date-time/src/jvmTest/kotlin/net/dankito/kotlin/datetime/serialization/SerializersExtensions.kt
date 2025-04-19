package net.dankito.kotlin.datetime.serialization

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature


private val _jacksonObjectMapper = ObjectMapper().apply {
    findAndRegisterModules() // registers Kotlin module and JSR 310 (Java DateTime) module

    // by default Jackson serializes date times as numbers like "[2015,10,21]", disable that
    disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
}

val Serializers.jacksonObjectMapper: ObjectMapper
    get() = _jacksonObjectMapper