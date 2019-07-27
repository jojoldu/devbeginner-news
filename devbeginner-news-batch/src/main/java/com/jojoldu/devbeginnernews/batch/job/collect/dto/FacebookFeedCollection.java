package com.jojoldu.devbeginnernews.batch.job.collect.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.jojoldu.devbeginnernews.core.article.Article;
import com.jojoldu.devbeginnernews.core.article.facebook.ArticleFacebook;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@NoArgsConstructor
public class FacebookFeedCollection {
    private List<FacebookFeedDto> data;
    private FacebookPagingDto paging;

    public FacebookFeedCollection(List<FacebookFeedDto> data, FacebookPagingDto paging) {
        this.data = data;
        this.paging = paging;
    }

    public boolean emptyNext() {
        return StringUtils.isEmpty(paging.getNext());
    }

    public String getNextUrl() {
        return paging.getNext();
    }

    public List<Article> toArticles(String pageId) {
        return data.stream()
                .map(d -> d.toArticle(pageId))
                .collect(Collectors.toList());
    }

}
