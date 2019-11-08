package com.jojoldu.devbeginnernews.batch.job.facebook.feed;

import com.jojoldu.devbeginnernews.batch.common.CleanRunIdIncrementer;
import com.jojoldu.devbeginnernews.batch.job.facebook.feed.dto.FacebookFeedDto;
import com.jojoldu.devbeginnernews.core.article.Article;
import com.jojoldu.devbeginnernews.core.article.facebook.ArticleFacebookRepository;
import com.jojoldu.devbeginnernews.core.token.FacebookPageToken;
import com.jojoldu.devbeginnernews.core.token.FacebookPageTokenRepository;
import com.jojoldu.devbeginnernews.facebook.service.FacebookPageTokenRefresher;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import javax.persistence.EntityManagerFactory;

@Slf4j
@RequiredArgsConstructor
@Configuration
public class FacebookPageFeedBatchConfiguration {

    public static final String JOB_NAME = "facebookPageFeedBatch";
    public static final String BEAN_PREFIX = JOB_NAME + "_";

    private final EntityManagerFactory emf;
    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final FacebookFeedRestTemplate facebookRestTemplate;
    private final FacebookPageTokenRefresher facebookPageTokenRefresher;
    private final ArticleFacebookRepository articleFacebookRepository;
    private final FacebookPageFeedJobParameter jobParameter;

    private int chunkSize;

    @Value("${chunkSize:100}")
    public void setChunkSize(int chunkSize) {
        this.chunkSize = chunkSize;
    }

    @Bean(BEAN_PREFIX+"jobParameter")
    @JobScope
    public FacebookPageFeedJobParameter jobParameter() {
        return new FacebookPageFeedJobParameter();
    }

    @Bean(BEAN_PREFIX + "job")
    public Job job() {
        return jobBuilderFactory.get(JOB_NAME)
                .incrementer(new CleanRunIdIncrementer())
                .start(step())
                .build();
    }

    @Bean(BEAN_PREFIX + "step")
    public Step step() {
        return stepBuilderFactory.get(BEAN_PREFIX+ "step")
                .<FacebookFeedDto, Article>chunk(chunkSize)
                .reader(reader())
                .processor(processor())
                .writer(writer())
                .build();
    }

    @Bean(BEAN_PREFIX + "reader")
    @StepScope
    public FacebookPageItemReader reader() {
        String pageToken = facebookPageTokenRefresher.refresh(jobParameter.getPageId());

        return new FacebookPageItemReader(
                facebookRestTemplate,
                chunkSize,
                jobParameter.getPageId(),
                pageToken);
    }

    @Bean(BEAN_PREFIX + "processor")
    @StepScope
    public ItemProcessor<FacebookFeedDto, Article> processor() {
        return item -> {
            String pageId = jobParameter.getPageId();
            Article article = item.toArticle(pageId);
            return articleFacebookRepository.findByPostsId(article.getPostsId())
                    .map(a -> a.updateArticle(article.getTitle(), article.getContent(), article.getLikes()))
                    .orElse(article);
        };
    }

    @Bean(BEAN_PREFIX + "writer")
    public JpaItemWriter<Article> writer() {
        JpaItemWriter<Article> itemWriter = new JpaItemWriter<>();
        itemWriter.setEntityManagerFactory(emf);
        return itemWriter;
    }

}
