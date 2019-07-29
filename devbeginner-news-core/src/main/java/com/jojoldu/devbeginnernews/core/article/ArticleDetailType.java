package com.jojoldu.devbeginnernews.core.article;

import com.jojoldu.devbeginnernews.core.common.type.EnumType;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ArticleDetailType implements EnumType {
    RECRUIT ("채용", ArticleType.RECRUIT),
    ESSAY ("에세이", ArticleType.ESSAY),
    SEMINAR ("세미나", ArticleType.SEMINAR),
    WEB_FRONT_END ("웹 프론트엔드", ArticleType.TECH),
    MOBILE ("안드로이드/IOS", ArticleType.TECH),
    BACK_END ("백엔드", ArticleType.TECH),
    DEV_OPS ("데브옵스", ArticleType.TECH),
    ETC ("기타", ArticleType.ETC);

    private final String title;
    private final ArticleType articleType;

    @Override
    public String getId() {
        return name();
    }
}
