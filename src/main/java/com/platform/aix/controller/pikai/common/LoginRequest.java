package com.platform.aix.controller.pikai.common;

/**
 * @author fuyanliang
 * @version V1.0.0
 * @date 2025年04月17日 15:22
 */
public class LoginRequest {
    private String email;
    private String password;
    private String captcha;
    private String uuid; // 新增

    public String getCaptcha() {
        return captcha;
    }

    public void setCaptcha(String captcha) {
        this.captcha = captcha;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
