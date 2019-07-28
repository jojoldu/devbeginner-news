package com.jojoldu.devbeginnernews.core.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


public abstract class LocalDateTimeUtils {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public static String toStringDate(LocalDateTime localDateTime, String pattern) {
        return localDateTime.format(DateTimeFormatter.ofPattern(pattern));
    }

    public static String toStringDate(LocalDateTime localDateTime) {
        if (localDateTime == null) {
            return "";
        }
        return FORMATTER.format(localDateTime);
    }
}
