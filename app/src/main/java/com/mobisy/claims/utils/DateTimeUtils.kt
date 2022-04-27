package com.mobisy.claims.utils

import java.text.SimpleDateFormat
import java.util.*

object DateTimeUtils {
    private const val DATE_FORMAT = "dd-MM-yyyy"
    private const val TIME_FORMAT = "HH:mm:ss"

    fun getDate(calendar: Calendar): String? {
        val dateFormat = SimpleDateFormat(DATE_FORMAT, Locale.getDefault())
        return dateFormat.format(calendar.time)
    }

    fun getTime(): String? {
        val dateFormat = SimpleDateFormat(TIME_FORMAT, Locale.getDefault())
        val calendar = Calendar.getInstance()
        return dateFormat.format(calendar.time)
    }

    fun getTodayDate(): String? {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val calendar = Calendar.getInstance()
        return dateFormat.format(calendar.time)
    }
}