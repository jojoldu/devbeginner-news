package com.jojoldu.devbeginnernews.batch.job.facebook;

import com.jojoldu.devbeginnernews.batch.job.facebook.feed.FacebookFeedCollection;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Slf4j
@RequiredArgsConstructor
@Component
public class FacebookRestTemplate {
    private final RestTemplate restTemplate;

    public FacebookFeedCollection feed(String pageId, String pageToken) {
        final String URL = String.format("https://graph.facebook.com/v3.3/%s/feed?limit=100&access_token=%s", pageId, pageToken);
        return feed(URL);
    }

    public FacebookFeedCollection feed(String url) {
        try {
            ResponseEntity<FacebookFeedCollection> responseEntity = restTemplate.getForEntity(url, FacebookFeedCollection.class);
            HttpStatus statusCode = responseEntity.getStatusCode();

            if(!HttpStatus.OK.equals(statusCode)) {
                log.error("페이스북 posts API 비정상 응답 statusCode={}", statusCode);
            }

            return responseEntity.getBody();
        } catch (HttpClientErrorException hce) {
            log.error("페이스북 posts API 요청 실패 statusCode={}, body={}", hce.getStatusCode(), hce.getResponseBodyAsString(), hce);
            throw hce;
        }
    }



}
