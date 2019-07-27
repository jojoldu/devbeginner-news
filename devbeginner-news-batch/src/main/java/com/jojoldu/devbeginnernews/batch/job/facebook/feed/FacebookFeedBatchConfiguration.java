package com.jojoldu.devbeginnernews.batch.job.facebook.feed;

import com.jojoldu.devbeginnernews.batch.job.facebook.FacebookRestTemplate;
import com.jojoldu.devbeginnernews.core.article.ArticleRepository;
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

import javax.persistence.EntityManagerFactory;

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
                    FacebookFeedCollection feedCollection = facebookRestTemplate.posts(pageId, jobParameter.getPageToken());
                    articleRepository.saveAll(feedCollection.toArticles(pageId));

                    streamFeed(pageId, feedCollection);

                    return RepeatStatus.FINISHED;
                })
                .build();
    }

    private void streamFeed(String pageId, FacebookFeedCollection feedCollection) {
        while (!feedCollection.emptyNext()) {
            FacebookFeedCollection next = facebookRestTemplate.posts(feedCollection.getNextUrl());
            articleRepository.saveAll(next.toArticles(pageId));
        }
    }


}
