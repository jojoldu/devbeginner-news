package com.jojoldu.devbeginnernews.web.service;

import com.jojoldu.devbeginnernews.core.article.Article;
import lombok.Getter;

import static com.jojoldu.devbeginnernews.core.utils.LocalDateTimeUtils.toStringDate;

@Getter
public class ArticleItemDto {
    private Long id;
    private String limitTitle;
    private String limitContent;
    private String articleTypeName;
    private long likes;
    private long views = 0;
    private String registrationDateTime;

    public ArticleItemDto(Article article) {
        this.id = article.getId();
        this.limitTitle = parseTitle(article.getTitle());
        this.limitContent = parseContent(article.getContent());
        this.articleTypeName = article.getArticleType().getTitle();
        this.likes = article.getLikes();
        this.registrationDateTime = toStringDate(article.getRegistrationDateTime());
    }

    private String parseTitle(String title) {
        final int titleLimit = 25;

        if(title.length() <= titleLimit) {
            return title;
        }
        return title.substring(0, titleLimit);
    }

    private String parseContent(String content) {
        final int contentLimit = 60;

        if(content.length() <= contentLimit) {
            return content;
        }

        return content.substring(0, contentLimit);
    }
}
