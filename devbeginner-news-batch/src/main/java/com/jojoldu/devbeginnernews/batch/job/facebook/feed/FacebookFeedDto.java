package com.jojoldu.devbeginnernews.batch.job.facebook.feed;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.jojoldu.devbeginnernews.core.article.Article;
import com.jojoldu.devbeginnernews.core.article.ArticleType;
import com.jojoldu.devbeginnernews.core.article.facebook.ArticleFacebook;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

@ToString
@Getter
@NoArgsConstructor
public class FacebookFeedDto {
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ssZ");
    private static final String HTTP = "http";

    private String id;
    private String message;
    @JsonProperty("created_time")
    private String createdTime;

    @Builder
    public FacebookFeedDto(String id, String message, String createdTime) {
        this.id = id;
        this.message = message;
        this.createdTime = createdTime;
    }

    public LocalDateTime getCreatedTime() {
        ZonedDateTime zonedDateTime = ZonedDateTime.parse(createdTime, FORMATTER);
        ZoneId kstZone = ZoneId.of("Asia/Seoul");
        ZonedDateTime kstZoned = zonedDateTime.withZoneSameInstant(kstZone);
        return kstZoned.toLocalDateTime();
    }

    public String parseContent() {
        return message.split(HTTP)[0];
    }

    public String parseLink() {
        String[] strings = message.split(HTTP);
        if(strings.length == 1) {
            return "";
        }

        return HTTP + strings[1];
    }

    public String parseTitle() {
        String title = message.split("\n")[0];
        if(title.length() > 200) {
            return title.substring(199);
        }

        return title;
    }

    public Article toArticle(String pageId) {
        Article article = Article.builder()
                .registrationDateTime(getCreatedTime())
                .content(parseContent())
                .link(parseLink())
                .articleType(ArticleType.ETC)
                .title(parseTitle())
                .build();
        article.setFacebook(toArticleFacebook(pageId));
        return article;
    }

    public ArticleFacebook toArticleFacebook(String pageId) {
        return ArticleFacebook.byApi()
                .pageId(pageId)
                .postsId(id)
                .build();
    }

}
