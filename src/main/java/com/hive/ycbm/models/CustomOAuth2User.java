package com.hive.ycbm.models;

import java.util.Collections;
import java.util.Map;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;

public class CustomOAuth2User extends DefaultOAuth2User {
    private User user;

    public CustomOAuth2User(Map<String, Object> attributes, User user) {
        super(Collections.singleton(new SimpleGrantedAuthority("ROLE_ADMIN")), attributes, "email");
        this.user = user;
    }

    public User getUser() {
        return user;
    }
}