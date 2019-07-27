package com.jojoldu.devbeginnernews.batch.job.facebook;

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

    private String id;
    private Likes likes;

    public FacebookLikeDto(String id, Likes likes) {
        this.id = id;
        this.likes = likes;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    @Getter
    @NoArgsConstructor
    public static class Likes {
        private Summary summary;

        public Likes(Summary summary) {
            this.summary = summary;
        }
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
