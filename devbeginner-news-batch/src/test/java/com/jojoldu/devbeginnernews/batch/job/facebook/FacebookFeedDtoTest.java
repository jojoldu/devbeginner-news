package com.jojoldu.devbeginnernews.batch.job.facebook;

import com.jojoldu.devbeginnernews.batch.job.facebook.feed.FacebookFeedDto;
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

    @Test
    public void Link가_파싱된다() {
        //given
        String expected = "https://jojoldu.tistory.com/419";

        FacebookFeedDto dto = FacebookFeedDto.builder()
                .message("팀 게임 즉, 회사에서 팀 단위로 일을 하다보면 \\\"에이스가 아닌 역할\\\"에 집중할때가 있습니다.\\n\\n팀을 위해 주요한 역할을 양보한다고 볼수도 있겠습니다만,\\n개인으로 봤을때 그게 도움이 될까요?\\n\\n좋은 팀에 있다고 해서 실력있는 개발자가 아닐 수 있습니다.\\n\\n이번에 그 이야기를 한번 적어보았습니다.\\n\\n출근길에 가볍게 한번 봐주세요 :)\\n\\nhttps://jojoldu.tistory.com/419")
                .build();
        //when
        String link = dto.parseLink();

        //then
        assertThat(link).isEqualTo(expected);
    }

    @Test
    public void Content가_파싱된다() {
        //given
        String expected = "팀 게임 즉, 회사에서 팀 단위로 일을 하다보면 \\\"에이스가 아닌 역할\\\"에 집중할때가 있습니다.\\n\\n팀을 위해 주요한 역할을 양보한다고 볼수도 있겠습니다만,\\n개인으로 봤을때 그게 도움이 될까요?\\n\\n좋은 팀에 있다고 해서 실력있는 개발자가 아닐 수 있습니다.\\n\\n이번에 그 이야기를 한번 적어보았습니다.\\n\\n출근길에 가볍게 한번 봐주세요 :)\\n\\n";

        FacebookFeedDto dto = FacebookFeedDto.builder()
                .message("팀 게임 즉, 회사에서 팀 단위로 일을 하다보면 \\\"에이스가 아닌 역할\\\"에 집중할때가 있습니다.\\n\\n팀을 위해 주요한 역할을 양보한다고 볼수도 있겠습니다만,\\n개인으로 봤을때 그게 도움이 될까요?\\n\\n좋은 팀에 있다고 해서 실력있는 개발자가 아닐 수 있습니다.\\n\\n이번에 그 이야기를 한번 적어보았습니다.\\n\\n출근길에 가볍게 한번 봐주세요 :)\\n\\nhttps://jojoldu.tistory.com/419")
                .build();
        //when
        String content = dto.parseContent();

        //then
        assertThat(content).isEqualTo(expected);
    }

    @Test
    public void Title이_파싱된다() {
        //given
        String expected = "팀 게임 즉, 회사에서 팀 단위로 일을 하다보면 \\\"에이스가 아닌 역할\\\"에 집중할때가 있습니다.";

        FacebookFeedDto dto = FacebookFeedDto.builder()
                .message("팀 게임 즉, 회사에서 팀 단위로 일을 하다보면 \\\"에이스가 아닌 역할\\\"에 집중할때가 있습니다.\\n\\n팀을 위해 주요한 역할을 양보한다고 볼수도 있겠습니다만,\\n개인으로 봤을때 그게 도움이 될까요?\\n\\n좋은 팀에 있다고 해서 실력있는 개발자가 아닐 수 있습니다.\\n\\n이번에 그 이야기를 한번 적어보았습니다.\\n\\n출근길에 가볍게 한번 봐주세요 :)\\n\\nhttps://jojoldu.tistory.com/419")
                .build();
        //when
        String title = dto.parseTitle();

        //then
        assertThat(title).isEqualTo(expected);
    }
}
