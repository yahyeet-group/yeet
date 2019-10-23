package com.yahyeet.boardbook.activity.matchcreation

import android.content.Context

object HelperFunctions {

    // TODO: Move somewhere more fitting, need utils package somewhere
    fun dpFromPx(px: Int, c: Context): Int {
        return (px * c.resources.displayMetrics.density).toInt()
    }
}
