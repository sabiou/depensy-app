package xyz.godi.budgetmanager.Utils;

import android.arch.persistence.room.TypeConverter;

import java.util.Date;

/**
 *  Converters class to converts a custom class to and from a known type that Room can persist.
 */
public class Converters {
    @TypeConverter
    public static Date fromTimestamp(Long value) {
        return value == null ? null : new Date(value);
    }

    @TypeConverter
    public static Long dateToTimestamp(Date date) {
        return date == null ? null : date.getTime();
    }

}