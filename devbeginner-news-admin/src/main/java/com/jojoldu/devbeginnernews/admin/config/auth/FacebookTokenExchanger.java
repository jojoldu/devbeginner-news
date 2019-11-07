package com.jojoldu.devbeginnernews.admin.config.auth;

import com.jojoldu.devbeginnernews.admin.config.auth.dto.FacebookPageTokenResponse;
import com.jojoldu.devbeginnernews.core.token.FacebookPageToken;
import com.jojoldu.devbeginnernews.core.token.FacebookUserToken;
import com.jojoldu.devbeginnernews.core.token.FacebookUserTokenRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static com.jojoldu.devbeginnernews.core.token.FacebookUserToken.defaultUser;
import static java.lang.String.format;

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
    private final FacebookUserTokenRepository facebookUserTokenRepository;

    @Transactional
    public void exchange (String userId, String userToken) {
        FacebookUserToken facebookUserToken = facebookUserTokenRepository.findTopByFacebookId(userId)
                .orElseGet(() -> facebookUserTokenRepository.save(defaultUser(userToken)));

        List<FacebookPageToken> facebookPageTokens = facebookUserToken.getFacebookPageTokens();

        for (FacebookPageToken facebookPageToken : facebookPageTokens) {
            String pageId = facebookPageToken.getPageId();
            String url = format("https://graph.facebook.com/v5.0/%s?access_token=%s&fields=access_token", pageId, userToken);
            FacebookPageTokenResponse responseBody = execute(url);
            facebookPageToken.refreshToken(responseBody.getAccessToken());
        }
    }

    private FacebookPageTokenResponse execute(String url) {
        try{
            ResponseEntity<FacebookPageTokenResponse> response = restTemplate.getForEntity(url, FacebookPageTokenResponse.class);
            HttpStatus statusCode = response.getStatusCode();

            if(!HttpStatus.OK.equals(statusCode)) {
                log.error("페이스북 페이지 AccessToken 비정상 응답 statusCode={}", statusCode);
                throw new HttpClientErrorException(statusCode, "페이스북 페이지 AccessToken 비정상 응답");
            }

            return response.getBody();

        } catch (HttpClientErrorException hce) {
            log.error("페이스북 페이지 AccessToken 요청 실패 statusCode={}, body={}", hce.getStatusCode(), hce.getResponseBodyAsString(), hce);
            throw hce;
        }
    }


}
