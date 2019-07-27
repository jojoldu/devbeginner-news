package com.jojoldu.devbeginnernews.batch.job.collect.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
@NoArgsConstructor
public class FacebookPagingDto {
    private String next;
}
