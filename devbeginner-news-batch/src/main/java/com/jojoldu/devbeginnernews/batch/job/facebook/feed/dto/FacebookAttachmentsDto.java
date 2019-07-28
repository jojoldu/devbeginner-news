package com.jojoldu.devbeginnernews.batch.job.facebook.feed.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@Getter
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class FacebookAttachmentsDto {

    private List<Data> data = new ArrayList<>();

    public FacebookAttachmentsDto(String url) {
        this.data = Arrays.asList(new Data(url));
    }

    public String getUrl() {
        if(CollectionUtils.isEmpty(data)) {
            return "";
        }

        return data.get(data.size()-1).getUrl();
    }

    @Getter
    @NoArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Data {
        private String url;

        public Data(String url) {
            this.url = url;
        }
    }
}
