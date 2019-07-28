package com.jojoldu.devbeginnernews.batch.job.facebook.feed.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@NoArgsConstructor
public class FacebookLikeDto {

    private Summary summary;

    public FacebookLikeDto(Summary summary) {
        this.summary = summary;
    }

    public FacebookLikeDto(long totalCount) {
        this.summary = new Summary(totalCount);
    }

    public long getTotalCount() {
        return summary.getTotalCount();
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    @Getter
    @NoArgsConstructor
    public static class Summary {
        @JsonProperty("total_count")
        private long totalCount;

        public Summary(long totalCount) {
            this.totalCount = totalCount;
        }
    }


}
