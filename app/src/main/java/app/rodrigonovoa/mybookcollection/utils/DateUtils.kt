package app.rodrigonovoa.mybookcollection.utils

import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

object DateUtils {
    fun fromTimestampToDateString(time: Long): String {
        try {
            val sdf = SimpleDateFormat("dd/MM/yyyy")
            val netDate = Date(time)
            return sdf.format(netDate)
        } catch (e: Exception) {
            return e.toString()
        }
    }

    fun fromMillisecondsToTimeString(time: Long) : String {
        return String.format("%02d:%02d:%02d",
            TimeUnit.MILLISECONDS.toHours(time),
            TimeUnit.MILLISECONDS.toMinutes(time) -
                    TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(time)),
            TimeUnit.MILLISECONDS.toSeconds(time) -
                    TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(time)))
    }

    fun fromMillisecondsToHours(time: Long) : Float {
        return (time / 1000 / 60).toFloat() / 60
    }

    fun getCurrentDateTimeAsTimeStamp(): Long {
        return Calendar.getInstance().timeInMillis
    }

    fun getCurrentDateAsString(): String {
        val time = Calendar.getInstance().time
        val formatter = SimpleDateFormat("yyyy-MM-dd")
        val current = formatter.format(time)

        return current
    }

    fun fromCalendarToString(calendar: Calendar): String {
        val time = calendar.time
        val formatter = SimpleDateFormat("yyyy-MM-dd")
        val current = formatter.format(time)

        return current
    }
}