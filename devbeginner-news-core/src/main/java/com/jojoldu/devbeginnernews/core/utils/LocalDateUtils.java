package com.jojoldu.devbeginnernews.core.utils;

import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static java.time.LocalDate.parse;
import static java.time.format.DateTimeFormatter.ofPattern;


public abstract class LocalDateUtils {

    private static final DateTimeFormatter FORMATTER = ofPattern("yyyy-MM-dd");

    public static LocalDate toLocalDate (String stringDate) {
        return toLocalDate(stringDate, FORMATTER);
    }

    public static LocalDate toLocalDate(String stringDate, DateTimeFormatter pattern) {
        if(StringUtils.isEmpty(stringDate)) {
            return null;
        }

        return parse(stringDate, pattern);
    }

    public static String toStringDate(LocalDate localDate) {
        if (localDate == null) {
            return "";
        }
        return FORMATTER.format(localDate);
    }
}
