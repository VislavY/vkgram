package ru.vyapps.vkgram.utils

import kotlinx.serialization.Serializable
import ru.vyapps.vkgram.utils.VKDateAsLongSerializer
import java.text.SimpleDateFormat
import java.util.*

@Serializable(VKDateAsLongSerializer::class)
class VKDate(
    val date: Long
) {

    override fun toString(): String {
        val receivedCalendar = GregorianCalendar()
        receivedCalendar.time = Date(date)

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