package com.jojoldu.devbeginnernews.web.config.handlebars;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Getter
@RequiredArgsConstructor
public enum HandlebarsTemplates {
    ARTICLES ("articles");

    private final String fileName;

    public static List<String> getAllFiles() {
        return Arrays.stream(HandlebarsTemplates.values())
                .map(e -> e.fileName)
                .collect(Collectors.toList());
    }
}
