package com.jpitkonen.tsirbulawatch;

import androidx.room.TypeConverter;

import java.util.Date;

public class Converters {

    @TypeConverter
    public static String convertDateToString(Date date) {
        return String.valueOf(date.getTime());
    }

    @TypeConverter
    public static Date convertLongToDate(String time) {
        return new Date(time);
    }
}
