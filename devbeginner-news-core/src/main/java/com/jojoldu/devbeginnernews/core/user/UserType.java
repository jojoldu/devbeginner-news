package com.jojoldu.devbeginnernews.core.user;

import lombok.RequiredArgsConstructor;

/**
 * Created by jojoldu@gmail.com on 06/11/2019
 * Blog : http://jojoldu.tistory.com
 * Github : http://github.com/jojoldu
 */
@RequiredArgsConstructor
public enum UserType {

    FACEBOOK ("페이스북"),
    GITHUB ("깃허브");

    private final String title;
}
