package com.jojoldu.devbeginnernews.core.article.count;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ArticleCountRepository extends JpaRepository <ArticleCount, Long> {

    Optional<ArticleCount> findTopByArticleId(Long articleId);
}
