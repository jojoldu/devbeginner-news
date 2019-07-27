package com.jojoldu.devbeginnernews.batch.job.collect;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CollectFacebookJobParameter {
    private String pageToken;
    private String pageId;
}
