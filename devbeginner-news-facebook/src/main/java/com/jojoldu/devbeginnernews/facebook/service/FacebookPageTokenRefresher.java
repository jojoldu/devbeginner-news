package com.jojoldu.devbeginnernews.facebook.service;

import com.jojoldu.devbeginnernews.core.token.FacebookPageToken;
import com.jojoldu.devbeginnernews.core.token.FacebookPageTokenRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

import static java.lang.String.format;
import static org.springframework.http.HttpMethod.GET;

/**
 * Created by jojoldu@gmail.com on 08/11/2019
 * Blog : http://jojoldu.tistory.com
 * Github : http://github.com/jojoldu
 */
@Slf4j
@RequiredArgsConstructor
@Component
public class FacebookPageTokenRefresher {
    private final RestTemplate restTemplate;
    private final FacebookPageTokenRepository facebookPageTokenRepository;
    private final FacebookTokenExchanger facebookTokenExchanger;

    @Transactional
    public String refresh(String pageId) {
        FacebookPageToken facebookPageToken = facebookPageTokenRepository.findByPageId(pageId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 PageId 입니다."));
        String pageToken = facebookPageToken.getToken();
        String url = format("https://graph.facebook.com/v5.0/%s/feed?limit=1&access_token=%s", pageId, pageToken);

        try {
            restTemplate.exchange(url, GET, null, new ParameterizedTypeReference<Map<String, Object>>() {});
            return pageToken;

        } catch (HttpClientErrorException hce) {
            log.error("페이스북 페이지 Token에 문제있음 statusCode={}, body={}", hce.getStatusCode(), hce.getResponseBodyAsString(), hce);
            return refresh(pageId, facebookPageToken);
        }
    }

    private String refresh(String pageId, FacebookPageToken facebookPageToken) {
        String newToken = facebookTokenExchanger.exchangeOne(pageId, facebookPageToken.getUserToken());
        facebookPageToken.refreshToken(newToken);
        return newToken;
    }
}
