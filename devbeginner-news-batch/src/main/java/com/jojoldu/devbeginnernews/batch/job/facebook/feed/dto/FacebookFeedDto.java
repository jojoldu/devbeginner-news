package com.jojoldu.devbeginnernews.batch.job.facebook.feed.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.jojoldu.devbeginnernews.core.article.Article;
import com.jojoldu.devbeginnernews.core.article.ArticleDetailType;
import com.jojoldu.devbeginnernews.core.article.facebook.ArticleFacebook;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

@ToString
@Getter
@NoArgsConstructor
public class FacebookFeedDto {
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ssZ");

    private String id;
    private String message;
    @JsonProperty("created_time")
    private String createdTime;
    private FacebookLikeDto likes;
    private FacebookAttachmentsDto attachments;

    @Builder
    public FacebookFeedDto(String id, String message, String createdTime, FacebookLikeDto likes, FacebookAttachmentsDto attachments) {
        this.id = id;
        this.message = message;
        this.createdTime = createdTime;
        this.likes = likes;
        this.attachments = attachments;
    }

    @Builder(builderMethodName = "forTest", builderClassName = "ForTest")
    public FacebookFeedDto(String id, String message, String createdTime, long totalCount, String url) {
        this.id = id;
        this.message = message;
        this.createdTime = createdTime;
        this.likes = new FacebookLikeDto(totalCount);
        this.attachments = new FacebookAttachmentsDto(url);
    }

    public boolean isNotEmpty() {
        return !StringUtils.isEmpty(message);
    }

    public LocalDateTime parseCreatedTime() {
        ZonedDateTime zonedDateTime = ZonedDateTime.parse(createdTime, FORMATTER);
        ZoneId kstZone = ZoneId.of("Asia/Seoul");
        ZonedDateTime kstZoned = zonedDateTime.withZoneSameInstant(kstZone);
        return kstZoned.toLocalDateTime();
    }

    public String parseUrl() {
        if(attachments == null) {
            return "";
        }

        return attachments.getUrl();
    }

    public String parseTitle() {
        String title = message.split("\n")[0];
        if(title.length() > 200) {
            return title.substring(199);
        }

        return title;
    }

    public long parseLikes() {
        return likes.getTotalCount();
    }

    public Article toArticle(String pageId) {
        Article article = Article.builder()
                .registrationDateTime(parseCreatedTime())
                .content(message)
                .link(parseUrl())
                .articleType(ArticleDetailType.ETC)
                .title(parseTitle())
                .build();
        article.setFacebook(toArticleFacebook(pageId));
        return article;
    }

    public ArticleFacebook toArticleFacebook(String pageId) {
        return ArticleFacebook.byApi()
                .pageId(pageId)
                .postsId(id)
                .likes(parseLikes())
                .build();
    }

}
