package com.jojoldu.devbeginnernews.admin;

import com.jojoldu.devbeginnernews.core.article.Article;
import com.jojoldu.devbeginnernews.core.article.ArticleDetailType;
import com.jojoldu.devbeginnernews.core.article.ArticleRepository;
import com.jojoldu.devbeginnernews.core.article.facebook.ArticleFacebookRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ArticleAdminServiceTest {

    @Autowired
    ArticleRepository articleRepository;

    @Autowired
    ArticleAdminService articleAdminService;

    @Autowired
    ArticleFacebookRepository articleFacebookRepository;

    @Test
    public void name() {
        //given
        articleRepository.save(Article.builder()
                .title("a")
                .articleType(ArticleDetailType.ETC)
                .content("a")
                .link("a")
                .registrationDateTime(LocalDateTime.now())
                .build());
        //when
        articleAdminService.saveChilds(1L);

        //then
        assertThat(articleFacebookRepository.findAll().size()).isEqualTo(1);

    }
}
