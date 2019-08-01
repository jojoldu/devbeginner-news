package com.jojoldu.devbeginnernews.web.repository;

import com.jojoldu.devbeginnernews.core.article.Article;
import com.jojoldu.devbeginnernews.core.article.facebook.ArticleFacebook;

import java.time.LocalDate;
import java.util.List;

public interface ArticleWebRepositoryCustom {
    List<Article> findAllLimitDesc(long offset, long limit);

    List<ArticleFacebook> findAllMostLikes(long offset, long limit, LocalDate today);
}
