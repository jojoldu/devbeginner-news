package com.jojoldu.devbeginnernews.web.repository;

import com.jojoldu.devbeginnernews.core.article.Article;
import com.jojoldu.devbeginnernews.core.article.facebook.ArticleFacebook;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.util.List;

import static com.jojoldu.devbeginnernews.core.article.QArticle.article;
import static com.jojoldu.devbeginnernews.core.article.facebook.QArticleFacebook.articleFacebook;

@RequiredArgsConstructor
public class ArticleWebRepositoryImpl implements ArticleWebRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    @Override
    public List<Article> findAllLimitDesc(long offset, long limit) {
        return queryFactory
                .selectFrom(article)
                .leftJoin(article.facebooks).fetchJoin()
                .offset(offset)
                .limit(limit)
                .orderBy(article.descIndex.asc())
                .fetch();
    }

    @Override
    public List<ArticleFacebook> findAllMostLikes(long offset, long limit, LocalDate today) {
        return queryFactory
                .selectFrom(articleFacebook)
                .join(articleFacebook.article, article).fetchJoin()
                .where(article.registrationDate.after(today.minusYears(1)))
                .offset(offset)
                .limit(limit)
                .orderBy(articleFacebook.likes.desc())
                .fetch();
    }


}
