package com.jojoldu.devbeginnernews.core.article.facebook;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QArticleFacebook is a Querydsl query type for ArticleFacebook
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QArticleFacebook extends EntityPathBase<ArticleFacebook> {

    private static final long serialVersionUID = 1661714911L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QArticleFacebook articleFacebook = new QArticleFacebook("articleFacebook");

    public final com.jojoldu.devbeginnernews.core.common.QBaseTimeEntity _super = new com.jojoldu.devbeginnernews.core.common.QBaseTimeEntity(this);

    public final com.jojoldu.devbeginnernews.core.article.QArticle article;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdDate = _super.createdDate;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final NumberPath<Long> likes = createNumber("likes", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedDate = _super.modifiedDate;

    public final StringPath pageId = createString("pageId");

    public final StringPath postsId = createString("postsId");

    public QArticleFacebook(String variable) {
        this(ArticleFacebook.class, forVariable(variable), INITS);
    }

    public QArticleFacebook(Path<? extends ArticleFacebook> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QArticleFacebook(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QArticleFacebook(PathMetadata metadata, PathInits inits) {
        this(ArticleFacebook.class, metadata, inits);
    }

    public QArticleFacebook(Class<? extends ArticleFacebook> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.article = inits.isInitialized("article") ? new com.jojoldu.devbeginnernews.core.article.QArticle(forProperty("article")) : null;
    }

}

