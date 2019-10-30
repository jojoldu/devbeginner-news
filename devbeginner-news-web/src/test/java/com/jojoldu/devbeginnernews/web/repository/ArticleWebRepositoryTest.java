package com.jojoldu.devbeginnernews.web.repository;

import com.jojoldu.devbeginnernews.core.article.Article;
import com.jojoldu.devbeginnernews.core.article.ArticleDetailType;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.List;

import static java.time.LocalDateTime.of;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ArticleWebRepositoryTest {

    @Autowired
    private ArticleWebRepository articleWebRepository;

    @After
    public void tearDown() throws Exception {
        articleWebRepository.deleteAll();
    }

    @Test
    public void descIndex_기준으로_정렬되어_조회된다() {
        //given
        LocalDateTime dateTime1 = of(2019, 8, 1, 0, 0, 0);
        article(dateTime1);
        LocalDateTime dateTime2 = of(2019, 9, 1, 0, 0, 0);
        article(dateTime2);
        LocalDateTime dateTime3 = of(2019, 10, 1, 0, 0, 0);
        article(dateTime3);

        //when
        List<Article> articles = articleWebRepository.findAllLimitDesc(0, 10);

        //then
        assertThat(articles.size()).isEqualTo(3);
        assertThat(articles.get(0).getRegistrationDateTime()).isEqualTo(dateTime3);
        assertThat(articles.get(1).getRegistrationDateTime()).isEqualTo(dateTime2);
        assertThat(articles.get(2).getRegistrationDateTime()).isEqualTo(dateTime1);
    }

    @Test
    public void offset은_page번호가아니라_item인덱스_기준이다() {
        //given
        LocalDateTime dateTime1 = of(2019, 8, 1, 0, 0, 0);
        article(dateTime1);
        LocalDateTime dateTime2 = of(2019, 9, 1, 0, 0, 0);
        article(dateTime2);
        LocalDateTime dateTime3 = of(2019, 10, 1, 0, 0, 0);
        article(dateTime3);

        //when
        List<Article> articles = articleWebRepository.findAllLimitDesc(2, 10);

        //then
        assertThat(articles.size()).isEqualTo(1);
        assertThat(articles.get(0).getRegistrationDateTime()).isEqualTo(dateTime1);
    }

    private void article(LocalDateTime dateTime) {
        articleWebRepository.save(Article.builder()
                .title("title")
                .content("content")
                .articleType(ArticleDetailType.ETC)
                .registrationDateTime(dateTime)
                .build());
    }
}
