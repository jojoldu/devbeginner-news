package com.jojoldu.devbeginnernews.core.article;

import com.jojoldu.devbeginnernews.core.common.BaseTimeEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.TemporalField;

@Getter
@NoArgsConstructor
@Entity
@Table(
        indexes = {
                @Index(name = "idx_article_1", columnList = "descIndex"),
                @Index(name = "idx_article_2", columnList = "registrationDate")
        }
)
public class Article extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 50)
    private ArticleType articleType;

    @Column(nullable = false, length = 1000)
    private String content;

    @Column(length = 1000)
    private String link;

    private LocalDateTime registrationDateTime;
    private LocalDate registrationDate;
    private long descIndex;

    @Builder
    public Article(ArticleType articleType, String content, String link, LocalDateTime registrationDateTime) {
        this.articleType = articleType;
        this.content = content;
        this.link = link;
        this.registrationDateTime = registrationDateTime;
        this.registrationDate = registrationDateTime.toLocalDate();
        this.descIndex = toMilliseconds(registrationDateTime);
    }

    private long toMilliseconds(LocalDateTime registrationDateTime) {
        return ZonedDateTime.of(registrationDateTime, ZoneId.systemDefault()).toInstant().toEpochMilli();
    }
}
