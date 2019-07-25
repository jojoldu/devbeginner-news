package com.jojoldu.devbeginnernews.core.article;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ArticleType {
    RECRUIT ("채용"),
    ESSAY ("에세이"),
    SEMINAR ("세미나"),
    WEB_FRONT_END ("웹 프론트엔드"),
    MOBILE ("안드로이드/IOS"),
    BACK_END ("백엔드"),
    DEV_OPS ("데브옵스"),
    ETC ("기타");

    private final String title;
}
