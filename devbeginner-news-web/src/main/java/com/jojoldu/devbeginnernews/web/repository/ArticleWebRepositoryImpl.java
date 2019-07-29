package com.jojoldu.devbeginnernews.web.repository;

import com.jojoldu.devbeginnernews.core.article.Article;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.jojoldu.devbeginnernews.core.article.QArticle.article;

@RequiredArgsConstructor
public class ArticleWebRepositoryImpl implements ArticleWebRepositoryCustom{
    private final JPAQueryFactory queryFactory;

    @Override
    public List<Article> findAllLimitDesc(long offset, long limit) {
        return queryFactory
                .selectFrom(article)
                .join(article.facebooks).fetchJoin()
                .offset(offset)
                .limit(limit)
                .orderBy(article.descIndex.asc())
                .fetch();
    }

}
