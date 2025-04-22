package net.dankito.datetime.serialization

import kotlinx.serialization.Serializable

@Serializable
class LocalTimeSurrogate(val hour: Int, val minute: Int, val second: Int = 0, val nanosecond: Int = 0)