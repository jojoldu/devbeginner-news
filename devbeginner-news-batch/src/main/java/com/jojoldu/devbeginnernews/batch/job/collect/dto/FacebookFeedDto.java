package com.jojoldu.devbeginnernews.batch.job.collect.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import static com.fasterxml.jackson.annotation.JsonFormat.Shape.STRING;

@ToString
@Getter
@NoArgsConstructor
public class FacebookFeedDto {
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ssZ");

    private String id;
    private String message;
    @JsonProperty("created_time")
    private String createdTime;

    public LocalDateTime getCreatedTime() {
        ZonedDateTime zonedDateTime = ZonedDateTime.parse(createdTime, FORMATTER);
        ZoneId kstZone = ZoneId.of("Asia/Seoul");
        ZonedDateTime kstZoned = zonedDateTime.withZoneSameInstant(kstZone);
        return kstZoned.toLocalDateTime();
    }


}
