package com.jojoldu.devbeginnernews;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@EnableBatchProcessing
@Configuration
@EnableAutoConfiguration
@ComponentScan
public class TestJobConfiguration {

}