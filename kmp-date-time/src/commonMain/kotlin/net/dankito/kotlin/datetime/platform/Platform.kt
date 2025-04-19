package net.dankito.kotlin.datetime.platform

import net.dankito.kotlin.datetime.Instant
import net.dankito.kotlin.datetime.LocalDate
import net.dankito.kotlin.datetime.LocalDateTime
import net.dankito.kotlin.datetime.LocalTime

internal expect object Platform {

    fun getInstantNow(): Instant

    fun getLocalDateNow(): LocalDate

    fun getLocalTimeNow(): LocalTime

    fun getLocalDateTimeNow(): LocalDateTime

}