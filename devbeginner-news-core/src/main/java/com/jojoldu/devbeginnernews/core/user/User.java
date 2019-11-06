package com.jojoldu.devbeginnernews.core.user;
import com.jojoldu.devbeginnernews.core.common.BaseTimeEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Getter
@NoArgsConstructor
@Entity
@Table(uniqueConstraints = @UniqueConstraint(name = "uni_user_1", columnNames = {"email"}))
public class User extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String username; //github username

    @Column(nullable = false)
    private String email;

    @Column
    private String profileImage;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserType userType;

    @Builder
    public User(String name, String username, String email, String profileImage, Role role, UserType userType) {
        this.name = name;
        this.username = username;
        this.email = email;
        this.profileImage = profileImage;
        this.role = role;
        this.userType = userType;
    }

    public User update(String name, String profileImage) {
        this.name = name;
        this.profileImage = profileImage;

        return this;
    }

    public String getRoleKey() {
        return this.role.getKey();
    }
}