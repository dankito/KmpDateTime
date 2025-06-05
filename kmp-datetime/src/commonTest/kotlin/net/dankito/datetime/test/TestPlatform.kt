package net.dankito.datetime.test

import net.codinux.kotlin.platform.Platform
import net.codinux.kotlin.platform.isJavaScript

object TestPlatform {

    val isJavaScript: Boolean by lazy { Platform.isJavaScript }

}