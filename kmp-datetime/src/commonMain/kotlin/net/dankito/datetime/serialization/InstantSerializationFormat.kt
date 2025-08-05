package net.dankito.datetime.serialization

enum class InstantSerializationFormat {
    Iso8601,

    EpochMilliseconds,

    EpochNanoseconds,

    EpochSecondsAsDouble,

    Components,

    Custom
}