package com.jojoldu.devbeginnernews.facebook.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jojoldu.devbeginnernews.core.token.FacebookPageToken;
import com.jojoldu.devbeginnernews.core.token.FacebookUserToken;
import com.jojoldu.devbeginnernews.core.token.FacebookUserTokenRepository;
import com.jojoldu.devbeginnernews.facebook.service.dto.FacebookErrorResponse;
import com.jojoldu.devbeginnernews.facebook.service.dto.FacebookPageTokenResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

import static com.jojoldu.devbeginnernews.core.token.FacebookUserToken.defaultUser;
import static java.lang.String.format;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpStatus.OK;

/**
 * Created by jojoldu@gmail.com on 07/11/2019
 * Blog : http://jojoldu.tistory.com
 * Github : http://github.com/jojoldu
 */

@Slf4j
@RequiredArgsConstructor
@Component
public class FacebookTokenExchanger {
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;
    private final FacebookUserTokenRepository facebookUserTokenRepository;

    @Transactional
    public void exchangeAll (String userId, String userToken) {
        FacebookUserToken facebookUserToken = facebookUserTokenRepository.findTopByFacebookId(userId)
                .orElseGet(() -> facebookUserTokenRepository.save(defaultUser(userToken)));

        List<FacebookPageToken> facebookPageTokens = facebookUserToken.getFacebookPageTokens();

        for (FacebookPageToken facebookPageToken : facebookPageTokens) {
            facebookPageToken.refreshToken(exchangeOne(facebookPageToken.getPageId(), userToken));
        }
    }

    public String exchangeOne(String pageId, String userToken) {
        String url = format("https://graph.facebook.com/v5.0/%s?access_token=%s&fields=access_token", pageId, userToken);
        return execute(url).getAccessToken();
    }

    private FacebookPageTokenResponse execute(String url) {
        try{
            ResponseEntity<Map<String, Object>> response = restTemplate.exchange(url, GET, null, new ParameterizedTypeReference<Map<String, Object>>() {});

            validate(response);

            return objectMapper.convertValue(response.getBody(), FacebookPageTokenResponse.class) ;

        } catch (HttpClientErrorException hce) {
            log.error("페이스북 페이지 AccessToken 요청 실패 statusCode={}, body={}", hce.getStatusCode(), hce.getResponseBodyAsString(), hce);
            throw hce;
        }
    }

    private void validate(ResponseEntity<Map<String, Object>> response) {
        HttpStatus statusCode = response.getStatusCode();

        if(!OK.equals(statusCode)) {
            FacebookErrorResponse errorResponse = objectMapper.convertValue(response.getBody(), FacebookErrorResponse.class);
            log.error("페이스북 페이지 AccessToken 비정상 응답 statusCode={}, response={}", statusCode, errorResponse);
            throw new HttpClientErrorException(statusCode, "페이스북 페이지 AccessToken 비정상 응답");
        }
    }


}
