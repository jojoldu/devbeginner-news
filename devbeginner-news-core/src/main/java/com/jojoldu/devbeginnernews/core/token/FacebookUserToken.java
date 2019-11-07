package com.jojoldu.devbeginnernews.core.token;

import com.jojoldu.devbeginnernews.core.common.BaseTimeEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.StringUtils;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jojoldu@gmail.com on 07/11/2019
 * Blog : http://jojoldu.tistory.com
 * Github : http://github.com/jojoldu
 */
@Getter
@NoArgsConstructor
@Entity
@Table(uniqueConstraints = {
        @UniqueConstraint(name = "uni_facebook_user_token_1", columnNames = {"facebookId"}),
        @UniqueConstraint(name = "uni_facebook_user_token_2", columnNames = {"email"})
})
public class FacebookUserToken extends BaseTimeEntity {
    private static final String DEFAULT_USER = "2139107269552358";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String facebookId;
    private String email;
    private String token;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "facebookUserToken")
    private List<FacebookPageToken> facebookPageTokens = new ArrayList<>();

    @Builder
    public FacebookUserToken(String facebookId, String email, String token) {
        this.facebookId = facebookId;
        this.email = email;
        this.token = token;
    }

    public static FacebookUserToken defaultUser(String token) {
        FacebookUserToken userToken = FacebookUserToken.builder()
                .facebookId(DEFAULT_USER)
                .email("jojoldu@gmail.com")
                .token(token)
                .build();
        userToken.addPageTokens(FacebookPageToken.defaultUser());

        return userToken;
    }

    public void addPageTokens (FacebookPageToken pageToken) {
        this.facebookPageTokens.add(pageToken);
        pageToken.setUserToken(this);
    }

    public void refreshToken(String token) {
        if(!StringUtils.isEmpty(token)){
            this.token = token;
        }
    }
}
