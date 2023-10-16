package me.yihtseu.jishi.utils.time

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.*

fun today(): String {
    return LocalDate.now().toString()
}

fun yesterday(): String {
    return LocalDate.now().minusDays(1).toString()
}

fun format(formatter: String, timestamp: Long): String {
    return SimpleDateFormat(formatter).format(Date(timestamp))
}

@SuppressLint("SimpleDateFormat")
fun parse(formatter: String, dateString: String): Long {
    return SimpleDateFormat(formatter).parse(dateString).time
}

fun parseDate(date: String): Pair<Int, Int> {
    val regex = "(\\d{2})月(\\d{2})日".toRegex()
    val values = regex.find(date)!!.groupValues
    return Pair(values[1].toInt(), values[2].toInt())
}