package com.jojoldu.devbeginnernews.batch.job.facebook.feed;

import com.jojoldu.devbeginnernews.batch.job.facebook.feed.dto.FacebookFeedCollection;
import com.jojoldu.devbeginnernews.batch.job.facebook.feed.dto.FacebookFeedDto;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemReader;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.io.UnsupportedEncodingException;

import static java.lang.String.format;

/**
 * Created by jojoldu@gmail.com on 29/10/2019
 * Blog : http://jojoldu.tistory.com
 * Github : http://github.com/jojoldu
 */
@Slf4j
@Getter
public class FacebookPageItemReader implements ItemReader<FacebookFeedDto> {
    private final RestTemplate restTemplate;
    private String targetUrl;

    private int current = 0;
    private int page = 0;
    private int pageSize;

    private FacebookFeedCollection results;

    public FacebookPageItemReader(RestTemplate restTemplate, int pageSize, String pageId, String pageToken) {
        this.restTemplate = restTemplate;
        this.pageSize = pageSize;
        this.targetUrl = format("https://graph.facebook.com/v3.3/%s/feed?limit=%d&access_token=%s&fields=id,message,created_time,attachments{url},likes.limit(1).summary(true)", pageId, pageSize, pageToken);
    }

    @Override
    public FacebookFeedDto read() throws Exception {

        if (results == null || current >= pageSize) {

            log.debug("Reading page " + getPage());

            doReadPage();
            page++;
            if (current >= pageSize) {
                current = 0;
            }

        }

        int next = current++;
        if (next < results.size()) {
            return results.get(next);
        } else {
            return null;
        }
    }

    private void doReadPage() {
        try {
            if (StringUtils.isEmpty(targetUrl)) {
                results = new FacebookFeedCollection();
                return;
            }

            ResponseEntity<FacebookFeedCollection> responseEntity = restTemplate.getForEntity(targetUrl, FacebookFeedCollection.class, "{url}");
            HttpStatus statusCode = responseEntity.getStatusCode();

            if (!HttpStatus.OK.equals(statusCode)) {
                log.error("페이스북 posts API 비정상 응답 statusCode={}", statusCode);
            }

            log.info(">>>>>>>> Last Feed Time= {}", responseEntity.getBody().getLastFeedTime());
            log.info(">>>>>>>> Next Url= {}", responseEntity.getBody().getNextUrl());

            results = responseEntity.getBody();
            targetUrl = results.getNextUrl();
        } catch (HttpClientErrorException hce) {
            log.error("페이스북 posts API 요청 실패 statusCode={}, body={}", hce.getStatusCode(), hce.getResponseBodyAsString(), hce);
            throw hce;
        } catch (UnsupportedEncodingException e) {
            log.error("Encoding Exception getNextUrl()", e);
        }
    }

}
