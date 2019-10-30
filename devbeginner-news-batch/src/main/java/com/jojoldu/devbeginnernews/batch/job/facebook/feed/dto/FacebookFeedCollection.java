package com.jojoldu.devbeginnernews.batch.job.facebook.feed.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.jojoldu.devbeginnernews.batch.job.facebook.FacebookPagingDto;
import com.jojoldu.devbeginnernews.core.article.Article;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static java.net.URLDecoder.decode;
import static lombok.AccessLevel.PRIVATE;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@NoArgsConstructor
public class FacebookFeedCollection {
    private List<FacebookFeedDto> data = new ArrayList<>();
    private FacebookPagingDto paging;

    public FacebookFeedCollection(List<FacebookFeedDto> data, FacebookPagingDto paging) {
        this.data = data;
        this.paging = paging;
    }

    public List<FacebookFeedDto> getOnlyMyPosts (String pageId) {
        return data.stream()
                .filter(d -> d.isMyPost(pageId))
                .filter(FacebookFeedDto::isNotEmpty)
                .collect(Collectors.toList());
    }

    public LocalDateTime getLastFeedTime() {
        if(CollectionUtils.isEmpty(data)) {
            return LocalDateTime.now();
        }
        return data.get(data.size() - 1).parseCreatedTime();
    }

    public boolean emptyNext() {
        return StringUtils.isEmpty(paging.getNext());
    }

    public String getNextUrl() throws UnsupportedEncodingException {
        if(StringUtils.isEmpty(paging.getNext())) {
            return "";
        }

        return decode(paging.getNext(), "UTF-8");
    }

    public List<Article> toArticles(String pageId) {
        return data.stream()
                .filter(FacebookFeedDto::isNotEmpty)
                .map(d -> d.toArticle(pageId))
                .collect(Collectors.toList());
    }

}
