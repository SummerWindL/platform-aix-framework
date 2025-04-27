package com.platform.aix.controller.pikai.common;

import java.util.Date;

/**
 * @author fuyanliang
 * @version V1.0.0
 * @date 2025年04月18日 11:08
 */
public class AuthResponse {
    private String userId;
    private String token;
    private String email;
    private String userName;
    private Long expireTime;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Long getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(Long expireTime) {
        this.expireTime = expireTime;
    }
}
