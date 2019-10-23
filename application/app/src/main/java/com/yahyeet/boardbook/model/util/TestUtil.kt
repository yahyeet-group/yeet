package com.yahyeet.boardbook.model.util

import java.util.Arrays

object TestUtil {

    val isJUnitTest: Boolean
        get() {
            val stackTrace = Thread.currentThread().stackTrace
            val list = Arrays.asList(*stackTrace)
            for (element in list) {
                if (element.className.startsWith("org.junit.")) {
                    return true
                }
            }
            return false
        }
}
