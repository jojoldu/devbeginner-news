package com.jojoldu.devbeginnernews.facebook.service.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * Created by jojoldu@gmail.com on 08/11/2019
 * Blog : http://jojoldu.tistory.com
 * Github : http://github.com/jojoldu
 */

@ToString
@Getter
@NoArgsConstructor
public class FacebookErrorResponse {
    private Error error;

    public FacebookErrorResponse(Error error) {
        this.error = error;
    }

    public boolean isTokenError() {
        return error.isTokenError();
    }

    @ToString
    @Getter
    @NoArgsConstructor
    public static class Error {
        private String message;
        private String type;
        private long code;

        @JsonProperty("error_subcode")
        private long errorSubCode;
        @JsonProperty("fbtrace_id")
        private String fbtraceId;

        @Builder
        public Error(String message, String type, long code, long errorSubCode, String fbtraceId) {
            this.message = message;
            this.type = type;
            this.code = code;
            this.errorSubCode = errorSubCode;
            this.fbtraceId = fbtraceId;
        }

        public boolean isTokenError() {
            return code == 190;
        }
    }
}
