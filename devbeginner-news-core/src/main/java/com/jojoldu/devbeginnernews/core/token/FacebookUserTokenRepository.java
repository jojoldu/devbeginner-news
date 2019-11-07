package com.jojoldu.devbeginnernews.core.token;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Created by jojoldu@gmail.com on 01/11/2019
 * Blog : http://jojoldu.tistory.com
 * Github : http://github.com/jojoldu
 */
public interface FacebookUserTokenRepository extends JpaRepository<FacebookUserToken, Long> {
    Optional<FacebookUserToken> findTopByFacebookId (String facebookId);
    Optional<FacebookUserToken> findTopByEmail (String email);
}
