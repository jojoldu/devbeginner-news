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
import java.util.ArrayList;
import java.util.List;

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
    private String pageId;

    private int current = 0;
    private int page = 0;
    private int pageSize;

    private List<FacebookFeedDto> results;

    public FacebookPageItemReader(RestTemplate restTemplate, int pageSize, String pageId, String pageToken) {
        if(pageSize > 100) {
            throw new IllegalArgumentException("페이스북 조회는 한번에 100을 초과할 수 없습니다.");
        }
        this.restTemplate = restTemplate;
        this.pageSize = pageSize;
        this.pageId = pageId;
        this.targetUrl = format("https://graph.facebook.com/v4.0/%s/feed?limit=%d&access_token=%s&fields=id,message,from,created_time,attachments{url},likes.limit(1).summary(true)", pageId, pageSize, pageToken);
        log.info("targetUrl={}", targetUrl);
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
                results = new ArrayList<>();
                return;
            }

            ResponseEntity<FacebookFeedCollection> responseEntity = restTemplate.getForEntity(targetUrl, FacebookFeedCollection.class, "{url}");
            HttpStatus statusCode = responseEntity.getStatusCode();

            if (!HttpStatus.OK.equals(statusCode)) {
                log.error("페이스북 posts API 비정상 응답 statusCode={}", statusCode);
            }

            FacebookFeedCollection body = responseEntity.getBody();
            log.info(">>>>>>>> Last Feed Time= {}", body.getLastFeedTime());
            log.info(">>>>>>>> Next Url= {}", body.getNextUrl());

            results = body.getOnlyMyPosts(pageId);
            targetUrl = body.getNextUrl();
            pageSize = results.size();

        } catch (HttpClientErrorException hce) {
            log.error("페이스북 posts API 요청 실패 statusCode={}, body={}", hce.getStatusCode(), hce.getResponseBodyAsString(), hce);
            throw hce;
        } catch (UnsupportedEncodingException e) {
            log.error("Encoding Exception getNextUrl()", e);
        }
    }

}
