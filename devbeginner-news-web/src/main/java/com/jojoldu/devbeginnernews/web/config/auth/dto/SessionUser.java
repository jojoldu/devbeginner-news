package com.jojoldu.devbeginnernews.web.config.auth.dto;

import com.jojoldu.devbeginnernews.core.user.User;
import lombok.Getter;

import java.io.Serializable;

@Getter
public class SessionUser implements Serializable {
    private String name;
    private String email;
    private String picture;

    public SessionUser(User user) {
        this.name = user.getName();
        this.email = user.getEmail();
        this.picture = user.getProfileImage();
    }
}
