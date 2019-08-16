package com.jojoldu.devbeginnernews.core.article.facebook;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ArticleFacebookRepository extends JpaRepository <ArticleFacebook, Long> {

    Optional<ArticleFacebook> findTopByArticleId(Long articleId);
    Optional<ArticleFacebook> findByPostsId(String postsId);
}
