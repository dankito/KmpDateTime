package net.dankito.kotlin.datetime.platform

import net.dankito.kotlin.datetime.Instant
import net.dankito.kotlin.datetime.LocalDate
import net.dankito.kotlin.datetime.toKotlinInstant
import net.dankito.kotlin.datetime.toKotlinLocalDate

internal actual object Platform {

    actual fun getInstantNow(): Instant {
        val javaInstance = java.time.Instant.now()

        return javaInstance.toKotlinInstant()
    }

    actual fun getLocalDateNow(): LocalDate {
        val javaLocalDate = java.time.LocalDate.now()

        return javaLocalDate.toKotlinLocalDate()
    }

}