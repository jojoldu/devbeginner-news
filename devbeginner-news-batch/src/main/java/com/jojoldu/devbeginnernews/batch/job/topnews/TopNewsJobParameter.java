package com.jojoldu.devbeginnernews.batch.job.topnews;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;

import java.time.LocalDate;

import static com.jojoldu.devbeginnernews.core.utils.LocalDateUtils.toLocalDate;

@Getter
@NoArgsConstructor
public class TopNewsJobParameter {
    private LocalDate startDate;
    private LocalDate endDate;
    private String pageId;

    @Value("#{jobParameters[startDate]}")
    public void setStartDate(String startDate) {
        this.startDate = toLocalDate(startDate);
    }

    @Value("#{jobParameters[startDate]}")
    public void setEndDate(String endDate) {
        this.endDate = toLocalDate(endDate);
    }
}
