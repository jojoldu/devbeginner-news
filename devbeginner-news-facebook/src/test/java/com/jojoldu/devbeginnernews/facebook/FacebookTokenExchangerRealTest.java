package com.jojoldu.devbeginnernews.facebook;

import com.jojoldu.devbeginnernews.core.token.FacebookUserTokenRepository;
import com.jojoldu.devbeginnernews.facebook.service.FacebookTokenExchanger;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.HttpClientErrorException;

import static com.jojoldu.devbeginnernews.core.token.FacebookUserToken.DEFAULT_USER;

/**
 * Created by jojoldu@gmail.com on 08/11/2019
 * Blog : http://jojoldu.tistory.com
 * Github : http://github.com/jojoldu
 */

@RunWith(SpringRunner.class)
@SpringBootTest
public class FacebookTokenExchangerRealTest {

    @Autowired
    private FacebookUserTokenRepository facebookUserTokenRepository;

    @Autowired
    private FacebookTokenExchanger facebookTokenExchanger;

    @After
    public void tearDown() throws Exception {
        facebookUserTokenRepository.deleteAll();
    }

    @Test(expected = HttpClientErrorException.class)
    public void 토큰이잘못되면_HttpClientErrorException이_반환된다() {
        //given
        String userToken = "a";

        //when
        facebookTokenExchanger.exchangeAll(DEFAULT_USER, userToken);

    }
}
