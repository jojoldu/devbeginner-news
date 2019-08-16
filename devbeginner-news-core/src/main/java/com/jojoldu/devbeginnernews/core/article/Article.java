package com.jojoldu.devbeginnernews.core.article;

import com.jojoldu.devbeginnernews.core.article.facebook.ArticleFacebook;
import com.jojoldu.devbeginnernews.core.common.BaseTimeEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.CollectionUtils;

import javax.annotation.Nonnull;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

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

    @Column(nullable = false, length = 200)
    private String title;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 50)
    private ArticleDetailType articleType;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    @Column(length = 1000)
    private String link;

    private LocalDateTime registrationDateTime;
    private LocalDate registrationDate;
    private long descIndex;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "article")
    private List<ArticleFacebook> facebooks = new ArrayList<>(); // oneToOne 이지만 Lazy Loading을 위해 oneToMany

    @Builder
    public Article(@Nonnull String title, @Nonnull ArticleDetailType articleType, @Nonnull String content, String link, @Nonnull LocalDateTime registrationDateTime) {
        this.title = title;
        this.articleType = articleType;
        this.content = content;
        this.link = link;
        this.registrationDateTime = registrationDateTime;
        this.registrationDate = registrationDateTime.toLocalDate();
        this.descIndex = toMilliseconds(registrationDateTime) * -1;
    }

    private long toMilliseconds(LocalDateTime registrationDateTime) {
        return ZonedDateTime.of(registrationDateTime, ZoneId.systemDefault()).toInstant().toEpochMilli();
    }

    /**
     * 1:1 관계지만 Lazy Loading을 위해 OneToMany
     */
    public void setFacebook (ArticleFacebook facebook) {
        facebooks.add(facebook);
        facebook.setArticle(this);
    }

    public ArticleFacebook getFacebook() {
        return facebooks.get(0);
    }

    public String getPostsId() {
        if(CollectionUtils.isEmpty(facebooks)) {
            return "";
        }

        return facebooks.get(0).getPostsId();
    }

    public long getLikes() {
        if(CollectionUtils.isEmpty(facebooks)) {
            return 0;
        }

        return facebooks.get(0).getLikes();
    }

    public void update (String title, String content, long likes) {
        this.title = title;
        this.content = content;
        this.getFacebook().updateLikes(likes);
    }

}
