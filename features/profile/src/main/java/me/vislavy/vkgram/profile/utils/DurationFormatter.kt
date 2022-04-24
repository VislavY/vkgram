package me.vislavy.vkgram.profile.utils

import java.text.SimpleDateFormat
import java.util.*

fun formatDuration(value: Int): String {
    val calendar = GregorianCalendar(
        0,
        0,
        0,
        0,
        0,
        0,
    )
    calendar.add(Calendar.SECOND, value)
    val dateFormatPattern = if (value <= 3600) "m:ss" else "h:mm:ss"
    val dateFormatter = SimpleDateFormat(dateFormatPattern, Locale.getDefault())
    return dateFormatter.format(calendar.timeInMillis)
}