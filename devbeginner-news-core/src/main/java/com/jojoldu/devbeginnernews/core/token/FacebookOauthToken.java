package com.jojoldu.devbeginnernews.core.token;

/**
 * Created by jojoldu@gmail.com on 01/11/2019
 * Blog : http://jojoldu.tistory.com
 * Github : http://github.com/jojoldu
 */
import com.jojoldu.devbeginnernews.core.common.BaseTimeEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.StringUtils;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Getter
@NoArgsConstructor
@Entity
@Table(uniqueConstraints = {
        @UniqueConstraint(name = "uni_facebook_oauth_token_1", columnNames = {"pageId"})
})
public class FacebookOauthToken extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String pageId;
    private String token;

    @Builder
    public FacebookOauthToken(String pageId, String token) {
        this.pageId = pageId;
        this.token = token;
    }

    public void updateToken (String token) {
        if(!StringUtils.isEmpty(token)){
            this.token = token;
        }
    }
}
