package net.dankito.kotlin.datetime.platform

import net.dankito.kotlin.datetime.Instant

internal actual object Platform {

    actual fun getInstantNow(): Instant {
        val javaInstance = java.time.Instant.now()

        return Instant(javaInstance.epochSecond, javaInstance.nano)
    }

}