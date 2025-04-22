package net.dankito.datetime.serialization

import kotlinx.serialization.Serializable

@Serializable
class LocalDateSurrogate(val year: Int, val month: Int, val day: Int)