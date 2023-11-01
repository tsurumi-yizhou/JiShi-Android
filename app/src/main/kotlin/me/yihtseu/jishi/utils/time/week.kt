package me.yihtseu.jishi.utils.time

import android.icu.text.SimpleDateFormat
import android.icu.util.Calendar


fun weeksPast(startDate: String): Int {
    val start = SimpleDateFormat("yyyy-MM-dd 00:00:00").parse(startDate)
    val today = Calendar.getInstance().time

    return ((today.time - start.time)/(1000 * 60 * 60 * 24 * 7)).toInt() + 1
}
