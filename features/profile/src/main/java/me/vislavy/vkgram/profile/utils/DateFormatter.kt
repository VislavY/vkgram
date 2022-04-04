package me.vislavy.vkgram.profile.utils

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import me.vislavy.vkgram.profile.R
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun formatDate(value: Date): String {
    val resources = LocalContext.current.resources
    val timeFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
    val monthFormat = SimpleDateFormat("MMM", Locale.getDefault())
    val dayOfMonthFormat = SimpleDateFormat("M", Locale.getDefault())

    val lastSeenDate = GregorianCalendar()
    lastSeenDate.time = value
    val currentDate = GregorianCalendar()

    lastSeenDate.set(Calendar.MILLISECOND, 0)
    currentDate.set(Calendar.MILLISECOND, 0)
    if (lastSeenDate == currentDate) {
        return stringResource(R.string.last_seen_online).lowercase()
    }

    lastSeenDate.set(Calendar.SECOND, 0)
    currentDate.set(Calendar.SECOND, 0)
    currentDate.add(Calendar.MINUTE, -1)
    if (lastSeenDate > currentDate) {
        return stringResource(R.string.last_seen_recently).lowercase()
    }

    var millisDifferent = (currentDate.timeInMillis - lastSeenDate.timeInMillis).toInt()
    val minutesDifferent = if (millisDifferent == 0) 1 else millisDifferent / (60 * 1000)
    lastSeenDate.set(Calendar.MINUTE, 0)
    currentDate.set(Calendar.MINUTE, 0)
    if (lastSeenDate == currentDate) {
        return resources.getQuantityString(
            R.plurals.last_seen_minute_plurals,
            minutesDifferent,
            if (minutesDifferent == 1) "" else "$minutesDifferent"
        ).lowercase()
    }

    currentDate.add(Calendar.HOUR, -1)
    millisDifferent = (currentDate.timeInMillis - lastSeenDate.timeInMillis).toInt()
    val hoursDifferent = if (millisDifferent == 0) 1 else millisDifferent / (60 * 1000 * 60)
    if (lastSeenDate >= currentDate) {
        return resources.getQuantityString(
            R.plurals.last_seen_hour_plurals,
            hoursDifferent,
            if (hoursDifferent == 1) "" else "$hoursDifferent"
        ).lowercase()
    }

    lastSeenDate.set(Calendar.HOUR, 0)
    currentDate.set(Calendar.HOUR, 0)
    if (lastSeenDate == currentDate) {
        return stringResource(R.string.last_seen_today, timeFormat.format(value)).lowercase()
    }

    currentDate.add(Calendar.DAY_OF_WEEK, -1)
    if (lastSeenDate >= currentDate) {
        return stringResource(
            R.string.last_seen_yesterday,
            timeFormat.format(value)
        ).lowercase()
    }

    lastSeenDate.set(Calendar.DAY_OF_WEEK, 0)
    currentDate.set(Calendar.DAY_OF_WEEK, 0)
    if (lastSeenDate == currentDate) {
        val dayOfWeekFormat = SimpleDateFormat("E", Locale.getDefault())
        return stringResource(
            R.string.last_seen_week,
            dayOfWeekFormat.format(value),
            timeFormat.format(value)
        ).lowercase()
    }

    lastSeenDate.set(Calendar.WEEK_OF_MONTH, 0)
    lastSeenDate.set(Calendar.DAY_OF_MONTH, 0)
    lastSeenDate.set(Calendar.MONTH, 0)
    currentDate.set(Calendar.WEEK_OF_MONTH, 0)
    currentDate.set(Calendar.DAY_OF_MONTH, 0)
    currentDate.set(Calendar.MONTH, 0)
    if (lastSeenDate == currentDate) {
        return stringResource(
            R.string.last_seen_month,
            monthFormat.format(value),
            dayOfMonthFormat.format(value),
            timeFormat.format(value)
        ).lowercase()
    }

    val yearFormat = SimpleDateFormat("y", Locale.getDefault())
    return stringResource(
        R.string.last_seen_year,
        monthFormat.format(value),
        dayOfMonthFormat.format(value),
        yearFormat.format(value),
        timeFormat.format(value)
    ).lowercase()
}