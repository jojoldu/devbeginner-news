package com.jojoldu.devbeginnernews.batch.job.collect.dto;

import org.junit.Test;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import static org.assertj.core.api.Assertions.assertThat;

public class FacebookFeedDtoTest {

    @Test
    public void created_time이_정상적으로_변환된다() throws IOException {
        //given
        ZonedDateTime expected = ZonedDateTime.of(2019,7,26,0,27,8,0, ZoneId.of("UTC"));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ssZ");

        //when
        ZonedDateTime zonedDateTime = ZonedDateTime.parse("2019-07-26T00:27:08+0000", formatter);

        //then
        assertThat(zonedDateTime).isEqualTo(expected);
    }

    @Test
    public void ZonedDateTime_UTC가_LocalDateTime_KST로_변환된다() throws IOException {
        //given
        LocalDateTime expected = LocalDateTime.of(2019,7,26,9,27,8);
        ZonedDateTime zonedDateTime = ZonedDateTime.of(2019,7,26,0,27,8,0, ZoneId.of("UTC"));

        //when
        ZoneId kstZone = ZoneId.of("Asia/Seoul");
        ZonedDateTime kstZoned = zonedDateTime.withZoneSameInstant(kstZone);
        LocalDateTime result = kstZoned.toLocalDateTime();

        //then
        assertThat(result).isEqualTo(expected);
    }
}
