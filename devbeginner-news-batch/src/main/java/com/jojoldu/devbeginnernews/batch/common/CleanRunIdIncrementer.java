package com.jojoldu.devbeginnernews.batch.common;

import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersIncrementer;

/**
 * Created by jojoldu@gmail.com on 01/11/2019
 * Blog : http://jojoldu.tistory.com
 * Github : http://github.com/jojoldu
 */
public class CleanRunIdIncrementer implements JobParametersIncrementer {
    private static String RUN_ID_KEY = "run.id";
    private String key = RUN_ID_KEY;

    public void setKey(String key) {
        this.key = key;
    }

    public JobParameters getNext(JobParameters parameters) {
        JobParameters params = (parameters == null) ? new JobParameters() : parameters;
        long id = params.getLong(key, 0L) + 1;
        return new JobParametersBuilder().addLong(key, id).toJobParameters();
    }
}
