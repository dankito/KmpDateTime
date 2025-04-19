package net.dankito.kotlin.datetime.platform

import net.dankito.kotlin.datetime.Instant
import net.dankito.kotlin.datetime.LocalDate

internal expect object Platform {

    fun getInstantNow(): Instant

    fun getLocalDateNow(): LocalDate

}