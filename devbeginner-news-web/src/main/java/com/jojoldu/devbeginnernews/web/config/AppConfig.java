package com.jojoldu.devbeginnernews.web.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.jojoldu.devbeginnernews.core.article.ArticleDetailType;
import com.jojoldu.devbeginnernews.core.common.type.EnumMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.web.client.RestTemplate;

import static java.time.Duration.ofSeconds;

@Configuration
@RequiredArgsConstructor
public class AppConfig {

    @Bean
    @Primary
    public ObjectMapper objectMapper() {
        return new Jackson2ObjectMapperBuilder()
                .featuresToDisable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
                .modules(new JavaTimeModule())
                .timeZone("Asia/Seoul")
                .build();
    }

    @Bean
    @Primary
    public RestTemplate restTemplate() {
        return new RestTemplateBuilder()
                .setReadTimeout(ofSeconds(60))
                .setConnectTimeout(ofSeconds(60))
                .build();
    }

    @Bean
    public EnumMapper enumMapper() {
        EnumMapper enumMapper = new EnumMapper();
        enumMapper.put(ArticleDetailType.class);
        return enumMapper;
    }
}
