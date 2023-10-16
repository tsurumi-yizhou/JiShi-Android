package me.yihtseu.jishi.utils.time

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.icu.util.Calendar
import android.provider.CalendarContract
import me.yihtseu.jishi.model.campus.edu.LessonResult
import kotlin.math.abs


@SuppressLint("Range")
fun checkEventExists(context: Context, event: LessonResult.Datas.studentLessonTable.Row): Boolean {
    val (month, day) = parseDate(event.startDate)
    val startTime = Calendar.getInstance().apply {
        set(2023, month - 1, day, startHour(event.startLessonNum), startMin(event.startLessonNum), 0)
    }.timeInMillis
    val endTime = Calendar.getInstance().apply {
        set(2023, month - 1, day, endHour(event.endLessonNum), endMin(event.endLessonNum), 0)
    }.timeInMillis
    val title = event.lessonName

    val cursor = context.contentResolver.query(CalendarContract.Events.CONTENT_URI, null, null, null, null)
        ?: return true
    if (cursor.moveToFirst()) {
        do {
            val start = cursor.getLong(cursor.getColumnIndex(CalendarContract.Events.DTSTART))
            val end = cursor.getLong(cursor.getColumnIndex(CalendarContract.Events.DTEND))
            val event = cursor.getString(cursor.getColumnIndex(CalendarContract.Events.TITLE))
            if (abs(start - startTime) < 10000 && abs(end - endTime) < 10000 && event == title) {
                cursor.close()
                return true
            }
        } while (cursor.moveToNext())
    }
    cursor.close()
    return false
}

fun insertEvent(context: Context, event: LessonResult.Datas.studentLessonTable.Row) {
    val (month, day) = parseDate(event.startDate)
    val startTime = Calendar.getInstance().apply {
        set(2023, month - 1, day, startHour(event.startLessonNum), startMin(event.startLessonNum), 0)
    }.timeInMillis
    val endTime = Calendar.getInstance().apply {
        set(2023, month - 1, day, endHour(event.endLessonNum), endMin(event.endLessonNum), 0)
    }.timeInMillis
    val event = ContentValues().apply {
        put(CalendarContract.Events.CALENDAR_ID, 1)
        put(CalendarContract.Events.TITLE, event.lessonName)
        put(CalendarContract.Events.DESCRIPTION, event.teacherName)
        put(CalendarContract.Events.EVENT_LOCATION, event.classroomName)
        put(CalendarContract.Events.DTSTART, startTime)
        put(CalendarContract.Events.DTEND, endTime)
        put(CalendarContract.Events.EVENT_TIMEZONE, "Asia/Shanghai")
    }
    context.contentResolver.insert(CalendarContract.Events.CONTENT_URI, event)
}