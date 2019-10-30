package com.jojoldu.devbeginnernews.batch.job.facebook.feed.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.util.StringUtils;

@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@NoArgsConstructor
public class FacebookFromDto {

    private String name;
    private String id;

    @Builder
    public FacebookFromDto(String name, String id) {
        this.name = name;
        this.id = id;
    }

    public boolean isMyPost (String pageId) {
        if(StringUtils.isEmpty(pageId)) {
            return false;
        }

        return pageId.equals(id);
    }

}
