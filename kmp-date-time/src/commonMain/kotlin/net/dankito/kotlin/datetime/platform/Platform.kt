package net.dankito.kotlin.datetime.platform

import net.dankito.kotlin.datetime.Instant

internal expect object Platform {

    fun getInstantNow(): Instant

}