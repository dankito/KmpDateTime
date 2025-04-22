package net.dankito.datetime.serialization

import kotlinx.serialization.Serializable

@Serializable
class LocalDateTimeSurrogate(val year: Int, val month: Int, val day: Int, val hour: Int, val minute: Int, val second: Int = 0, val nanosecond: Int = 0)