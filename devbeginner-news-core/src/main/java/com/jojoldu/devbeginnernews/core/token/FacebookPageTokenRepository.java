package com.jojoldu.devbeginnernews.core.token;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Created by jojoldu@gmail.com on 01/11/2019
 * Blog : http://jojoldu.tistory.com
 * Github : http://github.com/jojoldu
 */
public interface FacebookPageTokenRepository extends JpaRepository<FacebookPageToken, Long> {

    Optional<FacebookPageToken> findByPageId (String pageId);
}
