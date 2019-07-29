package com.jojoldu.devbeginnernews.core.article;

import com.jojoldu.devbeginnernews.core.common.type.EnumType;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ArticleType implements EnumType {
    RECRUIT ("채용"),
    ESSAY ("에세이"),
    SEMINAR ("세미나"),
    TECH ("기술"),
    ETC ("기타");

    private final String title;

    @Override
    public String getId() {
        return name();
    }
}
