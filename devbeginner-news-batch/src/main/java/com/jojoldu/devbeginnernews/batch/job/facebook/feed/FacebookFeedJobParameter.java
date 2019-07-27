package com.jojoldu.devbeginnernews.batch.job.facebook.feed;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;

@Getter
@NoArgsConstructor
public class FacebookFeedJobParameter {
    @Value("#{jobParameters[pageToken]}")
    private String pageToken;
    @Value("#{jobParameters[pageId]}")
    private String pageId;
}
