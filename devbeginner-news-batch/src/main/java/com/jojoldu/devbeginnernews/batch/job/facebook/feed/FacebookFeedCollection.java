package com.jojoldu.devbeginnernews.batch.job.facebook.feed;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.jojoldu.devbeginnernews.batch.job.facebook.FacebookPagingDto;
import com.jojoldu.devbeginnernews.core.article.Article;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
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

    public LocalDateTime getLastFeedTime() {
        if(CollectionUtils.isEmpty(data)) {
            return null;
        }
        return data.get(data.size() - 1).getCreatedTime();
    }

    public boolean emptyNext() {
        return StringUtils.isEmpty(paging.getNext());
    }

    public String getNextUrl() {
        return paging.getNext();
    }

    public List<Article> toArticles(String pageId) {
        return data.stream()
                .filter(FacebookFeedDto::isNotEmpty)
                .map(d -> d.toArticle(pageId))
                .collect(Collectors.toList());
    }

}
