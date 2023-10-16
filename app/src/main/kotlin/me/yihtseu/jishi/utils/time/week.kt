package me.yihtseu.jishi.utils.time

import android.icu.text.SimpleDateFormat
import android.icu.util.Calendar
import java.util.*

fun weeksPast(dateString: String): Int {
    val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
    val currentDate = Calendar.getInstance().apply { time = Date() }
    val givenDate = Calendar.getInstance().apply { time = dateFormat.parse(dateString) }
    val weeksPassed = (currentDate.timeInMillis - givenDate.timeInMillis) / (7 * 24 * 60 * 60 * 1000)
    return Math.abs(weeksPassed.toInt()) + 1
}