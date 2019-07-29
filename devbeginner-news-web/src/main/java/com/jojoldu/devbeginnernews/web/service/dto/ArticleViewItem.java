package com.jojoldu.devbeginnernews.web.service.dto;

import com.jojoldu.devbeginnernews.core.article.Article;
import com.jojoldu.devbeginnernews.core.utils.UrlMatcher;
import lombok.Getter;

import java.util.List;

import static com.jojoldu.devbeginnernews.core.utils.LocalDateTimeUtils.toStringDate;

@Getter
public class ArticleViewItem {
    private Long id;
    private String title;
    private String content;
    private String articleTypeName;
    private String link;
    private long likes;
    private long views;
    private String registrationDateTime;

    public ArticleViewItem(Article article, long views) {
        this.id = article.getId();
        this.title = article.getTitle();
        this.content = article.getContent();
        this.articleTypeName = article.getArticleType().getTitle();
        this.link = article.getLink();
        this.likes = article.getLikes();
        this.views = views;
        this.registrationDateTime = toStringDate(article.getRegistrationDateTime());
    }

    public String getLimitTitle() {
        final int titleLimit = 25;

        if(title.length() <= titleLimit) {
            return title;
        }
        return title.substring(0, titleLimit);
    }

    public String getLimitContent() {
        final int contentLimit = 60;

        if(content.length() <= contentLimit) {
            return content;
        }

        return content.substring(0, contentLimit);
    }

    public String getHtmlContent() {
        String[] texts = content.split("\n");
        StringBuilder builder = new StringBuilder();

        for (String text : texts) {
            builder.append(wrapHtmlTag(text));
        }

        return builder.toString();
    }

    private String wrapHtmlTag(String text) {
        if(isHttp(text)) {
            return wrapHtmlTag(UrlMatcher.parse(text));
        }

        return String.format("<p>%s</p>", text);
    }

    private String wrapHtmlTag (List<String> hasLink) {
        StringBuilder builder = new StringBuilder();

        for (String text : hasLink) {
            if(isHttp(text)) {
                builder.append(String.format("<p><a target=\"_blank\" href=\"%s\">%s</a></p>", text, text));
            } else {
                builder.append(String.format("<p>%s</p>", text));
            }
        }

        return builder.toString();
    }

    private boolean isHttp(String text) {
        return text.contains("http");
    }


}
