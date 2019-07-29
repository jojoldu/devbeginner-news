package com.jojoldu.devbeginnernews.core.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UrlMatcher {
    private static final Pattern URL_PATTERN = Pattern.compile(
            "(?:^|[\\W])((ht|f)tp(s?):\\/\\/|www\\.)"
                    + "(([\\w\\-]+\\.){1,}?([\\w\\-.~]+\\/?)*"
                    + "[\\p{Alnum}.,%_=?&#\\-+()\\[\\]\\*$~@!:/{};']*)",
            Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);

    public static List<String> parse (String text) {
        List<String> result = new ArrayList<>();
        Matcher matcher = URL_PATTERN.matcher(text);

        while (matcher.find()) {
            int matchStart = matcher.start(1);
            int matchEnd = matcher.end();

            if(matchStart != 0) {
                result.add(text.substring(0, matchStart));
            }

            result.add(text.substring(matchStart, matchEnd));

            if(matchEnd != text.length()) {
                result.add(text.substring(matchEnd));
            }
        }

        return result;
    }
}
