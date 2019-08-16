package com.jojoldu.devbeginnernews.batch.job.facebook.feed;

import com.jojoldu.devbeginnernews.batch.job.facebook.FacebookRestTemplate;
import com.jojoldu.devbeginnernews.batch.job.facebook.feed.dto.FacebookFeedCollection;
import com.jojoldu.devbeginnernews.core.article.Article;
import com.jojoldu.devbeginnernews.core.article.ArticleRepository;
import com.jojoldu.devbeginnernews.core.article.facebook.ArticleFacebook;
import com.jojoldu.devbeginnernews.core.article.facebook.ArticleFacebookRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManagerFactory;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Configuration
public class FacebookFeedBatchConfiguration {

    public static final String JOB_NAME = "facebookFeedBatch";
    public static final String BEAN_PREFIX = JOB_NAME + "_";

    private final FacebookFeedJobParameter jobParameter;
    private final EntityManagerFactory emf;
    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final FacebookRestTemplate facebookRestTemplate;
    private final ArticleRepository articleRepository;
    private final ArticleFacebookRepository articleFacebookRepository;

    @Value("${chunkSize:1000}")
    private int chunkSize;

    @Bean
    @JobScope
    public FacebookFeedJobParameter collectFacebookJobParameter() {
        return new FacebookFeedJobParameter();
    }

    @Bean(BEAN_PREFIX + "job")
    public Job job() {
        return jobBuilderFactory.get(JOB_NAME)
                .preventRestart()
                .start(saveArticleStep())
                .build();
    }

    @Bean(BEAN_PREFIX + "saveArticleStep")
    public Step saveArticleStep() {
        return stepBuilderFactory.get("saveArticleStep")
                .tasklet((contribution, chunkContext) -> {
                    String pageId = jobParameter.getPageId();
                    FacebookFeedCollection feedCollection = facebookRestTemplate.feed(pageId, jobParameter.getPageToken());
                    saveOrUpdateArticles(pageId, feedCollection);

                    streamFeed(pageId, feedCollection.getNextUrl());

                    return RepeatStatus.FINISHED;
                })
                .build();
    }

    private void streamFeed(String pageId, String url) throws UnsupportedEncodingException {
        if(StringUtils.isEmpty(url)) {
            return;
        }

        String nextUrl = url;
        while (true) {
            FacebookFeedCollection next = facebookRestTemplate.feed(nextUrl);
            saveOrUpdateArticles(pageId, next);
            nextUrl = next.getNextUrl();

            log.info(">>>>>>>> Last Feed Time= {}", next.getLastFeedTime());
            log.info(">>>>>>>> Next Url= {}", nextUrl);

            if(next.emptyNext()) {
                break;
            }
        }
    }

    private void saveOrUpdateArticles(String pageId, FacebookFeedCollection feedCollection) {
        List<Article> newArticles = feedCollection.toArticles(pageId);

        for (Article article : newArticles) {
            String postsId = article.getPostsId();
            Optional<ArticleFacebook> existFacebook = articleFacebookRepository.findByPostsId(postsId);

            if(existFacebook.isPresent()) {
                Article existArticle = existFacebook.get().getArticle();
                existArticle.update(article.getTitle(), article.getContent(), article.getLikes());
                articleRepository.save(existArticle);
            } else {
                articleRepository.save(article);
            }
        }

    }


}
