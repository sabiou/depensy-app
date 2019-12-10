package dev.farouk.depensy.core.data.db

import androidx.room.TypeConverter
import java.util.*

class Converters {
    // If we want to persist instances of Date, we can write the following TypeConverter to store
    // the equivalent Unix timestamp in the database.

    @TypeConverter
    fun fromTimeStamp(value: Long?): Date? {
        return value?.let { Date(it) }
    }

    @TypeConverter
    fun dateToTimeStamp(date: Date?): Long? {
        return date?.time
    }
}