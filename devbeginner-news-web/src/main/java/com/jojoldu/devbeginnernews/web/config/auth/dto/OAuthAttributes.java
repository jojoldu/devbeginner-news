package com.jojoldu.devbeginnernews.web.config.auth.dto;

import com.jojoldu.devbeginnernews.core.user.Role;
import com.jojoldu.devbeginnernews.core.user.User;
import lombok.Builder;
import lombok.Getter;

import java.util.Map;

@Getter
public class OAuthAttributes {
    private Map<String, Object> attributes;
    private String nameAttributeKey;
    private String name;
    private String login;
    private String email;
    private String profileImage;

    @Builder
    public OAuthAttributes(Map<String, Object> attributes, String nameAttributeKey, String name, String login, String email, String profileImage) {
        this.attributes = attributes;
        this.nameAttributeKey = nameAttributeKey;
        this.name = name;
        this.login = login;
        this.email = email;
        this.profileImage = profileImage;
    }

    public static OAuthAttributes of(String userNameAttributeName, Map<String, Object> attributes) {
        return OAuthAttributes.builder()
                .name((String) attributes.get("name"))
                .login((String) attributes.get("login"))
                .email((String) attributes.get("email"))
                .profileImage((String) attributes.get("https://avatars0.githubusercontent.com/u/7760496?v=4"))
                .attributes(attributes)
                .nameAttributeKey(userNameAttributeName)
                .build();
    }

    public User toEntity() {
        return User.builder()
                .name(name)
                .email(email)
                .username(login)
                .profileImage(profileImage)
                .role(Role.USER)
                .build();
    }
}
