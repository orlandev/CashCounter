package com.orlandev.cashcounter.utils

import android.content.Context
import android.text.format.DateUtils

object Date {

    fun now() = System.currentTimeMillis()

    fun getDate(context: Context): String {
        return DateUtils.formatDateTime(
            context, System.currentTimeMillis(), DateUtils.FORMAT_SHOW_DATE
        ) + " - " + DateUtils.formatDateTime(
            context, System.currentTimeMillis(), DateUtils.FORMAT_SHOW_TIME
        )
    }
}