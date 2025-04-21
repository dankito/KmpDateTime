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
        addSerializer(LocalDate::class.java, JacksonLocalDateIso8601Serializer.Instance)
        addDeserializer(LocalDate::class.java, JacksonLocalDateIso8601Deserializer.Instance)

        // last added serializer wins
        addSerializer(LocalDate::class.java, JacksonLocalDateDelegatingSerializer())
        addDeserializer(LocalDate::class.java, JacksonLocalDateDelegatingDeserializer())


        addSerializer(LocalTime::class.java, JacksonLocalTimeIso8601Serializer.Instance)
        addDeserializer(LocalTime::class.java, JacksonLocalTimeIso8601Deserializer.Instance)

        addSerializer(LocalTime::class.java, JacksonLocalTimeDelegatingSerializer())
        addDeserializer(LocalTime::class.java, JacksonLocalTimeDelegatingDeserializer())


        addSerializer(LocalDateTime::class.java, JacksonLocalDateTimeIso8601Serializer.Instance)
        addDeserializer(LocalDateTime::class.java, JacksonLocalDateTimeIso8601Deserializer.Instance)

        addSerializer(LocalDateTime::class.java, JacksonLocalDateTimeDelegatingSerializer())
        addDeserializer(LocalDateTime::class.java, JacksonLocalDateTimeDelegatingDeserializer())


        addSerializer(Instant::class.java, JacksonInstantIso8601Serializer.Instance)
        addDeserializer(Instant::class.java, JacksonInstantIso8601Deserializer.Instance)

        addSerializer(Instant::class.java, JacksonInstantEpochMillisecondsSerializer.Instance)
        addDeserializer(Instant::class.java, JacksonInstantEpochMillisecondsDeserializer.Instance)

        addSerializer(Instant::class.java, JacksonInstantDelegatingSerializer())
        addDeserializer(Instant::class.java, JacksonInstantDelegatingDeserializer())


        super.setupModule(context)
    }

}