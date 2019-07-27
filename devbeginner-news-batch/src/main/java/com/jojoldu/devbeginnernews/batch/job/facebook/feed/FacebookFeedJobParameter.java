package com.jojoldu.devbeginnernews.batch.job.facebook.feed;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class FacebookFeedJobParameter {
    private String pageToken;
    private String pageId;
}
