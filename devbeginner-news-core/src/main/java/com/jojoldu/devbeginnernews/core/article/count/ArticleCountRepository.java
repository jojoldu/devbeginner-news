package com.jojoldu.devbeginnernews.core.article.count;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleCountRepository extends JpaRepository <ArticleCount, Long> {
}
