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
        addSerializer(LocalDate::class.java, LocalDateIso8601JacksonSerializer.Instance)
        addDeserializer(LocalDate::class.java, LocalDateIso8601JacksonDeserializer.Instance)

        // last added serializer wins
        addSerializer(LocalDate::class.java, LocalDateDelegatingJacksonSerializer())
        addDeserializer(LocalDate::class.java, LocalDateDelegatingJacksonDeserializer())


        addSerializer(LocalTime::class.java, LocalTimeIso8601JacksonSerializer.Instance)
        addDeserializer(LocalTime::class.java, LocalTimeIso8601JacksonDeserializer.Instance)

        addSerializer(LocalTime::class.java, LocalTimeDelegatingJacksonSerializer())
        addDeserializer(LocalTime::class.java, LocalTimeDelegatingJacksonDeserializer())


        addSerializer(LocalDateTime::class.java, LocalDateTimeIso8601JacksonSerializer.Instance)
        addDeserializer(LocalDateTime::class.java, LocalDateTimeIso8601JacksonDeserializer.Instance)

        addSerializer(LocalDateTime::class.java, LocalDateTimeDelegatingJacksonSerializer())
        addDeserializer(LocalDateTime::class.java, LocalDateTimeDelegatingJacksonDeserializer())


        addSerializer(Instant::class.java, InstantIso8601JacksonSerializer.Instance)
        addDeserializer(Instant::class.java, InstantIso8601JacksonDeserializer.Instance)

        addSerializer(Instant::class.java, InstantEpochMillisecondsJacksonSerializer.Instance)
        addDeserializer(Instant::class.java, InstantEpochMillisecondsJacksonDeserializer.Instance)

        addSerializer(Instant::class.java, InstantDelegatingJacksonSerializer())
        addDeserializer(Instant::class.java, InstantDelegatingJacksonDeserializer())


        super.setupModule(context)
    }

}