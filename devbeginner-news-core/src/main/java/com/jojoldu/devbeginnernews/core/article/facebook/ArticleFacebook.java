package com.jojoldu.devbeginnernews.core.article.facebook;

import com.jojoldu.devbeginnernews.core.article.Article;
import com.jojoldu.devbeginnernews.core.common.BaseTimeEntity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Getter
@NoArgsConstructor
@Entity
public class ArticleFacebook extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 800)
    private String pageId;

    @Column(nullable = false, length = 800)
    private String postsId;

    private long likes;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "article_id", foreignKey = @ForeignKey(name = "fk_facebook_article_id"))
    private Article article;

    @Builder(builderMethodName = "byApi", builderClassName = "ByApi")
    public ArticleFacebook(String pageId, String postsId, long likes) {
        this.pageId = pageId;
        this.postsId = postsId;
        this.likes = likes;
    }

    public void setArticle(Article article) {
        this.article = article;
    }

    public void updateLikes (long count) {
        this.likes = count;
    }

    public Article updateArticle (String title, String content, long likes) {
        this.article.update(title, content);
        this.updateLikes(likes);
        return this.article;
    }
}
