package net.dankito.datetime.serialization

import com.fasterxml.jackson.databind.module.SimpleModule
import net.dankito.datetime.Instant
import net.dankito.datetime.LocalDate
import net.dankito.datetime.LocalDateTime
import net.dankito.datetime.LocalTime

/**
 * Jackson Module with all our date and time serializers and deserializers for Jackson.
 *
 * Either register it manually with `ObjectMapper().registerModule(KmpDateTimeModule())` or with
 * `ObjectMapper().findAndRegisterModules()`, which automatically registers this module due to our ServiceLoader file in
 * `kmp-datetime/src/jvmMain/resources/META-INF/services/com.fasterxml.jackson.databind.Module`.
 */
open class KmpDateTimeModule : SimpleModule("KmpDateTimeModule") {

    override fun setupModule(context: SetupContext) {
        addSerializer(LocalDate::class.java, JacksonLocalDateIso8601Serializer())
        addDeserializer(LocalDate::class.java, JacksonLocalDateIso8601Deserializer())

        addSerializer(LocalTime::class.java, JacksonLocalTimeIso8601Serializer())
        addDeserializer(LocalTime::class.java, JacksonLocalTimeIso8601Deserializer())

        addSerializer(LocalDateTime::class.java, JacksonLocalDateTimeIso8601Serializer())
        addDeserializer(LocalDateTime::class.java, JacksonLocalDateTimeIso8601Deserializer())

        addSerializer(Instant::class.java, JacksonInstantIso8601Serializer())
        addDeserializer(Instant::class.java, JacksonInstantIso8601Deserializer())

        super.setupModule(context)
    }

}