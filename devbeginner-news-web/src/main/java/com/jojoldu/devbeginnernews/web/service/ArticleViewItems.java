package com.jojoldu.devbeginnernews.web.service;

import com.jojoldu.devbeginnernews.core.article.Article;
import com.jojoldu.devbeginnernews.core.article.count.ArticleCount;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ArticleViewItems {
    private final List<Article> articles;
    private final Map<Long, Long> articleCounts;
    @Getter private List<ArticleViewItem> articleItems = new ArrayList<>();

    public ArticleViewItems(List<Article> articles, List<ArticleCount> articleCounts) {
        this.articles = articles;
        this.articleCounts = articleCounts.stream()
                .collect(Collectors.toMap(ArticleCount::getArticleId, ArticleCount::getCount));
        this.map();
    }

    private void map() {
        for (Article article : articles) {
            articleItems.add(new ArticleViewItem(article, articleCounts.getOrDefault(article.getId(), 0L)));
        }
    }
}
