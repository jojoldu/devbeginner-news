package com.jojoldu.devbeginnernews;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

import static org.springframework.context.annotation.FilterType.REGEX;

@EnableBatchProcessing
@Configuration
@EnableAutoConfiguration
@ComponentScan(excludeFilters = @Filter(type = REGEX, pattern = "com.jojoldu.devbeginnernews.batch.job.*"))
public class TestJobConfiguration {

}