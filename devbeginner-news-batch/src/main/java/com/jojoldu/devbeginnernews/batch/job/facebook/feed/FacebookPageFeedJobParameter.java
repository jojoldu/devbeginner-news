package com.jojoldu.devbeginnernews.batch.job.facebook.feed;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;

@Getter
@NoArgsConstructor
public class FacebookPageFeedJobParameter {
    private String pageId;

    @Value("#{jobParameters[pageId]}")
    public void setPageId(String pageId) {
        this.pageId = pageId;
    }
}
