package com.jojoldu.devbeginnernews.batch.job.facebook;

import com.jojoldu.devbeginnernews.batch.job.facebook.feed.dto.FacebookFeedCollection;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import static java.lang.String.format;

@Slf4j
@RequiredArgsConstructor
@Component
public class FacebookRestTemplate {
    private final RestTemplate restTemplate;

    public FacebookFeedCollection feed(String pageId, int pageSize, String pageToken) {
        return feed(format("https://graph.facebook.com/v4.0/%s/feed?limit=%d&access_token=%s&fields=id,message,from,created_time,attachments{url},likes.limit(1).summary(true)", pageId, pageSize, pageToken));
    }

    public FacebookFeedCollection feed(String url) {
        try {
            ResponseEntity<FacebookFeedCollection> responseEntity = getForEntity(url, "{url}");
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

    private ResponseEntity<FacebookFeedCollection> getForEntity(String url, String uriVariables) {
        if(StringUtils.isEmpty(uriVariables)) {
            return restTemplate.getForEntity(url, FacebookFeedCollection.class);
        }

        return restTemplate.getForEntity(url, FacebookFeedCollection.class, uriVariables);
    }


}
