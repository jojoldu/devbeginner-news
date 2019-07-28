package com.jojoldu.devbeginnernews.web.repository;

import com.jojoldu.devbeginnernews.core.article.Article;

import java.util.List;

public interface ArticleWebRepositoryCustom {
    List<Article> findAllLimitDesc(long offset, long limit);
}
