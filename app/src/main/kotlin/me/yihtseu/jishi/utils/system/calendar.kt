package me.yihtseu.jishi.utils.system

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.provider.CalendarContract
import kotlin.math.abs

@SuppressLint("Range")
fun checkEvent(context: Context, name: String, start: Long, end: Long): Boolean {
    val cursor = context.contentResolver.query(CalendarContract.Events.CONTENT_URI, null, null, null)
    if (cursor == null) {
        return false
    }
    if (cursor.moveToFirst()) {
        do {
            if (cursor.getString(cursor.getColumnIndex(CalendarContract.Events.TITLE)) != name) continue
            if (abs(cursor.getLong(cursor.getColumnIndex(CalendarContract.Events.DTSTART)) - start) > 60000) continue
            if (abs(cursor.getLong(cursor.getColumnIndex(CalendarContract.Events.DTEND)) - end) > 60000) continue

            cursor.close()
            return true
        } while (cursor.moveToNext())
    }
    cursor.close()
    return false
}

fun insertEvent(context: Context, name: String, start: Long, end: Long, place: String, detail: String) {
    val event = ContentValues().apply {
        put(CalendarContract.Events.CALENDAR_ID, 1)
        put(CalendarContract.Events.TITLE, name)
        put(CalendarContract.Events.DESCRIPTION, detail)
        put(CalendarContract.Events.EVENT_LOCATION, place)
        put(CalendarContract.Events.DTSTART, start)
        put(CalendarContract.Events.DTEND, end)
        put(CalendarContract.Events.EVENT_TIMEZONE, "Asia/Shanghai")
    }
    context.contentResolver.insert(CalendarContract.Events.CONTENT_URI, event)
}

fun insertEvent(context: Context, name: String, start: Long, end: Long, place: String) {
    val event = ContentValues().apply {
        put(CalendarContract.Events.CALENDAR_ID, 1)
        put(CalendarContract.Events.TITLE, name)
        put(CalendarContract.Events.EVENT_LOCATION, place)
        put(CalendarContract.Events.DTSTART, start)
        put(CalendarContract.Events.DTEND, end)
        put(CalendarContract.Events.EVENT_TIMEZONE, "Asia/Shanghai")
    }
    context.contentResolver.insert(CalendarContract.Events.CONTENT_URI, event)
}