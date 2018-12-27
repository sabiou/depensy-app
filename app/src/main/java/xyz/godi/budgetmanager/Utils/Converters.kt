package xyz.godi.budgetmanager.Utils

import androidx.room.TypeConverter
import java.util.*

/**
 * Converters class to converts a custom class to and from a known type that Room can persist.
 */
class Converters {

    @TypeConverter fun calenderToDateStamp(calendar: Calendar): Long = calendar.timeInMillis

    @TypeConverter fun dateStampToCalendar(value: Long): Calendar =
            Calendar.getInstance().apply { timeInMillis = value }

}