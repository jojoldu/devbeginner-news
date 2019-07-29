package com.jojoldu.devbeginnernews.core.utils;

import org.junit.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class UrlMatcherTest {

    @Test
    public void url과_나머지_문자열로_파싱된다() {
        //given
        String text = "http://bit.ly/2LKGnLX URL로 신청서 다운로드 후 ";

        //when
        List<String> strings = UrlMatcher.parse(text);

        //then
        assertThat(strings.get(0)).isEqualTo("http://bit.ly/2LKGnLX");
        assertThat(strings.get(1)).isEqualTo(" URL로 신청서 다운로드 후 ");
    }

    @Test
    public void url과_나머지_문자열로_파싱된다2() {
        //given
        String text = "URL로 신청서 다운로드 후 http://bit.ly/2LKGnLX";

        //when
        List<String> strings = UrlMatcher.parse(text);

        //then
        assertThat(strings.get(0)).isEqualTo("URL로 신청서 다운로드 후 ");
        assertThat(strings.get(1)).isEqualTo("http://bit.ly/2LKGnLX");
    }

    @Test
    public void url과_나머지_문자열로_파싱된다3() {
        //given
        String text = "URL로 신청서 다운로드 후 http://bit.ly/2LKGnLX 하반기에는 안될듯";

        //when
        List<String> strings = UrlMatcher.parse(text);

        //then
        assertThat(strings.get(0)).isEqualTo("URL로 신청서 다운로드 후 ");
        assertThat(strings.get(1)).isEqualTo("http://bit.ly/2LKGnLX");
        assertThat(strings.get(2)).isEqualTo(" 하반기에는 안될듯");
    }
}
