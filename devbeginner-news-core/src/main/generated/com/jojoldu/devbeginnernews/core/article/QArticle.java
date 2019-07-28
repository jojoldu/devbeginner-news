package com.jojoldu.devbeginnernews.core.article;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QArticle is a Querydsl query type for Article
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QArticle extends EntityPathBase<Article> {

    private static final long serialVersionUID = -792939989L;

    public static final QArticle article = new QArticle("article");

    public final com.jojoldu.devbeginnernews.core.common.QBaseTimeEntity _super = new com.jojoldu.devbeginnernews.core.common.QBaseTimeEntity(this);

    public final EnumPath<ArticleType> articleType = createEnum("articleType", ArticleType.class);

    public final StringPath content = createString("content");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdDate = _super.createdDate;

    public final NumberPath<Long> descIndex = createNumber("descIndex", Long.class);

    public final ListPath<com.jojoldu.devbeginnernews.core.article.facebook.ArticleFacebook, com.jojoldu.devbeginnernews.core.article.facebook.QArticleFacebook> facebooks = this.<com.jojoldu.devbeginnernews.core.article.facebook.ArticleFacebook, com.jojoldu.devbeginnernews.core.article.facebook.QArticleFacebook>createList("facebooks", com.jojoldu.devbeginnernews.core.article.facebook.ArticleFacebook.class, com.jojoldu.devbeginnernews.core.article.facebook.QArticleFacebook.class, PathInits.DIRECT2);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath link = createString("link");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedDate = _super.modifiedDate;

    public final DatePath<java.time.LocalDate> registrationDate = createDate("registrationDate", java.time.LocalDate.class);

    public final DateTimePath<java.time.LocalDateTime> registrationDateTime = createDateTime("registrationDateTime", java.time.LocalDateTime.class);

    public final StringPath title = createString("title");

    public QArticle(String variable) {
        super(Article.class, forVariable(variable));
    }

    public QArticle(Path<? extends Article> path) {
        super(path.getType(), path.getMetadata());
    }

    public QArticle(PathMetadata metadata) {
        super(Article.class, metadata);
    }

}

