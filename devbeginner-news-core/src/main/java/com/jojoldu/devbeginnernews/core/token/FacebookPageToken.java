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
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Getter
@NoArgsConstructor
@Entity
@Table(uniqueConstraints = {
        @UniqueConstraint(name = "uni_facebook_page_token_1", columnNames = {"pageId"})
})
public class FacebookPageToken extends BaseTimeEntity {
    private static final String DEFAULT_PAGE = "845737092205327";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String pageId;
    private String token;

    @ManyToOne
    @JoinColumn(name = "user_token_id", foreignKey = @ForeignKey(name = "fk_page_token_user_token"))
    private FacebookUserToken facebookUserToken;

    @Builder
    public FacebookPageToken(String pageId, String token) {
        this.pageId = pageId;
        this.token = token;
    }

    public static FacebookPageToken defaultUser () {
        return FacebookPageToken.builder()
                .pageId(DEFAULT_PAGE)
                .build();
    }

    public void setUserToken(FacebookUserToken userToken) {
        this.facebookUserToken = userToken;
    }

    public void refreshToken (String token) {
        if(!StringUtils.isEmpty(token)){
            this.token = token;
        }
    }
}
