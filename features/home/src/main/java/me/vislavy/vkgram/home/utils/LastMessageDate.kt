package me.vislavy.vkgram.home.utils

import java.text.SimpleDateFormat
import java.util.*

object LastMessageDate {

    fun timeDifference(date: Date): String {
        val receivedCalendar = GregorianCalendar()
        receivedCalendar.time = date
        val currentCalendar = GregorianCalendar()
        val datePattern = when {
            receivedCalendar
                .get(Calendar.YEAR) < currentCalendar
                .get(Calendar.YEAR) -> "dd.MM.yy"

            receivedCalendar
                .get(Calendar.WEEK_OF_YEAR) < currentCalendar
                .get(Calendar.WEEK_OF_YEAR) -> "dd MMM"

            receivedCalendar
                .get(Calendar.DAY_OF_WEEK) < currentCalendar
                .get(Calendar.DAY_OF_WEEK) -> "E"

            else -> "HH:mm"
        }

        val dateFormat = SimpleDateFormat(datePattern, Locale.getDefault())
        return dateFormat.format(receivedCalendar.time)
    }
}