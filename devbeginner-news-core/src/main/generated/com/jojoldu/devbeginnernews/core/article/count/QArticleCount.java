package com.jojoldu.devbeginnernews.core.article.count;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QArticleCount is a Querydsl query type for ArticleCount
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QArticleCount extends EntityPathBase<ArticleCount> {

    private static final long serialVersionUID = 1635473379L;

    public static final QArticleCount articleCount = new QArticleCount("articleCount");

    public final com.jojoldu.devbeginnernews.core.common.QBaseTimeEntity _super = new com.jojoldu.devbeginnernews.core.common.QBaseTimeEntity(this);

    public final NumberPath<Long> articleId = createNumber("articleId", Long.class);

    public final NumberPath<Long> count = createNumber("count", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdDate = _super.createdDate;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedDate = _super.modifiedDate;

    public QArticleCount(String variable) {
        super(ArticleCount.class, forVariable(variable));
    }

    public QArticleCount(Path<? extends ArticleCount> path) {
        super(path.getType(), path.getMetadata());
    }

    public QArticleCount(PathMetadata metadata) {
        super(ArticleCount.class, metadata);
    }

}

