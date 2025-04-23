package net.dankito.datetime.serialization

import com.fasterxml.jackson.databind.module.SimpleModule
import net.dankito.datetime.*

/**
 * Jackson Module with all our date and time serializers and deserializers for Jackson.
 *
 * Either register it manually with `ObjectMapper().registerModule(KmpDateTimeModule())` or with
 * `ObjectMapper().findAndRegisterModules()`, which automatically registers this module due to our ServiceLoader file in
 * `kmp-datetime/src/jvmMain/resources/META-INF/services/com.fasterxml.jackson.databind.Module`.
 */
@OptIn(ExperimentalMultiplatform::class)
open class KmpDateTimeModule : SimpleModule("KmpDateTimeModule") {

    override fun setupModule(context: SetupContext) {
        addSerializer(LocalDate::class.java, LocalDateIso8601JacksonSerializer.Instance)
        addDeserializer(LocalDate::class.java, LocalDateIso8601JacksonDeserializer.Instance)

        addSerializer(LocalDate::class.java, LocalDateComponentJacksonSerializer.Instance)
        addDeserializer(LocalDate::class.java, LocalDateComponentJacksonDeserializer.Instance)

        // last added serializer wins
        addSerializer(LocalDate::class.java, LocalDateDelegatingJacksonSerializer.Instance)
        addDeserializer(LocalDate::class.java, LocalDateDelegatingJacksonDeserializer.Instance)


        addSerializer(LocalTime::class.java, LocalTimeIso8601JacksonSerializer.Instance)
        addDeserializer(LocalTime::class.java, LocalTimeIso8601JacksonDeserializer.Instance)

        addSerializer(LocalTime::class.java, LocalTimeComponentJacksonSerializer.Instance)
        addDeserializer(LocalTime::class.java, LocalTimeComponentJacksonDeserializer.Instance)

        addSerializer(LocalTime::class.java, LocalTimeDelegatingJacksonSerializer.Instance)
        addDeserializer(LocalTime::class.java, LocalTimeDelegatingJacksonDeserializer.Instance)


        addSerializer(LocalDateTime::class.java, LocalDateTimeIso8601JacksonSerializer.Instance)
        addDeserializer(LocalDateTime::class.java, LocalDateTimeIso8601JacksonDeserializer.Instance)

        addSerializer(LocalDateTime::class.java, LocalDateTimeComponentJacksonSerializer.Instance)
        addDeserializer(LocalDateTime::class.java, LocalDateTimeComponentJacksonDeserializer.Instance)

        addSerializer(LocalDateTime::class.java, LocalDateTimeDelegatingJacksonSerializer.Instance)
        addDeserializer(LocalDateTime::class.java, LocalDateTimeDelegatingJacksonDeserializer.Instance)


        addSerializer(Instant::class.java, InstantIso8601JacksonSerializer.Instance)
        addDeserializer(Instant::class.java, InstantIso8601JacksonDeserializer.Instance)

        addSerializer(Instant::class.java, InstantEpochMillisecondsJacksonSerializer.Instance)
        addDeserializer(Instant::class.java, InstantEpochMillisecondsJacksonDeserializer.Instance)

        addSerializer(Instant::class.java, InstantComponentJacksonSerializer.Instance)
        addDeserializer(Instant::class.java, InstantComponentJacksonDeserializer.Instance)

        addSerializer(Instant::class.java, InstantDelegatingJacksonSerializer.Instance)
        addDeserializer(Instant::class.java, InstantDelegatingJacksonDeserializer.Instance)


        addSerializer(UtcOffset::class.java, UtcOffsetIso8601JacksonSerializer.Instance)
        addDeserializer(UtcOffset::class.java, UtcOffsetIso8601JacksonDeserializer.Instance)


        super.setupModule(context)
    }

}