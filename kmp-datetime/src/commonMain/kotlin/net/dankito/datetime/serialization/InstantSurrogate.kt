package net.dankito.datetime.serialization

import kotlinx.serialization.Serializable

@Serializable
class InstantSurrogate(val epochSeconds: Long, val nanosecondsOfSecond: Int)