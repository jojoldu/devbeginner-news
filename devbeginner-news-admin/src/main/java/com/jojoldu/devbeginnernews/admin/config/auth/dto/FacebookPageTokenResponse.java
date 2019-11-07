package com.jojoldu.devbeginnernews.admin.config.auth.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Created by jojoldu@gmail.com on 07/11/2019
 * Blog : http://jojoldu.tistory.com
 * Github : http://github.com/jojoldu
 */

@Getter
@Setter
@NoArgsConstructor
public class FacebookPageTokenResponse {

    private String id;

    @JsonProperty("access_token")
    private String accessToken;
}
