package net.dankito.kotlin.datetime.serialization

import com.fasterxml.jackson.databind.module.SimpleModule
import net.dankito.kotlin.datetime.LocalDate
import net.dankito.kotlin.datetime.LocalDateTime
import net.dankito.kotlin.datetime.LocalTime

/**
 * Jackson Module with all our date and time serializers and deserializers for Jackson.
 *
 * Either register it manually with `ObjectMapper().registerModule(KmpDateTimeModule())` or with
 * `ObjectMapper().findAndRegisterModules()`, which automatically registers this module due to our ServiceLoader file in
 * `kmp-date-time/src/jvmMain/resources/META-INF/services/com.fasterxml.jackson.databind.Module`.
 */
open class KmpDateTimeModule : SimpleModule("KmpDateTimeModule") {

    override fun setupModule(context: SetupContext) {
        addSerializer(LocalDate::class.java, JacksonLocalDateIso8601Serializer())
        addDeserializer(LocalDate::class.java, JacksonLocalDateIso8601Deserializer())

        addSerializer(LocalTime::class.java, JacksonLocalTimeIso8601Serializer())
        addDeserializer(LocalTime::class.java, JacksonLocalTimeIso8601Deserializer())

        addSerializer(LocalDateTime::class.java, JacksonLocalDateTimeIso8601Serializer())
        addDeserializer(LocalDateTime::class.java, JacksonLocalDateTimeIso8601Deserializer())

        super.setupModule(context)
    }

}